package me.kmaxi.oneinthequiver.scoreboard;

import me.kmaxi.oneinthequiver.OneInTheQuiverMain;
import me.kmaxi.oneinthequiver.gamehandler.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ScoreboardFactory {

    private static OneInTheQuiverMain plugin = OneInTheQuiverMain.instance;

    private static HashMap<Player, OlzieScoreboardPlayerSidebar> scoreboards = new HashMap<>();

    public static void createScoreboard(Player p) {
        OlzieScoreboard OlzieScoreboard = OlzieScoreboardUtil.getNextOlzieScoreboard();
        OlzieScoreboard.addPlayer(p);

        OlzieScoreboardPlayerSidebar sidebar = OlzieScoreboard.getSidebar(p);
        updateScoreboard(p, sidebar);
    }

    private static void updateScoreboard(final Player p, OlzieScoreboardPlayerSidebar sidebar) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline() || !plugin.gameManager.isInGame) {
                    removeScoreboard(p);
                    cancel();
                    return;
                }

                sidebar.setDisplayName("OneInTheQuiver");
                PlayerManager playerManager = plugin.gameManager.playerManager.get(p.getUniqueId());

                HashMap<String, Integer> lines = new HashMap<>();
                lines.put(ChatColor.YELLOW + "Your points: " + ChatColor.GOLD + playerManager.getPlayerManagerByUUID(p.getUniqueId()).getPoints(), 14);
                lines.put(ChatColor.YELLOW + "First to 30 kills wins", 13);
                lines.put(ChatColor.YELLOW + "Kills: " + ChatColor.GOLD + playerManager.kills, 12);
                if (plugin.gameManager.playerManager.containsKey(p.getUniqueId())) {
                    lines.put(ChatColor.YELLOW + "Killstreak: " + ChatColor.GOLD + plugin.gameManager.playerManager.get(p.getUniqueId()).killStreak, 11);
                }
                lines.put(ChatColor.YELLOW + "Leaps: " + ChatColor.GOLD + playerManager.dashes + "/3" , 10);
                lines.put(" ", 9);


                int playersInGame = plugin.gameManager.playerManager.size();
                if (playersInGame > 8) playersInGame = 8;
                switch (playersInGame) {
                    case 8:
                        lines.put(ChatColor.GRAY + "8: " + plugin.gameManager.allPlayers.get(7).player.getName() + ": " + plugin.gameManager.allPlayers.get(7).kills, 1);
                    case 7:
                        lines.put(ChatColor.GRAY + "7: " + plugin.gameManager.allPlayers.get(6).player.getName() + ": " + plugin.gameManager.allPlayers.get(6).kills, 2);
                    case 6:
                        lines.put(ChatColor.GRAY + "6: " + plugin.gameManager.allPlayers.get(5).player.getName() + ": " + plugin.gameManager.allPlayers.get(5).kills, 3);
                    case 5:
                        lines.put(ChatColor.GRAY + "5: " + plugin.gameManager.allPlayers.get(4).player.getName() + ": " + plugin.gameManager.allPlayers.get(4).kills, 4);
                    case 4:
                        lines.put(ChatColor.GRAY + "4: " + plugin.gameManager.allPlayers.get(3).player.getName() + ": " + plugin.gameManager.allPlayers.get(3).kills, 5);
                    case 3:
                        lines.put(ChatColor.GRAY + "3: " + plugin.gameManager.allPlayers.get(2).player.getName() + ": " + plugin.gameManager.allPlayers.get(2).kills, 6);
                    case 2:
                        lines.put(ChatColor.GRAY + "2: " + plugin.gameManager.allPlayers.get(1).player.getName() + ": " + plugin.gameManager.allPlayers.get(1).kills, 7);
                    case 1:
                        lines.put(ChatColor.GRAY + "1: " + plugin.gameManager.allPlayers.get(0).player.getName() + ": " + plugin.gameManager.allPlayers.get(0).kills, 8);
                }
                lines.put(ChatColor.YELLOW + "Minecraft fridays", 0);


                sidebar.rewriteLines(lines);
                scoreboards.put(p, sidebar);
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public static boolean hasShowedSidebar(Player p) {
        if (!p.isOnline()) return false;

        OlzieScoreboardPlayerSidebar sidebar = scoreboards.get(p);

        if (sidebar == null) return false;

        return sidebar.isShowedPrivateSidebar();
    }

    public static void removeScoreboard(Player p) {
        if (!p.isOnline()) return;
        OlzieScoreboardUtil.getNextOlzieScoreboard().removePlayer(p);
        scoreboards.remove(p);
    }

}
