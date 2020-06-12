package me.kmaxi.oneInTheQuiver.commands;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.gameHandler.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class QuiverCommands implements CommandExecutor {
    private OneInTheQuiverMain plugin;

    public QuiverCommands(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can do this");
            return true;
        }
        Player player = (Player) sender;
        if(!(player.hasPermission("quiver.commands"))){
            player.sendMessage(ChatColor.RED + "Missing permissions!");
            return true;
        }
        Location loc = player.getLocation();
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("points")){
                    if (plugin.gameManager.allPlayerPoints.isEmpty()){
                        sender.sendMessage(ChatColor.RED + "No one has any points right now!");
                        return true;
                    }
                    int position = 1;
                    ArrayList<Player> sortedPlayers = new ArrayList<>();
                    for (UUID playerName: plugin.gameManager.allPlayerPoints.keySet()){
                        Player p = Bukkit.getPlayer(playerName);
                        sortedPlayers.add(p);
                    }
                    sortedPlayers.sort((playerA, playerB) -> plugin.gameManager.allPlayerPoints.get(playerA.getUniqueId()) - plugin.gameManager.allPlayerPoints.get(playerB.getUniqueId()));
                    for(Player playerManager : sortedPlayers){
                        sender.sendMessage(ChatColor.GRAY + String.valueOf(position) + ". " + playerManager.getName() + " (" + plugin.gameManager.allPlayerPoints.get(playerManager.getUniqueId()) + "p)");
                        position++;
                    }
                    return true;
            }
                if (args[0].equalsIgnoreCase(plugin.commands.cmd2)){ //CMD2, which should set spawn location for time before game starts
                    plugin.getConfig().set("spawn", loc);
                    player.sendMessage(ChatColor.GREEN + "Spawn succesfully located!");
                    plugin.saveConfig();
                    return true;
                }
                if (args[0].equalsIgnoreCase(plugin.commands.cmd3)){ //Command three which should start the game
                    player.sendMessage(ChatColor.GREEN + "You have started the game");
                    plugin.gameManager.setUpGame();
                    return true;
                }
                if (args[0].equalsIgnoreCase("end")){
                    plugin.gameManager.endGame(plugin.gameManager.playerManager.get(player.getUniqueId()));
                    player.sendMessage(ChatColor.RED + "You have force ended the game!");
                    return true;
                }


            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("spawnpoints")){
                    plugin.getConfig().set("spawnPointAmount", Integer.valueOf(args[1]));
                    player.sendMessage(ChatColor.GREEN + "You have set the spawn points amount to " + ChatColor.WHITE + args[1]);
                    plugin.saveConfig();
                    return true;

                }
                if (!(args[0].equalsIgnoreCase(plugin.commands.cmd1))) {;
                    return true;
                }
                if (!plugin.getConfig().contains("spawnPointAmount")){
                    player.sendMessage(ChatColor.RED + "Please set a spawn point amount first using" + ChatColor.WHITE + "/quiver spawnpoints [amount]");
                }
                    plugin.getConfig().set("point." + args[1], loc);
                    player.sendMessage(ChatColor.GREEN + "Spawn point " + ChatColor.WHITE + args[1]  + "/" + plugin.getConfig().get("spawnPointAmount") + ChatColor.GREEN + " succesfully located!");
                    plugin.saveConfig();
                    return true;

            } else {
                player.sendMessage(ChatColor.RED + "Missing arguments");
            }





        return false;
    }


}
