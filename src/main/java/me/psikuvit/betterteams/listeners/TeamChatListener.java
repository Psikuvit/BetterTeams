package me.psikuvit.betterteams.listeners;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Team;
import me.psikuvit.betterteams.TeamManager;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.comminication.Channel;
import me.psikuvit.betterteams.comminication.ChannelManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeamChatListener implements Listener {

    private final TeamManager teamManager;
    private final ChannelManager channelManager;

    public TeamChatListener(BetterTeams plugin) {
        this.teamManager = plugin.getTeamManager();
        this.channelManager = plugin.getChannelManager();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Channel channel = channelManager.getChannel(player);
        if (channel == Channel.TEAM) {
            event.setCancelled(true);
            Team team = teamManager.getTeamByPlayer(player.getUniqueId());

            team.getMembers().forEach(uuid -> {
                Player receiver = Bukkit.getPlayer(uuid);
                receiver.sendMessage(Utils.color("&7<" + player.getName() + "&7>: " + event.getMessage()));
            });

            Bukkit.getPlayer(team.getLeader()).sendMessage(Utils.color("&7[" + team.getName() + "]" + "&b|" + player.getName() + "&b|: &7" + event.getMessage()));
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        channelManager.setPlayerChannel(player, Channel.GLOBAL);
    }
}
