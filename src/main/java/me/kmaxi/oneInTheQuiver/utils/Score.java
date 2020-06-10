package me.kmaxi.oneInTheQuiver.utils;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.gameHandler.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Score {


    public static void killed(Player player, OneInTheQuiverMain plugin){
        Inventory playerInv = player.getInventory();
        playerInv.clear();
        playerInv.addItem(new ItemStack(Material.WOOD_SWORD,1));
        playerInv.addItem(new ItemStack(Material.BOW, 1));
        playerInv.addItem(new ItemStack(Material.ARROW, 1));
        Random random = new Random();
        int index = random.nextInt(plugin.gameManager.spawnLocations.size());
        Location location = plugin.gameManager.spawnLocations.get(index);
        player.teleport(location);
        player.setHealth(20);
    }

    public static void killSomeone(Player player, OneInTheQuiverMain plugin){
        PlayerManager playerManager = plugin.gameManager.playerManager.get(player.getUniqueId());
        playerManager.addKill();
        player.setHealth(20);
        player.getInventory().addItem(new ItemStack(Material.ARROW, 1));

    }
}
