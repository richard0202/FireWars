package com.firewars;

import com.firewars.Arena.ArenaMechanics;
import com.firewars.Arena.WaitingLobby;
import com.firewars.Commands.CommandsList;
import com.firewars.PlayerData.PlayerManager;
import com.firewars.PlayerData.PlayerScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    public HashMap<UUID, PlayerManager> playerManager = new HashMap<>();
    public ArrayList<Player> playersInGame = new ArrayList<>();
    public ArrayList<Player> killedPlayers = new ArrayList<>();

    public static boolean isStarted;

    public CommandsList commandsList;
    public ArenaMechanics arenaMechanics;
    public PlayerScoreboard playerScoreboard;
    public Events events;
    public WaitingLobby waitingLobby;


    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin FireWars has been successfully loaded!");
        getServer().getPluginManager().registerEvents(new Events(), this);
        loadConfig();
        commandsList = new CommandsList();
        commandsList.registerCommands();
        arenaMechanics = new ArenaMechanics();
        playerScoreboard = new PlayerScoreboard();
        events = new Events();
        waitingLobby = new WaitingLobby();
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Plugin FireWars has been disabled!");
    }

}
