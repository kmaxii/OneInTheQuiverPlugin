package me.kmaxi.oneinthequiver.gamehandler;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import me.kmaxi.oneinthequiver.scoreboard.ScoreboardFactory;
import me.kmaxi.oneinthequiver.utils.FireWork;
import me.kmaxi.oneinthequiver.utils.Score;
import me.kmaxi.oneinthequiver.utils.SpawnLocations;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameManager {
    private final OneInTheQuiverMain plugin;
    public boolean isStarted;
    public ArrayList<Location> spawnLocations;
    public HashMap<UUID, PlayerManager> playerManager;
    public Location spawn;
    public int gameTimeSeconds;
    public int gameTimeMinutes;
    public boolean isInGame;
    public ArrayList<PlayerManager> allPlayers;
    public ArrayList<Location> takenLocations;

    public GameManager(OneInTheQuiverMain plugin) {
        this.spawnLocations = new ArrayList<>();
        isStarted = false;
        isInGame = false;
        this.playerManager = new HashMap<>();
        this.plugin = plugin;
        this.allPlayers = new ArrayList<>();
        this.takenLocations = new ArrayList<>();
    }

    public void setUpGame() {
        spawnLocations = SpawnLocations.getLocations(plugin);
        if (!plugin.getConfig().contains("spawn")) {
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "Missing spawn point");
            return;
        }
        if (!plugin.getConfig().contains("spawnPointAmount")){
            Bukkit.broadcastMessage(ChatColor.RED + "Missing spawn point amout. Set it with /spawnpoints (Amount) and then set all the spawn points with /quiver point (number going from 1-spawn points amount)");
            return;
        }

        spawn = (Location) plugin.getConfig().get("spawn");
        gameTimeSeconds = 0;
        gameCountDown();


    }

    public void startGame() {
        isStarted = true;
        isInGame = true;
        gameTimeMinutes = 0;
        gameTimeSeconds = 0;
        Bukkit.getOnlinePlayers().forEach(x -> {
            playerManager.put(x.getUniqueId(), new PlayerManager(x.getUniqueId(), x));
            PlayerInventory inv = x.getInventory();
            inv.setHelmet(null);
            inv.setChestplate(null);
            inv.setLeggings(null);
            inv.setBoots(null);
            x.setGameMode(GameMode.ADVENTURE);
            x.setWalkSpeed(0.2f);
            Score.spawn(x, plugin);

        });

        takenLocations.clear();
        allPlayers.addAll(PlayerManager.getInstance().getPlayerManagers());

        Bukkit.getServer().getOnlinePlayers().forEach(ScoreboardFactory::createScoreboard);
    }

    public void gameCountDown() {
        isStarted = false;

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.teleport(spawn);
            player.setGameMode(GameMode.ADVENTURE);
        });
        new BukkitRunnable() {
            int time = 10;

            @Override
            public void run() {
                if (time > 0) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "The game starts in " + time + " seconds");
                    Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);
                    });
                    time--;
                } else {
                    startGame();
                    cancel();
                }

            }

        }.runTaskTimer(plugin, 0, 20);
    }


    public void endGame(PlayerManager winner) { //Ends the game
        PlayerManager playerManager = PlayerManager.getInstance();
        isStarted = false;
        Player winningPlayer = allPlayers.get(0).player; //Gets the winning player
        PlayerManager winningPlayerManager = playerManager.getPlayerManagerByUUID(winningPlayer.getUniqueId());
        allPlayers.forEach(playerManager1 -> {
            playerManager1.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100));
            playerManager1.player.sendTitle(winner.player.getName() + " is the winner!", "");
        });
        Bukkit.broadcastMessage(ChatColor.GREEN + "------------------------------------------------------");
        Bukkit.broadcastMessage(ChatColor.WHITE + "                               OneInTheQuiver                                  ");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "           Winner:" + ChatColor.GRAY + " - " + winner.player.getName() + " - " + winner.kills + "kills  -  " + ChatColor.GREEN + playerManager.getPlayerManagerByUUID(winner.player.getUniqueId()).getPoints() +   " +15 points");
        winningPlayerManager.addPoints(15);

        Player second = allPlayers.get(1).player;
        PlayerManager secondPlayermanager = playerManager.getPlayerManagerByUUID(second.getUniqueId());
        Bukkit.broadcastMessage(ChatColor.WHITE + "                                                                 ");
        Bukkit.broadcastMessage(ChatColor.GOLD + "           2th place:" + ChatColor.GRAY + " - " + allPlayers.get(1).player.getName() + " - " + allPlayers.get(1).kills + "kills  -  " + ChatColor.GREEN + secondPlayermanager.getPoints() +  " +10 points");
        secondPlayermanager.addPoints(10);



        if (allPlayers.size() >= 3) {
            Player third = allPlayers.get(2).player;
            PlayerManager thirdPlayermanager = playerManager.getPlayerManagerByUUID(third.getUniqueId());
            Bukkit.broadcastMessage(ChatColor.RED + "           3th place:" + ChatColor.GRAY + " - " + allPlayers.get(2).player.getName() + " - " + allPlayers.get(2).kills + "kills  -  " + ChatColor.GREEN + thirdPlayermanager.getPoints() +  " +5 points");
            thirdPlayermanager.addPoints(5);

        }
        if (allPlayers.size() >= 4) {
            Player fourth = allPlayers.get(3).player;
            PlayerManager fourthPlayerManager = playerManager.getPlayerManagerByUUID(fourth.getUniqueId());
            Bukkit.broadcastMessage(ChatColor.AQUA + "           4th place:" + ChatColor.GRAY + " - " + allPlayers.get(3).player.getName() + " - " + allPlayers.get(3).kills + "kills - " + ChatColor.GREEN + fourthPlayerManager.getPoints() +  " +3 points");
            fourthPlayerManager.addPoints(3);
        }
        Bukkit.broadcastMessage("      ");
        Bukkit.broadcastMessage(ChatColor.WHITE + "                  Do " + ChatColor.GOLD + "/quiver points" + ChatColor.WHITE +  " to check the leaderboard");
        Bukkit.broadcastMessage(ChatColor.GREEN + "------------------------------------------------------");



        //Announces the winner

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() { //Delays the whole end 7 seconds
                FireWork.launchFirework(winningPlayer);  //Launches fireworks from the player
                if (index >= 14) { //Checks if 7 seconds have gone by
                    isInGame = false; //Truely ends the game
                    allPlayers.forEach(playerManager1 -> { //Resets all the players
                        Player player = playerManager1.player;
                        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                        ScoreboardFactory.removeScoreboard(player);
                        player.getInventory().setHelmet(null);
                        player.getInventory().clear();
                        player.setPlayerListName(ChatColor.WHITE + player.getName());

                        player.teleport(spawn);
                        player.setGameMode(GameMode.ADVENTURE);
                    });
                    allPlayers.clear();
                    cancel();
                    return;
                }
                index++;
            }
        }.runTaskTimer(plugin, 0, 10);


    }


}
