package com.firewars.Commands;

import com.firewars.Arena.WaitingLobby;
import com.firewars.Main;
import com.firewars.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Restart implements CommandExecutor {

    private Main main = Main.getPlugin(Main.class);
    private CommandsList commandsList = main.commandsList;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase(commandsList.cmd2)){
            if (commandSender instanceof Player){
                Player sender = (Player) commandSender;
                if (sender.isOp()) {
                    Main.isStarted = false;
                    main.playerManager.clear();
                    main.playersInGame.clear();
                    main.killedPlayers.clear();
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        main.events.join(player);
                    });
                } else {
                    sender.sendMessage(Messages.notOp);
                }
            } else {
                Messages.onlyPlayersCanSendMessage(commandSender);
            }

        }

        if (command.getName().equalsIgnoreCase(commandsList.cmd3)){
            if (commandSender instanceof Player){
                Player sender = (Player) commandSender;
                if (sender.isOp()) {
                    int cost = WaitingLobby.neededPlayers;
                    WaitingLobby.neededPlayers = 0;
                    main.waitingLobby.playerCheck(10000);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                        @Override
                        public void run() {
                            WaitingLobby.neededPlayers = cost;
                        }
                    },100);
                } else {
                    sender.sendMessage(Messages.notOp);
                }
            } else {
                Messages.onlyPlayersCanSendMessage(commandSender);
            }

        }
        return true;
    }
}
