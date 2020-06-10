package me.kmaxi.oneInTheQuiver.listeners;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CancelDamage implements Listener {
    OneInTheQuiverMain plugin;

    public CancelDamage(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void damageEvent(EntityDamageByBlockEvent event){
        if (plugin.gameManager.isStarted) {
            if (!(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)
                    || (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)
                    || (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)))
                    || (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)))) {
                return;
            }
            event.setCancelled(true);
        }
    }
}
