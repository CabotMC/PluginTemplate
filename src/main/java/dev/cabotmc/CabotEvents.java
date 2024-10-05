package dev.cabotmc;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class CabotEvents implements Listener {

    @EventHandler
    public void spawn(PlayerSpawnLocationEvent event) {
        if (event.getPlayer().getFirstPlayed() == event.getPlayer().getLastLogin()) {
            // first spawn
            event.setSpawnLocation(new Location(Bukkit.getWorld("world"), 0, 65, 0));
        }
    }
    @EventHandler
    public void setGamerules(WorldLoadEvent event) {
        event.getWorld().setGameRule(GameRule.SPAWN_RADIUS, 0);
        event.getWorld().setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 1);
    }
}
