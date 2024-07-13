package me.psikuvit.betterteams.mysql;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Utils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {

    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;
    private Connection connection;

    public MySQL(BetterTeams plugin) {
        this.host = plugin.getConfig().getString("mysql.host");
        this.port = plugin.getConfig().getString("mysql.port");
        this.database = plugin.getConfig().getString("mysql.database");
        this.username = plugin.getConfig().getString("mysql.username");
        this.password = plugin.getConfig().getString("mysql.password");

        connectMySQL();
        registerMySQL();
    }

    // Connect to Database
    public void connectMySQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            Utils.log("Connected to MySQL");
        } catch (SQLException ex) {
            Utils.log("No valid MySQL Credentials is set in Config!\n Disabling Plugin!");
            Bukkit.getPluginManager().disablePlugin(BetterTeams.getPlugin(BetterTeams.class));
        }
    }

    // Disconnect from Database
    public void disconnectMySQL() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Utils.log("Disconnected from MySQL");
            }
        } catch (SQLException exception) {
            Utils.log("Couldn't disconnect from database");
        }
    }

    // Setting up the Database
    public void registerMySQL() {
        try {
            if (isConnected()) {
                PreparedStatement playerStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS teams (team_name VARCHAR(100), leader_name VARCHAR(100), spawn_point VARCHAR(100), members VARCHAR(255), PRIMARY KEY (team_name))");
                playerStatement.executeUpdate();
            } else {
                Utils.log("Failed to register database: No connection");
            }
        } catch (SQLException e) {
            Utils.log("Failed to register database");
        }
    }

    // If the Server is connected to the MySQL
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException exception) {
            Utils.log("Couldn't check if database is connected");
            return false;
        }
    }

    // Get the Connection
    public Connection getConnection() {
        if (!isConnected()) {
            connectMySQL();
        }
        return connection;
    }


}
