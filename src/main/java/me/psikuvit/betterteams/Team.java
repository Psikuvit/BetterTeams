package me.psikuvit.betterteams;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {

    private final String name;
    private final List<UUID> members;
    private final UUID leader;
    private Location teamSpawnLoc;
    private final TeamScoreboard teamScoreboard;

    public Team(String name, UUID leader, Location teamSpawnLoc) {
        this.name = name;
        this.members = new ArrayList<>();
        this.leader = leader;
        this.teamSpawnLoc = teamSpawnLoc;
        this.teamScoreboard = new TeamScoreboard(this);
    }

    public List<UUID> getMembers() {
        return members;
    }

    public UUID getLeader() {
        return leader;
    }

    public Location getTeamSpawnLoc() {
        return teamSpawnLoc;
    }

    public void setTeamSpawnLoc(Location newSpawnLoc) {
        this.teamSpawnLoc = newSpawnLoc;
    }

    public String getName() {
        return name;
    }

    public void addMember(UUID player) {
        members.add(player);
        teamScoreboard.updateScoreboard();
    }

    public int teamSize() {
        return members.size() + 1;
    }

    public TeamScoreboard getTeamScoreboard() {
        return teamScoreboard;
    }
}
