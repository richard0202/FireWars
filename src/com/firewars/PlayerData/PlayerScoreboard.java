package com.firewars.PlayerData;

import com.firewars.Arena.ArenaMechanics;
import com.firewars.Arena.WaitingLobby;
import com.firewars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerScoreboard {

    private final Main main = Main.getPlugin(Main.class);

    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private final Objective objective = scoreboard.registerNewObjective("FireWars", "");

    private final Team statusState = scoreboard.registerNewTeam("statusState");
    private final Team time = scoreboard.registerNewTeam("time");
    private final Team alive = scoreboard.registerNewTeam("alive");
    private final Team players = scoreboard.registerNewTeam("players");
    private final Team lighted = scoreboard.registerNewTeam("lighted");
    private final Team died = scoreboard.registerNewTeam("died");
    private final Team spectators = scoreboard.registerNewTeam("spectators");

    private final Score mezera2 = objective.getScore("  ");
    private final Score mezera1 = objective.getScore(" ");
    private final Score footer = objective.getScore(ChatColor.DARK_GRAY + "■■■■■■■■■■■■");


    public void setScoreBoard(Player player) {

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "FireWars");


        statusState.addEntry("");
        if (Main.isStarted){
            lengthMoreThan16(statusState,ChatColor.GRAY + "Status: "+ ChatColor.GREEN + "Running");
        } else {
            lengthMoreThan16(statusState,ChatColor.GRAY + "Status: "+ ChatColor.RED + "Wait§cing");
        }
        objective.getScore("").setScore(6);


        time.addEntry(ChatColor.WHITE + "");
        if (!Main.isStarted){
            lengthMoreThan16(time,ChatColor.GRAY + "Countdown: " + ChatColor.WHITE +
                    "00:0" + WaitingLobby.waitingCountdownConst);
        } else {
            lengthMoreThan16(time, ChatColor.GRAY + "Countdown: " + ChatColor.WHITE +
                    "00:" + ArenaMechanics.deathCountdownConst);
        }
        objective.getScore(ChatColor.WHITE + "").setScore(5);

        mezera1.setScore(4);

        alive.addEntry(ChatColor.GREEN + "");
        alive.setPrefix(ChatColor.WHITE + "Alive: " + ChatColor.GREEN + main.playersInGame.size());
        objective.getScore(ChatColor.GREEN + "").setScore(3);

        players.setPrefix(ChatColor.YELLOW + "");
        players.addEntry(player.getName());

        lighted.setPrefix(ChatColor.RED + "");

        died.setPrefix(ChatColor.GRAY + "");

        spectators.addEntry(ChatColor.GRAY + "");
        lengthMoreThan16(spectators, "§fSpectators: " + "§7" + main.killedPlayers.size());
        objective.getScore(ChatColor.GRAY + "").setScore(2);

        mezera2.setScore(1);

        footer.setScore(0);
        player.setScoreboard(scoreboard);
    }

    public void lengthMoreThan16(Team team, String string) {
        if (string.length() <= 16) {
            team.setPrefix(string);
            return;
        }
        if (string.length() > 32) {
            string = string.substring(32);
        }
        team.setPrefix(string.substring(0, 16));
        team.setSuffix(string.substring(16));
    }
}
