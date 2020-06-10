package me.kmaxi.oneInTheQuiver;

import me.kmaxi.oneInTheQuiver.commands.Commands;
import me.kmaxi.oneInTheQuiver.gameHandler.GameManager;
import me.kmaxi.oneInTheQuiver.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OneInTheQuiverMain extends JavaPlugin{
        public static OneInTheQuiverMain instance;
        public Commands commands;
        public GameManager gameManager;

        @Override
        public void onEnable(){
                instanceClasses();
                loadConfig();
                Bukkit.getPluginManager().registerEvents(new EntityDamageEvent(this), this);
                Bukkit.getPluginManager().registerEvents(new CancelHealthRegen(this), this);
                Bukkit.getPluginManager().registerEvents(new DurabilityChangeEvent(this), this);
                Bukkit.getPluginManager().registerEvents(new MaxSaturation(), this);
                Bukkit.getPluginManager().registerEvents(new CancelDamage(this), this);
                Bukkit.getPluginManager().registerEvents(new RemoveArrow(this), this);
                Bukkit.getPluginManager().registerEvents(new ItemDropCancel(this), this);
                Bukkit.getPluginManager().registerEvents(new PlayerDamageByOther(this), this);
        }

        @Override
        public void onDisable(){

        }


        private void instanceClasses(){
                commands = new Commands(this);
                commands.registerCommands();
                gameManager = new GameManager(this);
                instance = this;


        }

        private void loadConfig(){
                getConfig().options().copyDefaults(true);
                saveConfig();
                
        }


}
