package dev.cabotmc;

import dev.cabotmc.commands.NukeCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("Hello");

        // tell the server to use BenryEvents to check for events
        getServer().getPluginManager().registerEvents(new CabotEvents(), this);

        // register commands

        var lifecycleManager = this.getLifecycleManager();
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("nuke")
                            .requires(ctx -> ctx.getSender().hasPermission("cabot.nuke"))
                            .then(
                                    Commands.argument("position", ArgumentTypes.blockPosition())
                                            .executes(NukeCommand::runPositioned)
                            )
                            .executes(NukeCommand::run)
                            .build()
            );
        });
    }

    @Override
    public void onDisable() {

    }
}