package me.kmaxi.oneinthequiver.utils;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import me.kmaxi.oneinthequiver.gamehandler.PlayerManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
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
        ItemStack sword = new ItemStack(Material.WOOD_SWORD, 1);
        sword.addEnchantment(Enchantment.DURABILITY, (short) 3);
        playerInv.addItem(sword);
        playerInv.addItem(new ItemStack(Material.BOW, 1));
        playerInv.addItem(new ItemStack(Material.ARROW, 1));
        ItemStack feather = new ItemStack(Material.FEATHER, 1);
        feather.getItemMeta().setDisplayName(ChatColor.YELLOW + "Leaping feather");
        playerInv.setItem(8, feather);
        plugin.gameManager.playerManager.get(player.getUniqueId()).killStreak = 0;
        player.setHealth(20);
        plugin.gameManager.playerManager.get(player.getUniqueId()).dashes = 2;
    }

    public static void killSomeone(Player player, OneInTheQuiverMain plugin){
        plugin.gameManager.allPlayers.sort((playerA, playerB) -> playerB.getKills() - playerA.getKills());
        PlayerManager playerManager = plugin.gameManager.playerManager.get(player.getUniqueId());
        playerManager.addKill();
        player.setHealth(20);
        player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1,2);
        killSomeonePoints(playerManager, plugin);
        if(plugin.gameManager.playerManager.get(player.getUniqueId()).kills >= 30){
            plugin.gameManager.allPlayers.sort((playerA, playerB) -> playerB.getKills() - playerA.getKills());
            plugin.gameManager.endGame(plugin.gameManager.playerManager.get(player.getUniqueId()));
        }
        if (playerManager.dashes < 3 && playerManager.killStreak % 2 == 0){
            playerManager.dashes++;
        }
    }

    public static void killSomeonePoints(PlayerManager playerManager, OneInTheQuiverMain plugin){
        int allPoints = 0;
        if (playerManager.killStreak == 3){
            Bukkit.broadcastMessage(ChatColor.YELLOW + playerManager.player.getName() + ChatColor.GRAY + " is on a 3 killstreak!");
        }
        if (playerManager.killStreak == 5){
            Bukkit.broadcastMessage(ChatColor.GOLD + playerManager.player.getName() + ChatColor.GRAY + " is unstoppable! and is on a 5 killstreak!");
        }
        if (playerManager.killStreak <= 2){
            allPoints++;
            playerManager.player.sendMessage(ChatColor.GREEN + "+1 point " + ChatColor.GRAY + "for getting a kill");
        }
        if (playerManager.killStreak >= 3 && playerManager.killStreak <= 4){
            allPoints += 2;
            playerManager.player.sendMessage(ChatColor.GREEN + "+2 points " + ChatColor.GRAY + "for getting a kill while having a " + playerManager.killStreak + " killstreak!");
        }
        if (playerManager.killStreak >= 5){
            allPoints += 3;
            playerManager.player.sendMessage(ChatColor.GREEN + "+3 points " + ChatColor.GRAY + "for getting a kill while having a " + playerManager.killStreak + " killstreak!");
        }
        playerManager.addPoints(allPoints);

    }

    public static void spawn(Player player, OneInTheQuiverMain plugin){
        Inventory playerInv = player.getInventory();
        Random random = new Random();
        playerInv.clear();
        for (Location loc : plugin.gameManager.spawnLocations){
            int index = random.nextInt(plugin.gameManager.spawnLocations.size());
            Location location = plugin.gameManager.spawnLocations.get(index);
            if(plugin.gameManager.takenLocations.contains(location)){
                continue;
            }
            player.teleport(location);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1));
            plugin.gameManager.takenLocations.add(location);
            plugin.gameManager.playerManager.get(player.getUniqueId()).dashes = 2;
            break;
        }

        ItemStack sword = new ItemStack(Material.WOOD_SWORD, 1);
        sword.addEnchantment(Enchantment.DURABILITY, (short) 3);
        playerInv.addItem(sword);
        playerInv.addItem(new ItemStack(Material.BOW, 1));
        playerInv.addItem(new ItemStack(Material.ARROW, 1));
        ItemStack feather = new ItemStack(Material.FEATHER, 1);
        feather.getItemMeta().setDisplayName(ChatColor.YELLOW + "Leaping feather");
        playerInv.setItem(8, feather);
        plugin.gameManager.playerManager.get(player.getUniqueId()).killStreak = 0;
        player.setHealth(20);

    }
}
