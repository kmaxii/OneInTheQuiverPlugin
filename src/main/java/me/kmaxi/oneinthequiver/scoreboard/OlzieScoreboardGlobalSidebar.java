package me.kmaxi.oneinthequiver.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class OlzieScoreboardGlobalSidebar {
    private ConcurrentHashMap<String, Integer> lines = new ConcurrentHashMap();
    private OlzieScoreboard OlzieScoreboard;
    private String displayName;

    OlzieScoreboardGlobalSidebar(OlzieScoreboard OlzieScoreboard) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', OlzieScoreboard.getName());
        this.OlzieScoreboard = OlzieScoreboard;
        this.OlzieScoreboard.getPlayers().forEach(this::showSidebar);
    }

    public void setDisplayName(String displayName) {
        displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        if (displayName.length() > 32) {
            System.out.println("[OlzieScoreboardUtil] Error! DisplayName is longer than 32 characters (" + displayName + ")");
        } else {
            if (!this.displayName.equals(displayName)) {

                for (Player p : this.OlzieScoreboard.getPlayers()) {
                    OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getSidebarPacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), displayName, 2));
                }

                this.displayName = displayName;
            }

        }
    }

    public void putLine(String key, int value) {

        for (Player p : this.OlzieScoreboard.getPlayers()) {
            OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getLinePacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), value, OlzieScoreboardPackets.EnumScoreboardAction.CHANGE));
        }

        this.lines.put(key, value);
    }

    public void putLines(HashMap<String, Integer> lines) {
        this.lines.forEach((key, value) -> {
            if (lines.containsKey(key) && (lines.get(key)).equals(value)) {
                lines.remove(key);
            }

        });

        for (Player p : this.OlzieScoreboard.getPlayers()) {
            lines.forEach((key, value) -> {
                OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getLinePacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), value, OlzieScoreboardPackets.EnumScoreboardAction.CHANGE));
            });
        }

        this.lines.putAll(lines);
    }

    public void rewriteLines(HashMap<String, Integer> lines) {
        this.lines.forEach((key, value) -> {
            if (!lines.containsKey(key)) {
                this.clearLine(key);
            }

        });
        lines.forEach((key, value) -> {
            if (!this.lines.containsKey(key) || !(this.lines.get(key)).equals(value)) {
                this.putLine(key, value);
            }

        });
    }

    public void clearLine(String key) {
        if (this.lines.containsKey(key)) {

            for (Player p : this.OlzieScoreboard.getPlayers()) {
                OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getLinePacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), 0, OlzieScoreboardPackets.EnumScoreboardAction.REMOVE));
            }

            this.lines.remove(key);
        }
    }

    public void clearLines() {

        for (Player p : this.OlzieScoreboard.getPlayers()) {
            this.lines.forEach((key, value) -> {
                OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getLinePacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), 0, OlzieScoreboardPackets.EnumScoreboardAction.REMOVE));
            });
        }

        this.lines.clear();
    }

    public void hideSidebar() {
        this.OlzieScoreboard.getPlayers().forEach(this::hideSidebar);
    }

    public void hideSidebar(Player p) {
        boolean showedGlobalSidebar = this.OlzieScoreboard.getSidebar(p).isShowedGlobalSidebar();
        if (showedGlobalSidebar) {
            this.OlzieScoreboard.getSidebar(p).setShowedGlobalSidebar(false);
            OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getSidebarPacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), this.displayName, 1));
        } else {
            this.showSidebar(p);
            this.hideSidebar(p);
        }
    }

    public void showSidebar() {
        this.OlzieScoreboard.getPlayers().forEach(this::showSidebar);
    }

    public void showSidebar(Player p) {
        boolean showedGlobalSidebar = this.OlzieScoreboard.getSidebar(p).isShowedGlobalSidebar();
        if (!showedGlobalSidebar) {
            (this.OlzieScoreboard.getEntries().get(p)).setShowedGlobalSidebar(true);
            OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getSidebarPacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), this.displayName, 0));
            OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getDisplayNamePacket(this.OlzieScoreboard.getId() + "." + p.getEntityId()));
            this.lines.forEach((key, value) -> {
                OlzieScoreboardPackets.sendPacket(p, OlzieScoreboardPackets.getLinePacket(this.OlzieScoreboard.getId() + "." + p.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), value, OlzieScoreboardPackets.EnumScoreboardAction.CHANGE));
            });
        } else {
            this.hideSidebar(p);
            this.showSidebar(p);
        }
    }

    public HashMap<String, Integer> getLines() {
        return new HashMap(this.lines);
    }
}
