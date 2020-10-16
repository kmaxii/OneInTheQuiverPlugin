package me.kmaxi.oneinthequiver;

import me.kmaxi.oneinthequiver.commands.Commands;
import me.kmaxi.oneinthequiver.gamehandler.GameManager;
import me.kmaxi.oneinthequiver.listeners.*;
import me.kmaxi.oneinthequiver.managers.DataBaseManager;
import me.kmaxi.oneinthequiver.scoreboard.OlzieScoreboardUtil;
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
                Bukkit.getPluginManager().registerEvents(new PlayerJoinWhileInGame(this), this);
                Bukkit.getPluginManager().registerEvents(new DashAbility(), this);
                new OlzieScoreboardUtil();
                new DataBaseManager().setup();
        }

        @Override
        public void onDisable(){

        }


        private void instanceClasses(){
                instance = this;
                gameManager = new GameManager(this);
                commands = new Commands(this);
                commands.registerCommands();
        }

        private void loadConfig(){
                getConfig().options().copyDefaults(true);
                saveConfig();
        }



}
