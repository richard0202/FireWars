package com.firewars.Arena;


import org.bukkit.Location;

public class Arena {

    public String arenaName;
    public Location arenaSpawn;


    public Arena(String arenaName, Location arenaSpawn) {
        this.arenaName = arenaName;
        this.arenaSpawn = arenaSpawn;
    }

    public Location getArenaSpawn() {
        return arenaSpawn;
    }

    public void setArenaSpawn(Location arenaSpawn) {
        this.arenaSpawn = arenaSpawn;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }
}
