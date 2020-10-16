package me.kmaxi.oneinthequiver.listeners;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinWhileInGame implements Listener {
    OneInTheQuiverMain plugin;

    public PlayerJoinWhileInGame(OneInTheQuiverMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.gameManager.isInGame) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();
                player.sendMessage(ChatColor.RED + "A game is currently running! You have to wait until it's over :(");
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(plugin.gameManager.spawn);
            }
        }.runTaskLater(plugin, 2);
    }

}
