package com.firewars.Arena;

import com.firewars.Main;
import com.firewars.Messages;
import com.firewars.PlayerData.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Random;

public class ArenaMechanics {

    private final Main main = Main.getPlugin(Main.class);
    public static final int deathCountdownConst = 20;
    private int deathCountdown = deathCountdownConst;
    private final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
    private final ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
    private final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

    public ArenaMechanics() {
        LeatherArmorMeta meta1 = (LeatherArmorMeta) boots.getItemMeta();
        meta1.setColor(Color.RED);
        meta1.spigot().setUnbreakable(true);
        boots.setItemMeta(meta1);

        LeatherArmorMeta meta2 = (LeatherArmorMeta) leggings.getItemMeta();
        meta2.setColor(Color.RED);
        meta2.spigot().setUnbreakable(true);
        leggings.setItemMeta(meta2);

        LeatherArmorMeta meta3 = (LeatherArmorMeta) chestplate.getItemMeta();
        meta3.setColor(Color.RED);
        meta3.spigot().setUnbreakable(true);
        chestplate.setItemMeta(meta3);
    }

    public void markLightedPlayers() {
        deathCountdown = deathCountdownConst;
        Random random = new Random();
        int number = main.playersInGame.size() / 2;

        for (int i = 1; number >= i; i++) {
            int randomNumber = random.nextInt(main.playersInGame.size());
            Player player = main.playersInGame.get(randomNumber);
            lightPlayer(player);
            player.sendMessage(Messages.randomlyLighted);
        }
        deathCountdown();
    }

    public void lightPlayer(Player player) {
        main.playerManager.get(player.getUniqueId()).setLighted(true);
        player.getInventory().addItem(new ItemStack(Material.BLAZE_POWDER, 576));
        player.getScoreboard().getTeam("players").removeEntry(player.getName());
        player.getScoreboard().getTeam("lighted").addEntry(player.getName());
        player.setPlayerListName(ChatColor.RED + player.getName());
        player.setFireTicks(2000);
        player.setWalkSpeed(0.4f);
        player.getInventory().setHelmet(new ItemStack(Material.GOLD_BLOCK));
        player.getInventory().setBoots(boots);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setChestplate(chestplate);
    }

    public void unLightPlayer(Player player) {
        main.playerManager.get(player.getUniqueId()).setLighted(false);
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
        player.setPlayerListName(ChatColor.YELLOW + player.getName());
        player.setFireTicks(0);
        player.setWalkSpeed(0.3f);
        player.getScoreboard().getTeam("lighted").removeEntry(player.getName());
        player.getScoreboard().getTeam("players").addEntry(player.getName());
    }

    public void deathCountdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (deathCountdown > 0) {
                    updateCountdown();
                    deathCountdown--;
                } else {
                    this.cancel();
                    killLightedPlayers();
                    if (main.playersInGame.size() == 1) {
                        endGame();
                    } else {
                        markLightedPlayers();
                    }
                }
            }
        }.runTaskTimer(main, 0, 20);
    }

    public void killLightedPlayers() {
        for (Player player : main.playersInGame) {
            if (main.playerManager.get(player.getUniqueId()).isLighted()) {
                PlayerManager lightedPlayer = main.playerManager.get(player.getUniqueId());
                lightedPlayer.setLighted(false);
                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                player.getInventory().setChestplate(new ItemStack(Material.AIR));
                player.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
                player.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
                player.sendMessage(Messages.youHaveDied);
                Messages.playerKilled(player);
                player.getInventory().clear();
                player.setGameMode(GameMode.SPECTATOR);
                player.getScoreboard().getTeam("lighted").removeEntry(player.getName());
                player.getScoreboard().getTeam("died").addEntry(player.getName());
                player.setPlayerListName(ChatColor.GRAY + player.getName());
                main.killedPlayers.add(player);
            }
            player.playSound(player.getLocation(), Sound.VILLAGER_DEATH,1,1);
        }
        main.killedPlayers.forEach(player -> {
            main.playersInGame.remove(player);
        });
        updateScoreboard();
        updateCountdown();
        Bukkit.getServer().broadcastMessage(Messages.killLighted);
    }

    public void endGame() {
        Messages.winner(main.playersInGame.get(0).getName());
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
        });
    }

    public void updateScoreboard() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Scoreboard scoreboard = player.getScoreboard();
            scoreboard.getTeam("alive").setPrefix(ChatColor.WHITE + "Alive: " + ChatColor.GREEN +
                    main.playersInGame.size());
            main.playerScoreboard.lengthMoreThan16(scoreboard.getTeam("spectators"),
                    "§fSpectators: " + "§7" + main.killedPlayers.size());
        });
    }

    public void updateCountdown() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Scoreboard scoreboard = player.getScoreboard();
            if (deathCountdown > 9) {
                main.playerScoreboard.lengthMoreThan16(scoreboard.getTeam("time"),
                        ChatColor.GRAY + "Countdown: " + ChatColor.WHITE +
                                "00:" + deathCountdown);
            } else {
                main.playerScoreboard.lengthMoreThan16(scoreboard.getTeam("time"),
                        ChatColor.GRAY + "Countdown: " + ChatColor.WHITE +
                                "00:0" + deathCountdown);
            }
        });
    }
}
