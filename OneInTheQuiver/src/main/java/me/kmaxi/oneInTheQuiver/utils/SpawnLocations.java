package me.kmaxi.oneInTheQuiver.utils;

import me.kmaxi.oneInTheQuiver.OneInTheQuiverMain;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class SpawnLocations {


    public static ArrayList<Location> getLocations(OneInTheQuiverMain plugin){
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 1; i < (int) plugin.getConfig().get("spawnPointAmount"); i++){
            Location loc = getSpawnPoint(i, plugin);
            locations.add(loc);
        }
        return locations;

    }

    public static Location getSpawnPoint(int point, OneInTheQuiverMain plugin) {
        Location location = null;
        if (plugin.getConfig().contains("point." + point)) {
            location = (Location) plugin.getConfig().get("point." + point);
        }
        return location;
    }
}
