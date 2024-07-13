package me.psikuvit.betterteams.commands.args;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.commands.CommandAbstract;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TeamCreateArg extends CommandAbstract {

    public TeamCreateArg(BetterTeams plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player leader = (Player) sender;

        String team_name = args[0];

        if (plugin.getTeamManager().isLeader(leader.getUniqueId())) {
            leader.sendMessage(Utils.color("&cYou already have a team"));
            return;
        }

        plugin.getTeamManager().createTeam(team_name, leader.getUniqueId(), leader.getLocation());

        leader.sendMessage(Utils.color("&bTeam &7" + team_name + " &bwas created successfully!"));

    }

    @Override
    public String correctArg() {
        return "/betterteams create <team_name>";
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public int requiredArg() {
        return 1;
    }


    @Override
    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }
}
