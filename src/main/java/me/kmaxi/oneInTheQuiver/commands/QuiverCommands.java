package me.kmaxi.oneInTheQuiver.commands;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        Location loc = player.getLocation();
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase(plugin.commands.cmd2)){ //CMD2, which should set spawn location for time before game starts
                    plugin.getConfig().set("spawn", loc);
                    player.sendMessage(ChatColor.GREEN + "Spawn succesfully located!");
                    plugin.saveConfig();
                    return true;
                }
                if (args[0].equalsIgnoreCase(plugin.commands.cmd3)){ //Command three which should start the game
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
                    player.sendMessage(ChatColor.GREEN + "Spawn point " + args[1] + ChatColor.WHITE + "/" + plugin.getConfig().get("spawnPointAmount") + ChatColor.GREEN + " succesfully located!");
                    plugin.saveConfig();
                    return true;

            } else {
                player.sendMessage(ChatColor.RED + "Missing arguments");
            }





        return false;
    }


}
