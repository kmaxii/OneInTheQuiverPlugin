package me.kmaxi.oneInTheQuiver.gameHandler;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.scoreBoard.InGameScoreboard;
import me.kmaxi.oneInTheQuiver.utils.FireWork;
import me.kmaxi.oneInTheQuiver.utils.Score;
import me.kmaxi.oneInTheQuiver.utils.SpawnLocations;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameManager {
    private OneInTheQuiverMain plugin;
    public boolean isStarted;
    public ArrayList<Location> spawnLocations;
    public HashMap<UUID, PlayerManager> playerManager;
    public Location spawn;
    private InGameScoreboard inGameScoreboard;
    public int gameTimeSeconds;
    public int gameTimeMinutes;
    public boolean isInGame;

    public GameManager(OneInTheQuiverMain plugin){
        this.spawnLocations = new ArrayList<>();
        isStarted = false;
        isInGame = false;
        this.playerManager = new HashMap<>();
        this.inGameScoreboard = new InGameScoreboard();
        this.plugin = plugin;
        this.gameTimeSeconds = 0;
        this.gameTimeMinutes = 0;
    }

    public void setUpGame(){
        spawnLocations = SpawnLocations.getLocations(plugin);
        if (!plugin.getConfig().contains("spawn")){
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "Missing spawn point");
            return;
        }
        spawn = (Location) plugin.getConfig().get("spawn");
        gameCountDown();
        return;



    }

    public void startGame(){
        isStarted = true;
        isInGame = true;
        for (Player player: Bukkit.getServer().getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            playerManager.put(uuid, new PlayerManager(uuid, player));
            player.getInventory().clear();
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            player.getInventory().addItem(new ItemStack(Material.BOW));
            player.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
            player.setGameMode(GameMode.ADVENTURE);
            Score.killed(player, plugin);
        };
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            inGameScoreboard.setBoard(player, plugin);
        });
        gameUpdater();
    }

    public void gameCountDown(){
        isStarted = false; //End the game mechanichs

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.teleport(spawn);
        });
        new BukkitRunnable(){
            int time = 10;
            @Override
            public void run(){

                    if (time > 0){
                        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                            player.sendTitle(time + " seconds", "");
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);
                        });
                        time--;
                    } else {
                        startGame();
                        cancel();
                        return;
                    }

            }

        }.runTaskTimer(plugin, 0, 20);
    }


    public void gameUpdater(){
        new BukkitRunnable(){
            @Override
            public void run(){
                if (gameTimeSeconds >= 59){
                    gameTimeMinutes++;
                    gameTimeSeconds = 0;
                } else {
                    gameTimeSeconds++;
                }
                playerManager.values().forEach(playerManager1 -> {
                    if(playerManager1.kills >= 20){
                        endGame(playerManager1);
                        cancel();
                        return;
                    }
                });
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void endGame(PlayerManager winner){ //Ends the game
        isStarted = false;
        Player winningPlayer = winner.player; //Gets the winning player
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + winningPlayer.getDisplayName() + " has won the game!"); //Announces the winner
        new BukkitRunnable(){
            int index = 0;
            @Override
            public void run(){ //Delays the whole end 7 seconds
                FireWork.launchFirework(winningPlayer);  //Launches fireworks from the player
                if (index >= 14){ //Checks if 7 seconds have gone by
                    isInGame = false; //Truely ends the game
                    playerManager.values().forEach(playerManager1 -> { //Resets all the players
                        Player player = playerManager1.player;
                        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                        player.getInventory().setHelmet(null);
                        player.getInventory().clear();
                        player.setPlayerListName(ChatColor.WHITE + player.getName());

                        player.teleport(spawn);
                        player.setGameMode(GameMode.ADVENTURE);
                    });
                    playerManager.clear();
                    cancel();
                    return;
                }
                index++;
            }
        }.runTaskTimer(plugin, 0, 10);


    }




}
