package me.kmaxi.oneInTheQuiver.listeners;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamageByOther implements Listener {
    OneInTheQuiverMain plugin;

    public PlayerDamageByOther(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (!(plugin.gameManager.isInGame)) {
            return;
        }
        if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL){
            event.setDamage(0);
        }
    }

}
