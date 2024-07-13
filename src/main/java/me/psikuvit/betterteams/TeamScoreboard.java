package me.psikuvit.betterteams;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class TeamScoreboard {

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Team team;

    public TeamScoreboard(Team team) {
        this.team = team;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective(team.getName(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateScoreboard() {
        scoreboard.getEntries().forEach(scoreboard::resetScores);

        objective.setDisplayName(Utils.color("&eTeam: &7" + team.getName()));

        OfflinePlayer offlineLeader = Bukkit.getOfflinePlayer(team.getLeader());

        Score leader = objective.getScore(Utils.color("&bLeader: &7" + offlineLeader.getName()));
        leader.setScore(5);
        Score members = objective.getScore(Utils.color("&bMembers:"));
        members.setScore(4);

        for (int i = 0; i < team.getMembers().size(); i++) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(team.getMembers().get(i));
            Score member = objective.getScore(Utils.color("- &e" + offlinePlayer.getName()));
            member.setScore(i);
        }
        if (offlineLeader.isOnline()) offlineLeader.getPlayer().setScoreboard(scoreboard);

        team.getMembers().forEach(member -> {
            OfflinePlayer offlineMember = Bukkit.getOfflinePlayer(member);

            if (offlineMember.isOnline()) offlineMember.getPlayer().setScoreboard(scoreboard);
        });
    }
}
