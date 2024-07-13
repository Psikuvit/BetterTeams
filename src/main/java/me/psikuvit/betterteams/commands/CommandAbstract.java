package me.psikuvit.betterteams.commands;

import me.psikuvit.betterteams.BetterTeams;
import org.bukkit.command.CommandSender;

import java.util.List;
public abstract class CommandAbstract {

    protected BetterTeams plugin;
    public CommandAbstract(final BetterTeams plugin) {
        this.plugin = plugin;
    }

    public abstract void executeCommand(final String[] args, final CommandSender sender);

    public abstract String correctArg();

    public abstract boolean onlyPlayer();

    public abstract int requiredArg();

    public abstract List<String> tabComplete(final String[] args);
}