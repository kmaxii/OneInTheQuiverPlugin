package me.kmaxi.oneinthequiver.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Random;


public class OlzieScoreboardUtil {
    private static Scoreboard bukkitScoreboard;
    private static HashMap<String, OlzieScoreboard> scoreboards = new HashMap();
    private static int packetIterator;
    private static int nameIterator;
    private static Random random;
    private static int serverStart;

    static {
        packetIterator = 0;
        nameIterator = 0;
        random = new Random();
        serverStart = 0;
    }

    public OlzieScoreboardUtil() {
        bukkitScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        serverStart = random.nextInt(100);
    }

    public static OlzieScoreboard getOlzieScoreboard(String name) {
        if (!scoreboards.containsKey(name)) {
            scoreboards.put(name, new OlzieScoreboard(bukkitScoreboard, serverStart + "." + getPacketIterator(), name));
        }

        return scoreboards.get(name);
    }

    public static OlzieScoreboard getNextOlzieScoreboard() {
        return getOlzieScoreboard(nameIterator++ + "");
    }


    private static int getPacketIterator() {
        return packetIterator++;
    }
}
