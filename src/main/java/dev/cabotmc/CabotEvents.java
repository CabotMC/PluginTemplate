package dev.cabotmc;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CabotEvents implements Listener {

    // make people blow up when they jump
    @EventHandler
    public void jump(PlayerJumpEvent event) {
        World world = event.getPlayer().getWorld();
        float power = 10;
        world.createExplosion(event.getPlayer().getLocation(), power);
    }

    // make sure everyone's in creative mode when they join
    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.CREATIVE);
    }
}
