package dev.cabotmc;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class CabotEvents implements Listener {

    public static Team playersTeam = null;

    @EventHandler
    public void spawn(PlayerSpawnLocationEvent event) {
        if (event.getPlayer().getFirstPlayed() == event.getPlayer().getLastLogin()) {
            // first spawn
            event.setSpawnLocation(new Location(Bukkit.getWorld("world"), 0, 65, 0));
            playersTeam.addPlayer(event.getPlayer());

            event.getPlayer().getInventory().addItem(
                    new ItemStack(Material.COOKED_BEEF, 16),
                    new ItemStack(Material.WHITE_BED),
                    new ItemStack(Material.OAK_SLAB, 16)
            );
        }
    }
    @EventHandler
    public void setGamerules(WorldLoadEvent event) {
        event.getWorld().setGameRule(GameRule.SPAWN_RADIUS, 0);
        event.getWorld().setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 1);
    }

    @EventHandler
    public void ready(ServerLoadEvent e) {
        playersTeam = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("players");
        if (playersTeam == null) {
            playersTeam = Bukkit.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("players");
            playersTeam.setAllowFriendlyFire(false);
            playersTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        }
    }


}
