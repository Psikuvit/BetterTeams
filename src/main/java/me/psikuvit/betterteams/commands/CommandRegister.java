package me.psikuvit.betterteams.commands;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.commands.args.ChannelToggleArg;
import me.psikuvit.betterteams.commands.args.MemberAddArg;
import me.psikuvit.betterteams.commands.args.SetTeamSpawnPointArg;
import me.psikuvit.betterteams.commands.args.TeamCreateArg;
import me.psikuvit.betterteams.commands.args.TeamDeleteArg;
import me.psikuvit.betterteams.commands.args.TeamSpawnPointTPArg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegister implements CommandExecutor, TabCompleter {
    private final Map<String, CommandAbstract> commandAbstractMap;

    public CommandRegister(BetterTeams plugin) {
        commandAbstractMap = new HashMap<>();
        commandAbstractMap.put("create", new TeamCreateArg(plugin));
        commandAbstractMap.put("add", new MemberAddArg(plugin));
        commandAbstractMap.put("stsp", new SetTeamSpawnPointArg(plugin));
        commandAbstractMap.put("sptp", new TeamSpawnPointTPArg(plugin));
        commandAbstractMap.put("toggle", new ChannelToggleArg(plugin));
        commandAbstractMap.put("delete", new TeamDeleteArg(plugin));


    }

    public boolean onCommand(final CommandSender commandSender, final Command command, final String label, String[] args) {
        if (args.length == 0) {
            return true;
        }
        String subCommand = args[0];
        boolean found = false;

        for (String cmdAlias : this.commandAbstractMap.keySet()) {
            if (cmdAlias.equalsIgnoreCase(subCommand)) {

                int argsCount = args.length - 1;
                boolean isSenderPlayer = commandSender instanceof Player;
                CommandAbstract cmd = this.commandAbstractMap.get(cmdAlias);

                if (argsCount != cmd.requiredArg()) {
                    commandSender.sendMessage(Utils.color("&cCorrect usage: &e" + cmd.correctArg()));
                    return true;
                }

                if (!isSenderPlayer && cmd.onlyPlayer()) {
                    commandSender.sendMessage(Utils.color("Sender must be a Player"));
                    return true;
                }

                args = this.move(args);
                cmd.executeCommand(args, commandSender);
                found = true;
                break;
            }
        }
        if (!found) {
            commandSender.sendMessage(Utils.color("&cIncorrect Command!"));
        }
        return true;
    }

    private String[] move(final String[] args) {
        final String[] result = new String[args.length - 1];
        System.arraycopy(args, 1, result, 0, args.length - 1);
        return result;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            // Return available sub-commands as tab completions
            List<String> completions = new ArrayList<>(commandAbstractMap.keySet());
            completions.removeIf(cmdAlias -> !cmdAlias.toLowerCase().startsWith(args[0].toLowerCase()));
            return completions;
        } else if (args.length > 1) {
            // Retrieve the CommandAbstract associated with the given sub-command
            CommandAbstract cmd = commandAbstractMap.get(args[0].toLowerCase());
            if (cmd != null) {
                // Delegate tab completion to the associated CommandAbstract
                return cmd.tabComplete(args);
            }
        }
        return new ArrayList<>();
    }
}