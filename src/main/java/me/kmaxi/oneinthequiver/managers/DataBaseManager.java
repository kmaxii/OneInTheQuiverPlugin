package me.kmaxi.oneinthequiver.managers;

import me.kmaxi.oneinthequiver.utils.Info;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataBaseManager {
    private Connection connection;
    public static DataBaseManager instance;

    public DataBaseManager() {
        instance = this;
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + Info.DB_HOSTNAME + ":" + Info.DB_PORT + "/" + Info.DB_NAME + "?autoReconnect=true&serverTimezone=EST", Info.DB_USERNAME, Info.DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) this.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return this.connection;
    }

    public void setup() {
        try {
            Connection con = this.getConnection();
            con.prepareStatement("CREATE TABLE IF NOT EXISTS player_points(" +
                    "uuid CHAR(36)," +
                    "points INT," +
                    "PRIMARY KEY(uuid))"
            ).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataBaseManager getInstance() {
        if (instance == null) new DataBaseManager();
        return instance;
    }

    public void reset() {
        try {
            Connection con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("DROP TABLE ?");
            ps.setString(1, "player_points");
            ps.executeUpdate();
            this.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UUID> getRegisterdUUIDS() {
        List<UUID> uuidList = new ArrayList<>();
        try {
            Connection con = this.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT uuid FROM player_points");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                uuidList.add(UUID.fromString(rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uuidList;
    }


}
