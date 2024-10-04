package dev.cabotmc;

import org.bukkit.plugin.java.JavaPlugin;

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
}