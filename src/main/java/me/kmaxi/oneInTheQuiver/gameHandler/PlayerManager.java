package me.kmaxi.oneInTheQuiver.gameHandler;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerManager {
    public int kills;
    public boolean isInGame;
    private UUID uuid;
    public Player player;

    public PlayerManager(UUID uuid, Player player){
        this.uuid = uuid;
        this.isInGame = false;
        this.kills = 0;
        this.player = player;
    }

    public void addKill(){
        this.kills++;
    }

    public int getKills(){
        return kills;
    }


}
