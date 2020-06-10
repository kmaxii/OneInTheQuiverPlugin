package me.kmaxi.oneInTheQuiver.scoreBoard;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.gameHandler.PlayerManager;
import me.kmaxi.oneInTheQuiver.utils.GetPlayerInPosition;
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
        setTeamString(time, ChatColor.GRAY + "Game time: (" + plugin.gameManager.gameTimeMinutes + ", " + plugin.gameManager.gameTimeSeconds + ")");
        objective.getScore(ChatColor.GRAY.toString()).setScore(12);

        Score firstTo20Kills = objective.getScore(ChatColor.YELLOW + "First to 20 kills wins");
        firstTo20Kills.setScore(11);

        Team playerScore = board.registerNewTeam("playerScore");
        playerScore.addEntry(ChatColor.AQUA.toString());
        setTeamString(playerScore, ChatColor.YELLOW + "Your score: " + ChatColor.GOLD + playerManager.kills);
        objective.getScore(ChatColor.AQUA.toString()).setScore(10);

        Score empty = objective.getScore(" ");
        empty.setScore(9);

        Team playerOne = board.registerNewTeam("firstPlayer");
        playerOne.addEntry(ChatColor.GOLD.toString());
        setTeamString(playerScore, ChatColor.GRAY + GetPlayerInPosition.get(0, plugin).player.getName() + ": " + GetPlayerInPosition.get(0, plugin).kills);
        objective.getScore(ChatColor.GOLD.toString()).setScore(8);

        Team playerTwo = board.registerNewTeam("secondPlayer");
        playerTwo.addEntry(ChatColor.LIGHT_PURPLE.toString());
        setTeamString(playerScore, ChatColor.GRAY + GetPlayerInPosition.get(1, plugin).player.getName() + ": " + GetPlayerInPosition.get(1, plugin).kills);
        objective.getScore(ChatColor.LIGHT_PURPLE.toString()).setScore(7);

        if(Bukkit.getServer().getOnlinePlayers().size() > 2) {
            Team playerThree = board.registerNewTeam("thirdPlayer");
            playerThree.addEntry(ChatColor.RED.toString());
            setTeamString(playerScore, ChatColor.GRAY + GetPlayerInPosition.get(2, plugin).player.getName() + ": " + GetPlayerInPosition.get(2, plugin).kills);
            objective.getScore(ChatColor.RED.toString()).setScore(6);
        }

        if(Bukkit.getServer().getOnlinePlayers().size() > 3) {
            Team playerFour = board.registerNewTeam("fourthPlayer");
            playerFour.addEntry(ChatColor.DARK_PURPLE.toString());
            setTeamString(playerScore, ChatColor.GRAY + GetPlayerInPosition.get(3, plugin).player.getName() + ": " + GetPlayerInPosition.get(3, plugin).kills);
            objective.getScore(ChatColor.DARK_PURPLE.toString()).setScore(5);
        }
        if(Bukkit.getServer().getOnlinePlayers().size() > 4) {
            Team playerFive = board.registerNewTeam("fifthPlayer");
            playerFive.addEntry(ChatColor.DARK_BLUE.toString());
            setTeamString(playerScore, ChatColor.GRAY + GetPlayerInPosition.get(4, plugin).player.getName() + ": " + GetPlayerInPosition.get(4, plugin).kills);
            objective.getScore(ChatColor.DARK_BLUE.toString()).setScore(4);
        }

        Score empty1 = objective.getScore("  ");
        empty1.setScore(3);

        Score server = objective.getScore(ChatColor.YELLOW + "kmaxi.mcserv.me");
        server.setScore(2);

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
                setTeamString(scoreboard.getTeam("time"), ChatColor.GRAY + "Game time: (" + plugin.gameManager.gameTimeMinutes + ", " + plugin.gameManager.gameTimeSeconds + ")");

                setTeamString(scoreboard.getTeam("playerScore"), ChatColor.YELLOW + "Your score: " + ChatColor.GOLD + playerManager.kills);
                if (plugin.gameManager.playerManager.size() >= 1) {
                    setTeamString(scoreboard.getTeam("firstPlayer"), ChatColor.GRAY + GetPlayerInPosition.get(0, plugin).player.getName() + ": " + GetPlayerInPosition.get(0, plugin).kills);
                }
                if (plugin.gameManager.playerManager.size() >= 2) {
                    setTeamString(scoreboard.getTeam("secondPlayer"), ChatColor.GRAY + GetPlayerInPosition.get(1, plugin).player.getName() + ": " + GetPlayerInPosition.get(1, plugin).kills);
                }
                if(plugin.gameManager.playerManager.size() > 2) {
                    setTeamString(scoreboard.getTeam("thirdPlayer"), ChatColor.GRAY + GetPlayerInPosition.get(2, plugin).player.getName() + ": " + GetPlayerInPosition.get(2, plugin).kills);
                }
                if(plugin.gameManager.playerManager.size()> 3) {
                    setTeamString(scoreboard.getTeam("fourthPlayer"), ChatColor.GRAY + GetPlayerInPosition.get(3, plugin).player.getName() + ": " + GetPlayerInPosition.get(3, plugin).kills);
                }
                if(plugin.gameManager.playerManager.size() > 4) {
                    setTeamString(scoreboard.getTeam("fifthPlayer"), ChatColor.GRAY + GetPlayerInPosition.get(4, plugin).player.getName() + ": " + GetPlayerInPosition.get(4, plugin).kills);
                }
            }
        }.runTaskTimer(plugin, 0, 10);

    };



}
