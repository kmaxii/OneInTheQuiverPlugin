package me.kmaxi.oneInTheQuiver.listeners;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

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
