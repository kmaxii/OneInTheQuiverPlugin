package me.kmaxi.oneInTheQuiver.scoreBoard;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.gameHandler.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class InGameScoreboard {

    public void setBoard(Player player, OneInTheQuiverMain plugin){
        PlayerManager playerManager = plugin.gameManager.playerManager.get(player.getUniqueId());
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("OneInTheQuiver", "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.RED + "OneInTheQuiver");

        Team time = board.registerNewTeam("time");
        time.addEntry(ChatColor.GRAY.toString());
        setTeamString(time, ChatColor.GRAY + "Game time: " + plugin.gameManager.gameTimeMinutes + ":" + plugin.gameManager.gameTimeSeconds);
        objective.getScore(ChatColor.GRAY.toString()).setScore(12);

        Score firstTo20Kills = objective.getScore(ChatColor.YELLOW + "First to 20 kills wins");
        firstTo20Kills.setScore(11);

        Team playerScore = board.registerNewTeam("playerScore");
        playerScore.addEntry(ChatColor.AQUA.toString());
        setTeamString(playerScore, ChatColor.YELLOW + "Your score: " + ChatColor.GOLD + playerManager.kills);
        objective.getScore(ChatColor.AQUA.toString()).setScore(10);

        Score empty = objective.getScore(" ");
        empty.setScore(9);

        Team playerOne = board.registerNewTeam("first");
        playerOne.addEntry(ChatColor.DARK_GREEN.toString());
        setTeamString(playerScore, ChatColor.GRAY + "1: " + plugin.gameManager.allPlayers.get(0) + ": " + plugin.gameManager.allPlayers.get(0).kills);
        objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(8);

        Team playerTwo = board.registerNewTeam("second");
        playerTwo.addEntry(ChatColor.LIGHT_PURPLE.toString());
        setTeamString(playerScore, ChatColor.GRAY + "2: " + plugin.gameManager.allPlayers.get(1).player.getName() + ": " + plugin.gameManager.allPlayers.get(1).kills);
        objective.getScore(ChatColor.LIGHT_PURPLE.toString()).setScore(7);

        if(Bukkit.getServer().getOnlinePlayers().size() > 2) {
            Team playerThree = board.registerNewTeam("third");
            playerThree.addEntry(ChatColor.RED.toString());
            setTeamString(playerScore, ChatColor.GRAY + "3: " + plugin.gameManager.allPlayers.get(2).player.getName() + ": " + plugin.gameManager.allPlayers.get(2).kills);
            objective.getScore(ChatColor.RED.toString()).setScore(6);
        }

        if(Bukkit.getServer().getOnlinePlayers().size() > 3) {
            Team playerFour = board.registerNewTeam("fourth");
            playerFour.addEntry(ChatColor.DARK_PURPLE.toString());
            setTeamString(playerScore, ChatColor.GRAY + "4: " + plugin.gameManager.allPlayers.get(3)+ ": " + plugin.gameManager.allPlayers.get(3).kills);
            objective.getScore(ChatColor.DARK_PURPLE.toString()).setScore(5);
        }
        if(Bukkit.getServer().getOnlinePlayers().size() > 4) {
            Team playerFive = board.registerNewTeam("fifth");
            playerFive.addEntry(ChatColor.DARK_BLUE.toString());
            setTeamString(playerScore, ChatColor.GRAY + "5: " + plugin.gameManager.allPlayers.get(4).player.getName() + ": " + plugin.gameManager.allPlayers.get(4).kills);
            objective.getScore(ChatColor.DARK_BLUE.toString()).setScore(4);
        }
        if(Bukkit.getServer().getOnlinePlayers().size() > 5) {
            Team playerFive = board.registerNewTeam("sixth");
            playerFive.addEntry(ChatColor.GOLD.toString());
            setTeamString(playerScore, ChatColor.GRAY + "6: " + plugin.gameManager.allPlayers.get(5).player.getName() + ": " + plugin.gameManager.allPlayers.get(5).kills);
            objective.getScore(ChatColor.GOLD.toString()).setScore(3);
        }
        if(Bukkit.getServer().getOnlinePlayers().size() > 6) {
            Team playerFive = board.registerNewTeam("seventh");
            playerFive.addEntry(ChatColor.YELLOW.toString());
            setTeamString(playerScore, ChatColor.GRAY + "7: " + plugin.gameManager.allPlayers.get(6).player.getName() + ": " + plugin.gameManager.allPlayers.get(6).kills);
            objective.getScore(ChatColor.YELLOW.toString()).setScore(2);
        }


        Score empty1 = objective.getScore("  ");
        empty1.setScore(1);

        Score server = objective.getScore(ChatColor.YELLOW + "kmaxi.mcserv.me");
        server.setScore(0);

        player.setScoreboard(board);

        updateScoreboard(player, playerManager, plugin);


    }

    private static void setTeamString(Team team, String text) {
        String s = text;
        if (s.length() <= 16) {
            team.setPrefix(s);
            return;
        }
        if (s.length() > 32) {
            s = s.substring(32);
            return;
        }
        team.setPrefix(s.substring(0, 16));
        team.setSuffix(s.substring(16));
    }

    private void updateScoreboard(Player player, PlayerManager playerManager, OneInTheQuiverMain plugin) {
        new BukkitRunnable(){
            Scoreboard scoreboard = player.getScoreboard();

            @Override
            public void run() {
                if(!(player.isOnline() || plugin.gameManager.isInGame)) {
                    player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                    player.sendMessage("You scoreboard has been cleared");
                    cancel();
                    return;
                }
                setTeamString(scoreboard.getTeam("time"), ChatColor.GRAY + "Game time: " + plugin.gameManager.gameTimeMinutes + ":" + plugin.gameManager.gameTimeSeconds);

                setTeamString(scoreboard.getTeam("playerScore"), ChatColor.YELLOW + "Your score: " + ChatColor.GOLD + playerManager.kills);
                if (plugin.gameManager.playerManager.size() >= 1) {
                    PlayerManager player = plugin.gameManager.allPlayers.get(0);
                    setTeamString(scoreboard.getTeam("first"), ChatColor.GRAY + "1: " + player.player.getName() + ": " + player.kills);
                }
                if (plugin.gameManager.playerManager.size() >= 2) {
                    PlayerManager player = plugin.gameManager.allPlayers.get(1);
                    setTeamString(scoreboard.getTeam("second"), ChatColor.GRAY + "2: " + player.player.getName() + ": " + player.kills);
                }
                if(plugin.gameManager.playerManager.size() > 2) {
                    PlayerManager player = plugin.gameManager.allPlayers.get(2);
                    setTeamString(scoreboard.getTeam("third"), ChatColor.GRAY + "3: " + player.player.getName() + ": " + player.kills);
                }
                if(plugin.gameManager.playerManager.size()> 3) {
                    PlayerManager player = plugin.gameManager.allPlayers.get(3);
                    setTeamString(scoreboard.getTeam("fourth"), ChatColor.GRAY + "4: " + player.player.getName() + ": " + player.kills);
                }
                if(plugin.gameManager.playerManager.size() > 4) {
                    PlayerManager player = plugin.gameManager.allPlayers.get(4);
                    setTeamString(scoreboard.getTeam("fifth"), ChatColor.GRAY + "5: " + player.player.getName() + ": " + player.kills);
                }
                if((plugin.gameManager.playerManager.size() >  5)){
                    PlayerManager player = plugin.gameManager.allPlayers.get(5);
                    setTeamString(scoreboard.getTeam("sixth"), ChatColor.GRAY + "6: " +player.player.getName() + ": " + player.kills);
                }
                if((plugin.gameManager.playerManager.size() >  6)){
                    PlayerManager player = plugin.gameManager.allPlayers.get(6);
                    setTeamString(scoreboard.getTeam("seventh"), ChatColor.GRAY + "7: " + player.player.getName() + ": " + player.kills);
                }
            }
        }.runTaskTimer(plugin, 0, 10);

    };



}
