package com.firewars;

import com.firewars.Arena.WaitingLobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {

    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "FireWars" + ChatColor.DARK_GRAY + "] ";

    //Commands:
    public static String notOp = prefix + ChatColor.RED + "You can not use this command!";
    public static void invalidInput(Player sender) {
        sender.sendMessage(prefix + ChatColor.RED + "Invalid input!");
    }
    public static void onlyPlayersCanSendMessage(CommandSender commandSender) {
        commandSender.sendMessage(prefix + ChatColor.RED + "Only players can use this command!");
    }

    public static void victimLighted(Player victim, Player damager) {
        victim.sendMessage(prefix + ChatColor.GRAY + "You have been lighted by " +
                 ChatColor.YELLOW + damager.getName());
    }

    public static void damagerLighted(Player victim, Player damager) {
        damager.sendMessage(prefix + ChatColor.GRAY + "You have lighted " +
                ChatColor.YELLOW + victim.getName());
    }

    public static void currentPlayers(int playersInGame) {
        Bukkit.getServer().broadcastMessage(( prefix + ChatColor.YELLOW + "There are current " + ChatColor.WHITE +
                playersInGame + "/" + WaitingLobby.neededPlayers));
    }

    public static void startingIn(int waitingCountdown) {
        Bukkit.getServer().broadcastMessage(prefix + ChatColor.WHITE + "The game wil start in "
                + ChatColor.YELLOW + waitingCountdown + " seconds");
    }

    public static String playerLeft = prefix + ChatColor.GRAY + "Player has left, canceling countdown..";
    public static String gameHasStarted = prefix + ChatColor.GREEN + "Game has started, good luck!! :)";
    public static String killLighted = prefix + ChatColor.RED + "Lighted players have been killed!";
    public static String randomlyLighted = prefix + ChatColor.GRAY + "You have been randomly lighted!";
    public static String youHaveDied = prefix + ChatColor.GRAY + "You have died!";

    public static void playerKilled (Player player) {
        Bukkit.getServer().broadcastMessage(prefix + ChatColor.GRAY +"Player " +
                ChatColor.YELLOW + player.getName() + ChatColor.GRAY +" has been killed!");
    }

    public static void winner (String player) {
        Bukkit.getServer().broadcastMessage(prefix + ChatColor.GREEN +
                "Arena has ended, winner: " + ChatColor.YELLOW + player);
    }




}
