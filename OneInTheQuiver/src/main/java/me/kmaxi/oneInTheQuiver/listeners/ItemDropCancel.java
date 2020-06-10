package me.kmaxi.oneInTheQuiver.listeners;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInventoryEvent;

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
