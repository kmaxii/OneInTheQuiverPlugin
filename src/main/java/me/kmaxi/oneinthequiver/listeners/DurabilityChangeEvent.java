package me.kmaxi.oneinthequiver.listeners;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class DurabilityChangeEvent implements Listener {
    OneInTheQuiverMain plugin;

    public DurabilityChangeEvent(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDurabilityChange(PlayerItemDamageEvent event){
        if(plugin.gameManager.isStarted){
            event.setDamage(0);
        }
    }
}
