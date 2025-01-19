package dev.cabotmc.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.cabotmc.customitems.LightningStick;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;

public class GiveLightningStickCommand {
    public static int run(CommandContext<CommandSourceStack> context) {
        var source = context.getSource().getSender();
        if (source instanceof Player player) {
            giveLightningStickToPlayer(player);
            return Command.SINGLE_SUCCESS;
        } else {
            source.sendMessage("You must be a player to run this command in this form");
            return -1;
        }
    }

    public static int runParameterized(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var playersResolver = context.getArgument("players", PlayerSelectorArgumentResolver.class);

        var players = playersResolver.resolve(context.getSource());

        for (var target : players) {
            giveLightningStickToPlayer(target);
        }

        return players.size();
    }

    private static void giveLightningStickToPlayer(Player target) {
        target
            .getInventory()
            .addItem(LightningStick.createItem());
    }
}
