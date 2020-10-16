package me.kmaxi.oneinthequiver.listeners;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropCancel implements Listener {
    OneInTheQuiverMain plugin;

    public ItemDropCancel(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if (plugin.gameManager.isInGame){
            event.setCancelled(true);
        }
    }

}
