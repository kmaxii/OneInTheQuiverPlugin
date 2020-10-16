package me.kmaxi.oneinthequiver.listeners;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class RemoveArrow implements Listener {
    OneInTheQuiverMain plugin;

    public RemoveArrow(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if(plugin.gameManager.isInGame){
            if (!(event.getEntity() instanceof Arrow)){
                return;
            }
            Arrow arrow = (Arrow) event.getEntity();
            arrow.remove();

        }
    }
}
