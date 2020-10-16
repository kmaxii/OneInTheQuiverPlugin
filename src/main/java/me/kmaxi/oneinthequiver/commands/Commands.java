package me.kmaxi.oneinthequiver.commands;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;

public class Commands {

    public String cmd1 = "point";
    public String cmd2 = "setspawn";
    public String cmd3 = "start";

    private OneInTheQuiverMain plugin;

    public Commands(OneInTheQuiverMain plugin){
        this.plugin = plugin;
    }

    public void registerCommands(){
        plugin.getCommand("quiver").setExecutor(new QuiverCommands(plugin));


    }




}
