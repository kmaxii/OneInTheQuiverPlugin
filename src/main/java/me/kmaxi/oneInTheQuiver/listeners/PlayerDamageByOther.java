package me.kmaxi.oneInTheQuiver.listeners;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class PlayerDamageByOther implements Listener {
    OneInTheQuiverMain plugin;

    public PlayerDamageByOther(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent event){
        if (plugin.gameManager.isInGame){
            event.setDamage(0);
        }
    }

}
