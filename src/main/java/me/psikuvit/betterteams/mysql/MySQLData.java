package me.psikuvit.betterteams.mysql;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Team;
import me.psikuvit.betterteams.TeamManager;
import me.psikuvit.betterteams.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQLData  {

    private final MySQL mySQL;
    private final TeamManager teamManager;

    public MySQLData(BetterTeams plugin) {
        this.mySQL = plugin.getMySQL();
        this.teamManager = plugin.getTeamManager();
    }

    public void loadTeams() {
        try (Connection connection = mySQL.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT team_name, leader_name, spawn_point, members FROM teams");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String team_name = resultSet.getString("team_name");
                String leader_name = resultSet.getString("leader_name");
                String spawn_point = resultSet.getString("spawn_point");
                String membersString = resultSet.getString("members");

                Location location = Utils.stringToLocation(spawn_point);
                List<UUID> members = new ArrayList<>();

                if (membersString != null && !membersString.isEmpty()) {

                    String[] membersNames = membersString.split("\\.");

                    for (String member : membersNames) {
                        UUID uuid = UUID.fromString(member);
                        members.add(uuid);
                    }
                }

                teamManager.createTeam(team_name, UUID.fromString(leader_name), location, members);
            }

            Utils.log("Loaded data from the database");
        } catch (SQLException exception) {
            Utils.log("Couldn't generate player data");
        }
    }

    public void saveTeams() {
        try (Connection connection = mySQL.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO teams (team_name, leader_name, spawn_point, members) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE team_name=team_name");

            for (Team team : teamManager.getTeams()) {
                preparedStatement.setString(1, team.getName());
                preparedStatement.setString(2, team.getLeader().toString());
                preparedStatement.setString(3, Utils.locationToString(team.getTeamSpawnLoc()));

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < team.getMembers().size(); i++) {
                    sb.append(team.getMembers().get(i).toString());
                    if (i < team.getMembers().size()) {
                        sb.append(".");
                    }
                }

                preparedStatement.setString(4, sb.toString());
                preparedStatement.executeUpdate();
            }

            Utils.log("Saved data into the database");

        } catch (SQLException e) {
            Utils.log("Can't find connection");
        }
    }
}
