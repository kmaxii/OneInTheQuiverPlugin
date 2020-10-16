package me.kmaxi.oneinthequiver.commands;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import me.kmaxi.oneinthequiver.gamehandler.PlayerManager;
import me.kmaxi.oneinthequiver.managers.DataBaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;

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
        PlayerManager playerManager = PlayerManager.getInstance();
        Location loc = player.getLocation();
            if (args.length == 0){
                player.sendMessage(ChatColor.YELLOW + "----------" + ChatColor.WHITE + " Commands " + ChatColor.YELLOW + "--------------------");
                player.sendMessage(ChatColor.GOLD + "/quiver setspawn" + ChatColor.WHITE + " sets the main spawn");
                player.sendMessage(ChatColor.GOLD + "/quiver spawnpoints x" + ChatColor.WHITE + " sets the amount of spawnpoints used for the game");
                player.sendMessage(ChatColor.GOLD + "/quiver resetpoints" + ChatColor.WHITE + " Resets all the points");
                player.sendMessage(ChatColor.GOLD + "/quiver point x" + ChatColor.WHITE + " sets a spawn point");
                player.sendMessage(ChatColor.GOLD + "/quiver end" + ChatColor.WHITE + " Force Ends a game");
                player.sendMessage(ChatColor.GOLD + "/quiver start" + ChatColor.WHITE + " starts a game");
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("points")){
                    if (playerManager.getPlayerManagers().isEmpty()){
                        player.sendMessage(ChatColor.RED + "Noone has any points right now!");
                        return true;
                    }
                    int position = 1;
                    ArrayList<Player> sortedPlayers = new ArrayList<>();
                    for (Player player1 : Bukkit.getOnlinePlayers()){
                        if (playerManager.getPlayerManagerByUUID(player1.getUniqueId()) != null) {
                            sortedPlayers.add(player1);
                        }
                    }
                    if (sortedPlayers.size() <= 0){
                        sender.sendMessage(ChatColor.RED + "An error occurred while retrieving the points");
                        return true;
                    }
                    sortedPlayers.sort(Comparator.comparingInt(playerA -> playerManager.getPlayerManagerByUUID(playerA.getUniqueId()).getPoints()));
                    ArrayList<Player> fullySorted = new ArrayList<>();
                    for(int index = sortedPlayers.size() - 1; index >= 0; index--){
                        fullySorted.add(sortedPlayers.get(index));
                    }
                    for(Player playerManager1 : fullySorted){
                        sender.sendMessage(ChatColor.GRAY + String.valueOf(position) + ". " + playerManager1.getName() + " (" + playerManager.getPlayerManagerByUUID(playerManager1.getUniqueId()).getPoints() + "p)");
                        position++;
                    }
                    return true;
            }
                if(!(player.hasPermission("quiver.commands"))){
                    player.sendMessage(ChatColor.RED + "Missing permissions!");
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
                if (args[0].equalsIgnoreCase("resetpoints")){
                    DataBaseManager.getInstance().reset();
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
