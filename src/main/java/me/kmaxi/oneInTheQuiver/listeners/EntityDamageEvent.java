package me.kmaxi.oneInTheQuiver.listeners;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.utils.Score;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDamageEvent implements Listener {
    OneInTheQuiverMain plugin;

    public EntityDamageEvent(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(plugin.gameManager.isStarted){
            Player damaged = (Player) event.getEntity();
            if (event.getCause().equals(DamageCause.PROJECTILE)) {
                Arrow shoot = (Arrow) event.getDamager();
                Player shooter = (Player) shoot.getShooter();
                if(shooter.equals(damaged)){
                    damaged.sendMessage(ChatColor.RED + "You can't kill yourself...");
                    return;
                }
                Bukkit.getServer().broadcastMessage(ChatColor.GRAY + damaged.getDisplayName() + ChatColor.YELLOW + " was killed by " + ChatColor.GRAY + shooter.getName());
                Score.killed(damaged, plugin);
                Score.killSomeone(shooter, plugin);
                event.setDamage(0);
            }
            if (!(event.getDamager() instanceof Player)){
                return;
            }

            if(damaged.getHealth() - event.getDamage() <= 0){
                Player damager = (Player) event.getDamager();
                if (damaged.equals(damager)){
                    damaged.sendMessage(ChatColor.RED + "You can't kill yourself...");
                    damaged.setHealth(event.getDamage());
                    return;
                }
                Score.killed(damaged, plugin);
                Score.killSomeone(damager, plugin);
                damaged.setHealth(20);
                event.setDamage(0);
                Bukkit.getServer().broadcastMessage(ChatColor.GRAY + damaged.getDisplayName() + ChatColor.YELLOW + " was killed by " + ChatColor.GRAY + damager.getName());
                return;
            }
        }
    }



}
