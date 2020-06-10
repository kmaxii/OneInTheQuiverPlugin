package me.kmaxi.oneInTheQuiver.utils;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import me.kmaxi.oneInTheQuiver.gameHandler.PlayerManager;

import java.util.ArrayList;

public class GetPlayerInPosition {


    public static PlayerManager get(int position, OneInTheQuiverMain plugin) {
        ArrayList<PlayerManager> players = new ArrayList<>();
        int index = 0;
        for(PlayerManager playerManager : plugin.gameManager.playerManager.values()){

            players.add(playerManager);
            for (PlayerManager playerManager1: plugin.gameManager.playerManager.values()){
                if (playerManager1.kills > playerManager.kills){
                    if(players.contains(playerManager1)){
                        continue;
                    }
                    players.remove(playerManager);
                    players.add(playerManager1);
                    players.add(playerManager);
                }
            };
            index++;
        };
        if (players.size() >= position){
            return players.get(position);
        }
        return new PlayerManager(null, null);




    }
}
