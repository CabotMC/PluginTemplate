package dev.cabotmc.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.math.BlockPosition;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;

public class NukeCommand{

    public static int runPositioned(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (context.getSource().getExecutor() == null) {
            context.getSource().getSender()
                    .sendMessage(Component.text("This command must be executed in a world"));

            return -1;
        }
        var world = context.getSource().getExecutor().getWorld();

        var resolver = context.getArgument("position", BlockPositionResolver.class);
        var pos = resolver.resolve(context.getSource());

        var loc = new Location(world, pos.x(), pos.y(), pos.z());
        nuke(loc);
        return 1;
    }

    public static int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var location = context.getSource().getLocation();
        nuke(location);
        return 1;
    }

    private static final int GRID_SIZE = 5;
    private static final int GRID_SPACING = 5;

    private static void nuke(Location position) {
        var startX = position.x() - ((double) (GRID_SIZE * GRID_SPACING) / 2);
        var startZ = position.z() - ((double) (GRID_SIZE * GRID_SPACING) / 2);
        var y = position.y() + 100;
        var start = new Location(position.getWorld(), startX, y, startZ);

        for (int x = 0; x < GRID_SIZE; x++) {
            for (int z = 0; z < GRID_SIZE; z++) {
                position.getWorld()
                        .spawn(
                                start.clone()
                                        .add(x * GRID_SIZE, 0, z * GRID_SIZE),
                                TNTPrimed.class,
                                tnt -> {
                                    tnt.setFuseTicks(200);
                                }
                        );
            }
        }
    }
}
