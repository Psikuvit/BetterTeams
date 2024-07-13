package me.psikuvit.betterteams;

import me.psikuvit.betterteams.commands.CommandRegister;
import me.psikuvit.betterteams.comminication.ChannelManager;
import me.psikuvit.betterteams.listeners.TeamChatListener;
import me.psikuvit.betterteams.mysql.MySQL;
import me.psikuvit.betterteams.mysql.MySQLData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterTeams extends JavaPlugin {

    private MySQL mySQL;
    private MySQLData mySQLData;
    private TeamManager teamManager;
    private ChannelManager channelManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        teamManager = new TeamManager();
        mySQL = new MySQL(this);
        mySQLData = new MySQLData(this);
        channelManager = new ChannelManager();

        saveDefaultConfig();

        mySQLData.loadTeams();

        getCommand("betterteams").setExecutor(new CommandRegister(this));
        getCommand("betterteams").setTabCompleter(new CommandRegister(this));

        getServer().getPluginManager().registerEvents(new TeamChatListener(this), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        mySQLData.saveTeams();
        mySQL.disconnectMySQL();
        Bukkit.getOnlinePlayers().forEach(player -> player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()));
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public ChannelManager getChannelManager() {
        return channelManager;
    }
}
