package me.kmaxi.oneInTheQuiver.utils;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.gameHandler.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Score {


    public static void killed(Player player, OneInTheQuiverMain plugin){
        Inventory playerInv = player.getInventory();
        playerInv.clear();
        Random random = new Random();
        int index = random.nextInt(plugin.gameManager.spawnLocations.size());
        Location location = plugin.gameManager.spawnLocations.get(index);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1));
        player.teleport(location);
        playerInv.addItem(new ItemStack(Material.WOOD_SWORD,1));
        playerInv.addItem(new ItemStack(Material.BOW, 1));
        playerInv.addItem(new ItemStack(Material.ARROW, 1));


        player.setHealth(20);
    }

    public static void killSomeone(Player player, OneInTheQuiverMain plugin){
        PlayerManager playerManager = plugin.gameManager.playerManager.get(player.getUniqueId());
        playerManager.addKill();
        player.setHealth(20);
        player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1,1);
        if(plugin.gameManager.playerManager.get(player.getUniqueId()).kills >= 20){
            plugin.gameManager.endGame(plugin.gameManager.playerManager.get(player.getUniqueId()));
        }

    }
}
