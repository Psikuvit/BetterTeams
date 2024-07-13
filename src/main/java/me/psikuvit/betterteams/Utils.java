package me.psikuvit.betterteams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class Utils {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void log(String toLog) {
        Bukkit.getLogger().info(toLog);
    }

    public static String locationToString(Location location) {
        if (location == null) {
            return "";
        }
        return location.getWorld().getName() + "," +
                location.getX() + "," +
                location.getY() + "," +
                location.getZ() + "," +
                location.getYaw() + "," +
                location.getPitch();
    }

    public static Location stringToLocation(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] parts = str.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid location string");
        }
        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
