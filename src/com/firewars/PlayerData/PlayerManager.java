package com.firewars.PlayerData;

import org.bukkit.entity.Player;

public class PlayerManager {

    private final Player player;
    private boolean isLighted;

    public PlayerManager(Player player, boolean isLighted) {
        this.player = player;
        this.isLighted = isLighted;
    }

    public Player getPlayer() {
        return player;
    }


    public boolean isLighted() {
        return isLighted;
    }

    public void setLighted(boolean lighted) {
        isLighted = lighted;
    }
}
