package me.psikuvit.betterteams.commands.args;

import me.psikuvit.betterteams.BetterTeams;
import me.psikuvit.betterteams.Team;
import me.psikuvit.betterteams.Utils;
import me.psikuvit.betterteams.commands.CommandAbstract;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TeamSpawnPointTPArg extends CommandAbstract {

    public TeamSpawnPointTPArg(BetterTeams plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player player = (Player) sender;

        if (!plugin.getTeamManager().isInTeam(player.getUniqueId())){
            player.sendMessage(Utils.color("&cYou aren't in a team to use this command"));
            return;
        }

        Team team = plugin.getTeamManager().getTeamByPlayer(player.getUniqueId());

        Location spawnPoint = team.getTeamSpawnLoc();
        player.teleport(spawnPoint);


    }

    @Override
    public String correctArg() {
        return "/betterteams sptp";
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
