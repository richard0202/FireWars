package com.firewars.Commands;

import com.firewars.Main;
import com.firewars.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetArena implements CommandExecutor {

    private Main main = Main.getPlugin(Main.class);
    private CommandsList commandsList = main.commandsList;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase(commandsList.cmd1)) {
            if (commandSender instanceof Player) {
                Player sender = (Player) commandSender;
                if (args.length == 1){
                    if (args[0].equalsIgnoreCase("lobby")){
                        Location location = sender.getLocation();
                        main.getConfig().set("arenalobby",location);
                        main.saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "Arena lobby has been set!");
                    } else if (args[0].equalsIgnoreCase("arena")) {
                        Location location = sender.getLocation();
                        main.getConfig().set("arenaspawn", location);
                        main.saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "Arena spawn has been set!");
                    } else {
                        Messages.invalidInput(sender);
                    }
                } else {
                    Messages.invalidInput(sender);
                }

            }else{
                Messages.onlyPlayersCanSendMessage(commandSender);
            }
        }
        return true;
    }

    // main.arenaManager.put(args[1],new Arena(args[1], location)); TODO DELETE
    //TODO ukládat arénu do configu, po reloadu se hashmapa smaže
    //TODO druhá možnost si lokaci a název brát z configu a potom až vytvořit objekt

}
