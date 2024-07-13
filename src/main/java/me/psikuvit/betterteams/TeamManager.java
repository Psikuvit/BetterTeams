package me.psikuvit.betterteams;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamManager {

    private final List<Team> teams;

    public TeamManager() {
        teams = new ArrayList<>();
    }

    public void createTeam(String name, UUID leader, Location spawnPoint) {
        Team team = new Team(name, leader, spawnPoint);
        teams.add(team);
        team.getTeamScoreboard().updateScoreboard();
    }

    public void createTeam(String name, UUID leader, Location spawnPoint, List<UUID> members) {
        Team team = new Team(name, leader, spawnPoint);
        members.forEach(team::addMember);
        teams.add(team);
    }

    public void deleteTeam(Team team) {
        teams.remove(team);
        team.getMembers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        });
        Bukkit.getPlayer(team.getLeader()).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public Team getTeamByLeader(UUID leader) {
        for (Team team : teams) {
            if (team.getLeader() == leader) {
                return team;
            }
        }
        return null;
    }

    public Team getTeamByMember(UUID member) {
        for (Team team : teams) {
            if (team.getMembers().contains(member)) {
                return team;
            }
        }
        return null;
    }

    public Team getTeamByPlayer(UUID player) {
        if (isLeader(player)) {
            return getTeamByLeader(player);
        } else {
            return getTeamByMember(player);
        }
    }

    public boolean isInTeam(UUID member) {
        return getTeamByMember(member) != null || getTeamByLeader(member) != null;
    }

    public boolean isLeader(UUID leader) {
        return getTeamByLeader(leader) != null;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
