package me.psikuvit.betterteams.commands.args;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.commands.CommandAbstract;
import me.psikuvit.betterteams.comminication.Channel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ChannelToggleArg extends CommandAbstract {

    public ChannelToggleArg(BetterTeams plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player player = (Player) sender;

        if (!plugin.getTeamManager().isInTeam(player.getUniqueId())) {
            player.sendMessage(Utils.color("&cYou are not in a team to switch to team chat."));
            return;
        }

        Channel channel = plugin.getChannelManager().getChannel(player);
        Channel newChannel = null;
        if (channel == Channel.GLOBAL) {
            newChannel = Channel.TEAM;
        } else if (channel == Channel.TEAM) {
            newChannel = Channel.GLOBAL;
        }

        plugin.getChannelManager().setPlayerChannel(player, newChannel);
        player.sendMessage(Utils.color("&bChat channel toggled to: &7" + newChannel));


    }

    @Override
    public String correctArg() {
        return "/betterteams toggle";
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
