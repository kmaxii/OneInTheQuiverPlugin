package me.kmaxi.oneinthequiver.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class OlzieScoreboardPlayerSidebar {
    private final OlzieScoreboard olzieScoreboard;
    private final Player player;
    private ConcurrentHashMap<String, Integer> lines = new ConcurrentHashMap();
    private String displayName;
    private boolean showedGlobalSidebar = false;
    private boolean showedPrivateSidebar = false;

    OlzieScoreboardPlayerSidebar(OlzieScoreboard olzieScoreboard, Player player) {
        this.olzieScoreboard = olzieScoreboard;
        this.player = player;
        this.displayName = ChatColor.translateAlternateColorCodes('&', olzieScoreboard.getName());
        this.showSidebar();
    }

    public void setDisplayName(String displayName) {
        displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        if (displayName.length() > 32) {
            System.out.println("[OlzieScoreboardUtil] Error! DisplayName is longer than 32 characters (" + displayName + ")");
        } else {
            if (!this.displayName.equals(displayName)) {
                OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getSidebarPacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), displayName, 2));
                this.displayName = displayName;
            }

        }
    }

    public void putLine(String key, int value) {
        OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getLinePacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), value, OlzieScoreboardPackets.EnumScoreboardAction.CHANGE));
        this.lines.put(key, value);
    }

    public void putLines(HashMap<String, Integer> lines) {
        this.lines.forEach((key, value) -> {
            if (lines.containsKey(key) && (lines.get(key)).equals(value)) {
                lines.remove(key);
            }

        });
        lines.forEach((key, value) -> {
            OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getLinePacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), value, OlzieScoreboardPackets.EnumScoreboardAction.CHANGE));
        });
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
        OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getLinePacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), 0, OlzieScoreboardPackets.EnumScoreboardAction.REMOVE));
        this.lines.remove(key);
    }

    public void clearLines() {
        this.lines.forEach((key, value) -> {
            OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getLinePacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), 0, OlzieScoreboardPackets.EnumScoreboardAction.REMOVE));
        });
        this.lines.clear();
    }

    public void hideSidebar() {
        if (this.showedPrivateSidebar) {
            this.showedPrivateSidebar = false;
            OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getSidebarPacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), this.displayName, 1));
        } else {
            this.showSidebar();
            this.hideSidebar();
        }
    }

    public void showSidebar() {
        if (!this.showedPrivateSidebar) {
            this.showedPrivateSidebar = true;
            OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getSidebarPacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), this.displayName, 0));
            OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getDisplayNamePacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId()));
            this.lines.forEach((key, value) -> {
                OlzieScoreboardPackets.sendPacket(this.player, OlzieScoreboardPackets.getLinePacket(this.olzieScoreboard.getId() + ":" + this.player.getEntityId(), ChatColor.translateAlternateColorCodes('&', key), value, OlzieScoreboardPackets.EnumScoreboardAction.CHANGE));
            });
        } else {
            this.hideSidebar();
            this.showSidebar();
        }
    }

    public HashMap<String, Integer> getLines() {
        return new HashMap(this.lines);
    }

    public boolean isShowedGlobalSidebar() {
        return this.showedGlobalSidebar;
    }

    public void setShowedGlobalSidebar(boolean bool) {
        this.showedGlobalSidebar = bool;
    }

    public boolean isShowedPrivateSidebar() {
        return this.showedPrivateSidebar;
    }
}
