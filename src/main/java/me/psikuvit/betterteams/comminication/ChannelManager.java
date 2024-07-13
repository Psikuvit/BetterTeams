package me.psikuvit.betterteams.comminication;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    private final Map<Player, Channel> playerChannel;

    public ChannelManager() {
        this.playerChannel = new HashMap<>();
    }

    public Channel getChannel(Player player) {
        return playerChannel.get(player);
    }

    public void setPlayerChannel(Player player, Channel channel) {
        playerChannel.put(player, channel);
    }

    public Map<Player, Channel> getPlayerChannel() {
        return playerChannel;
    }
}
