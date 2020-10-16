package me.kmaxi.oneinthequiver.listeners;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import me.kmaxi.oneinthequiver.gamehandler.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DashAbility implements Listener {
    OneInTheQuiverMain plugin = OneInTheQuiverMain.getPlugin(OneInTheQuiverMain.class);

    @EventHandler
    public void onEvent(PlayerInteractEvent event){
        if (!plugin.gameManager.isStarted) return;
        if(!(event.getMaterial().equals(Material.FEATHER))) {
            return;
        }
        Player player = event.getPlayer();
        PlayerManager playerManager = plugin.gameManager.playerManager.get(player.getUniqueId());
        if (!(playerManager.dashCooldown == 0)){
            player.sendMessage(ChatColor.RED + "This is on cooldown! " + ChatColor.YELLOW + "Please wait another " + playerManager.dashCooldown + " seconds.");
            return;
        }
        if (playerManager.dashes == 0) {
            player.sendMessage(ChatColor.YELLOW + "You are out of leaps! You get one dash each second kill on a killstreak!");
            return;
        }

        player.sendMessage(ChatColor.YELLOW + "You have leaped!");
        Vector vector = new Vector(player.getLocation().getDirection().getX(), 0, player.getLocation().getDirection().getZ());
        vector.normalize();
        player.setVelocity(vector.multiply(2.3));
        playerManager.dashes--;
        playerManager.dashCooldown = 3;
        new BukkitRunnable(){


            @Override
            public void run() {
                if (playerManager.dashCooldown <= 0){
                    cancel();
                    return;
                }
                playerManager.dashCooldown--;

            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
