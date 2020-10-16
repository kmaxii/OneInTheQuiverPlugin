package me.kmaxi.oneinthequiver.gamehandler;

import me.kmaxi.oneinthequiver.managers.DataBaseManager;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {
    public int kills;
    public boolean isInGame;
    private UUID uuid;
    public Player player;
    public int killStreak;
    public int dashCooldown;
    public int dashes;
    private int points;
    public static PlayerManager instance;
    private List<PlayerManager> playerManagers;

    public PlayerManager(UUID uuid, Player player) {
        this.uuid = uuid;
        this.isInGame = false;
        this.kills = 0;
        this.player = player;
        this.killStreak = 0;
        this.dashCooldown = 0;
        this.dashes = 0;
        this.createDpProfile();
        getInstance().addPlayerManager(this);
    }

    public PlayerManager(){
        instance = this;
        this.playerManagers = new ArrayList<>();
    }

    public void addKill() {
        this.kills++;
        this.killStreak++;
    }

    public int getKills() {
        return kills;
    }

    private void createDpProfile() {
        try {
            Connection con = DataBaseManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT IGNORE into player_points(uuid, points) VALUES(?, ?)");
            ps.setString(1, this.uuid.toString());
            ps.setInt(2, 0);
            ps.executeUpdate();

            ps = con.prepareStatement("SELECT * FROM player_points WHERE uuid=?");
            ps.setString(1, this.uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int points = rs.getInt("points");
                this.points = points;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPoints(int amount) {
        this.points += amount;
        try {
            Connection con = DataBaseManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE player_points SET points=?");
            ps.setInt(1, this.points);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PlayerManager getInstance(){
        if (instance == null) new PlayerManager();
        return instance;
    }

    public void addPlayerManager(PlayerManager playerManager){
        this.playerManagers.add(playerManager);
    }

    public UUID getUuid(){
        return this.uuid;
    }

    public PlayerManager getPlayerManagerByUUID(UUID uuid){
        return getInstance().playerManagers.stream().filter(x -> x.getUuid() == uuid).findFirst().orElse(null);
    }

    public int getPoints(){
        return this.points;
    }

    public List<PlayerManager> getPlayerManagers(){
        return this.playerManagers;
    }


}
