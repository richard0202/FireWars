package com.firewars;

import com.firewars.PlayerData.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.UUID;

public class Events implements Listener {

    private final Main main = Main.getPlugin(Main.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");
        join(player);
    }

    public void join(Player player) {
        UUID uuid = player.getUniqueId();
        player.setGameMode(GameMode.SURVIVAL);
        player.setWalkSpeed(0.2f);
        PlayerManager newPlayer = new PlayerManager(player, false);
        main.playerManager.put(uuid, newPlayer);
        main.playersInGame.add(player);
        if (main.getConfig().contains("arenalobby")) {
            Location arenaLobby = (Location) main.getConfig().get("arenalobby");
            player.teleport(arenaLobby);
            if (!Main.isStarted) {
                main.waitingLobby.playerJoin();
                player.setPlayerListName(ChatColor.YELLOW + player.getName());
            } else {
                player.sendMessage("Cannot join, the game has already started!");
            }
        } else {
            main.getServer().broadcastMessage(ChatColor.RED + "Arena lobby has not found!");
        }
        main.playerScoreboard.setScoreBoard(player);
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        main.playersInGame.remove(player);
        main.playerManager.remove(uuid);
        player.getInventory().clear();
        player.setWalkSpeed(0.2f);
        main.arenaMechanics.updateScoreboard();
    }

    @EventHandler
    public void lightPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            PlayerManager damagerManager = main.playerManager.get(damager.getUniqueId());
            PlayerManager victimManager = main.playerManager.get(victim.getUniqueId());

            event.setDamage(0);

            if (damagerManager.isLighted() && !victimManager.isLighted()) {
                main.arenaMechanics.lightPlayer(victim);
                Messages.victimLighted(victim, damager);
                victim.playSound(victim.getLocation(), Sound.FIRE_IGNITE, 1, 1);

                main.arenaMechanics.unLightPlayer(damager);
                Messages.damagerLighted(victim, damager);
                damager.playSound(damager.getLocation(), Sound.LEVEL_UP, 1, 1);
            }
        }
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerDropItemEvent(PlayerDropItemEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
            event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            event.setCancelled(true);
        }
    }
}
