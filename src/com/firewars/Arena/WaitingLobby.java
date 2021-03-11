package com.firewars.Arena;

import com.firewars.Main;
import com.firewars.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class WaitingLobby {

    private final Main main = Main.getPlugin(Main.class);
    public boolean isStarting = false;
    public static int neededPlayers = 10;
    public static final int waitingCountdownConst = 5;
    private int waitingCountdown = waitingCountdownConst;

    public void playerJoin() {
        Messages.currentPlayers(main.playersInGame.size());
        playerCheck(main.playersInGame.size());
    }

    public boolean playerCheck(int onlinePlayers) {
        if (onlinePlayers >= neededPlayers) {
            if (!isStarting) {
                lobbyCountdown();
                isStarting = true;
            }
            return true;
        } else {
            isStarting = false;
            return false;
        }
    }

    public void lobbyCountdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (waitingCountdown > 0) {
                    if (playerCheck(main.playersInGame.size())) {
                        Messages.startingIn(waitingCountdown);
                        for (Player player : main.playersInGame) {
                            player.playSound(player.getLocation(), Sound.BLAZE_HIT, 2, 2);
                        }
                        updateWaitingCountdown();
                        waitingCountdown--;
                    } else {
                        Bukkit.getServer().broadcastMessage(Messages.playerLeft);
                        this.cancel();
                        waitingCountdown = waitingCountdownConst;
                    }
                } else {
                    this.cancel();
                    waitingCountdown = waitingCountdownConst;
                    arenaStart();
                    Bukkit.getServer().broadcastMessage(Messages.gameHasStarted);
                }
            }
        }.runTaskTimer(main, 0, 20);
    }

    public void arenaStart() {
        if (main.getConfig().contains("arenaspawn")) {
            Location arenaSpawn = (Location) main.getConfig().get("arenaspawn");
            main.playersInGame.forEach(player -> {
                player.teleport(arenaSpawn);
                player.setWalkSpeed(0.3f);
            });
            Main.isStarted = true;
            main.arenaMechanics.markLightedPlayers();
            updateState();
        } else {
            main.getServer().broadcastMessage(ChatColor.RED + "Arena spawn has not found!");
            Main.isStarted = false;
        }
    }

    public void updateWaitingCountdown() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Scoreboard scoreboard = player.getScoreboard();
            main.playerScoreboard.lengthMoreThan16(scoreboard.getTeam("time"),
                    ChatColor.GRAY + "Countdown: " + ChatColor.WHITE +
                    "00:0" + waitingCountdown);
        });
    }

    public void updateState() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Scoreboard scoreboard = player.getScoreboard();
            main.playerScoreboard.lengthMoreThan16(scoreboard.getTeam("statusState"),
                    ChatColor.GRAY + "Status: "+ ChatColor.GREEN + "Running");
        });
    }
}
