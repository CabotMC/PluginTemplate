package dev.cabotmc;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("Hello");

        // tell the server to use BenryEvents to check for events
        getServer().getPluginManager().registerEvents(new CabotEvents(), this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return new CivGenerator();
    }
}