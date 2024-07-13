package me.psikuvit.betterteams.commands.args;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Team;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.commands.CommandAbstract;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MemberAddArg extends CommandAbstract {

    public MemberAddArg(BetterTeams plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player leader = (Player) sender;
        Player memberToAdd = Bukkit.getPlayer(args[0]);

        if (memberToAdd == null) {
            leader.sendMessage(Utils.color("&cCouldn't find player."));
            return;
        }

        if (leader == memberToAdd) {
            leader.sendMessage(Utils.color("&cYou can't add yourself."));
            return;
        }

        if (!plugin.getTeamManager().isLeader(leader.getUniqueId())) {
            leader.sendMessage(Utils.color("&cYou don't have any team to add players."));
            return;
        }

        if (plugin.getTeamManager().isInTeam(memberToAdd.getUniqueId())) {
            leader.sendMessage(Utils.color("&cThis player is already in a team."));
            return;
        }

        Team team = plugin.getTeamManager().getTeamByLeader(leader.getUniqueId());

        if (team.teamSize() == 5) {
            leader.sendMessage(Utils.color("&cYou have reached maximum members amount '5'"));
            return;
        }

        team.addMember(memberToAdd.getUniqueId());
        leader.sendMessage(Utils.color("&bMember &7" + memberToAdd.getName() + " &bwas added to your team."));
        memberToAdd.sendMessage(Utils.color("&bYou were added to the team &e" + team.getName()));
    }

    @Override
    public String correctArg() {
        return "/betterteams add <member_name>";
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
