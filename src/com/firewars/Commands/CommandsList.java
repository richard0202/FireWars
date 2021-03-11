package com.firewars.Commands;

import com.firewars.Main;

public class CommandsList {

    private Main main = Main.getPlugin(Main.class);

    public String cmd1 = "setarena";
    public String cmd2 = "arestart";
    public String cmd3 = "start";

    public void registerCommands() {
        main.getCommand(cmd1).setExecutor(new SetArena());
        main.getCommand(cmd2).setExecutor(new Restart());
        main.getCommand(cmd3).setExecutor(new Restart());
    }

}
