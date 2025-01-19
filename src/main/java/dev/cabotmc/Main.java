package dev.cabotmc;

import dev.cabotmc.commands.GiveLightningStickCommand;
import dev.cabotmc.commands.NukeCommand;
import dev.cabotmc.customitems.LightningStick;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("Hello");

        // tell the server to use an instance of the CabotEvents class to listen for events
        getServer().getPluginManager().registerEvents(new CabotEvents(), this);

        // for custom items there needs to be an event listener to handle events related to that item
        // here, we register an instance of the LightningStick class which has attached event handler methods
        getServer().getPluginManager().registerEvents(new LightningStick(), this);

        // register commands
        var lifecycleManager = this.getLifecycleManager();
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("nuke")
                            .requires(ctx -> ctx.getSender().hasPermission("cabot.nuke"))
                            .executes(NukeCommand::run)
                            .then(
                                    Commands.argument("position", ArgumentTypes.blockPosition())
                                            .executes(NukeCommand::runPositioned)
                            )
                            .build()
            );

            commands.register(
                    Commands.literal("givelightningstick")
                            .requires(ctx -> ctx.getSender().hasPermission("cabot.givelightningstick"))
                            .executes(GiveLightningStickCommand::run)
                            .then(
                                    Commands.argument("players", ArgumentTypes.players())
                                            .executes(GiveLightningStickCommand::runParameterized)
                            )
                            .build()
            );
        });
    }

    @Override
    public void onDisable() {

    }
}