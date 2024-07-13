package me.psikuvit.betterteams.commands.args;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Team;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.commands.CommandAbstract;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TeamDeleteArg extends CommandAbstract {

    public TeamDeleteArg(BetterTeams plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player leader = (Player) sender;

        if (!plugin.getTeamManager().isInTeam(leader.getUniqueId())) {
            leader.sendMessage(Utils.color("&cYou don't have a team to delete"));
            return;
        }

        if (!plugin.getTeamManager().isLeader(leader.getUniqueId())) {
            leader.sendMessage(Utils.color("&cYou don't have a the permission to delete the delete"));
            return;
        }

        Team team = plugin.getTeamManager().getTeamByPlayer(leader.getUniqueId());

        plugin.getTeamManager().deleteTeam(team);

        leader.sendMessage(Utils.color("&bTeam &7" + team.getName() + " &bwas deleted successfully!"));

    }

    @Override
    public String correctArg() {
        return "/betterteams delete";
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
