package me.kmaxi.oneinthequiver.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class OlzieScoreboard {

    private HashMap<Player, OlzieScoreboardPlayerSidebar> players = new HashMap();
    private OlzieScoreboardGlobalSidebar globalSidebar;
    private Scoreboard bukkitScoreboard;
    private String name;
    private String id;

    OlzieScoreboard(Scoreboard bukkitScoreboard, String id, String name) {
        this.bukkitScoreboard = bukkitScoreboard;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setDefaultTitle(String name) {
        this.name = name;
    }

    public OlzieScoreboardGlobalSidebar getSidebar() {
        if (this.globalSidebar == null) {
            this.globalSidebar = new OlzieScoreboardGlobalSidebar(this);
        }

        this.players.forEach((player, sidebar) -> {
            if (!sidebar.isShowedGlobalSidebar()) {
                this.globalSidebar.showSidebar(player);
            }

        });
        return this.globalSidebar;
    }

    public OlzieScoreboardPlayerSidebar getSidebar(Player p) {
        if (!this.players.containsKey(p)) {
            this.addPlayer(p);
        }

        return this.players.get(p);
    }

    public Scoreboard getBukkitScoreboard() {
        return this.bukkitScoreboard;
    }

    public void addPlayer(Player p) {
        p.setScoreboard(this.bukkitScoreboard);
        if (!this.players.containsKey(p)) {
            this.players.put(p, new OlzieScoreboardPlayerSidebar(this, p));
        }

    }

    public void removePlayer(Player p) {
        this.getSidebar().hideSidebar(p);
        this.getSidebar(p).hideSidebar();
        this.players.remove(p);

    }

    public Collection<Player> getPlayers() {
        return this.players.keySet();
    }

    public HashMap<Player, OlzieScoreboardPlayerSidebar> getEntries() {
        return this.players;
    }

    public void destroy() {
        this.getSidebar().clearLines();
        this.getSidebar().hideSidebar();
        List<Player> tmp = new ArrayList(this.getPlayers());
        tmp.forEach(this::removePlayer);
    }
}
