package me.psikuvit.betterteams.commands.args;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Team;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.commands.CommandAbstract;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SetTeamSpawnPointArg extends CommandAbstract {

    public SetTeamSpawnPointArg(BetterTeams plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player leader = (Player) sender;

        if (!plugin.getTeamManager().isLeader(leader.getUniqueId())) {
            leader.sendMessage(Utils.color("&cYou don't have any team to add players."));
            return;
        }

        Team team = plugin.getTeamManager().getTeamByLeader(leader.getUniqueId());

        Location newLoc = leader.getLocation();
        team.setTeamSpawnLoc(newLoc);
        leader.sendMessage(Utils.color("&bTeam spawn point changed successfully."));

    }

    @Override
    public String correctArg() {
        return "/betterteams stsp";
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public int requiredArg() {
        return 0;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }
}
