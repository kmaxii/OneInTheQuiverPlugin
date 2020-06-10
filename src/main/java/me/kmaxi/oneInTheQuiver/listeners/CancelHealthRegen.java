package me.kmaxi.oneInTheQuiver.listeners;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class CancelHealthRegen implements Listener {
    OneInTheQuiverMain plugin;

    public CancelHealthRegen(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    public void onHeathRegen(EntityRegainHealthEvent event){
        if (plugin.gameManager.isStarted){
            event.setCancelled(true);
        }
    }
}
