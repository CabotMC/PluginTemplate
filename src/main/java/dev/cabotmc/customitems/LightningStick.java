package dev.cabotmc.customitems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class LightningStick implements Listener {
    /// This marker key is applied to the item metadata to let us keep track of custom items without relying
    /// on properties that a player can modify like item name
    private static final NamespacedKey MARKER = new NamespacedKey("cabot", "lightning_stick");

    /// This method creates a copy of the item with the key applied
    public static ItemStack createItem() {
        ItemStack result = new ItemStack(Material.STICK);
        result.editMeta(meta -> {
           meta.setMaxStackSize(1);

           meta.customName(
                   Component.text("Smite-inator 9000")
                           .decoration(TextDecoration.ITALIC, false)
                           .color(TextColor.color(0xe8fa20))
           );

           meta.getPersistentDataContainer()
                   .set(MARKER, PersistentDataType.BOOLEAN, true);
        });


        return result;
    }

    /// This method is called any time a player sends an interaction packet to the server.
    /// This could be for anything from placing a block to shearing a sheep so we need to check
    /// some stuff first
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().isLeftClick()) {
            return; // ignore left clicks
        }

        if (event.getItem() == null || event.getItem().isEmpty()) {
            return; // ignore interactions without any item held
        }

        var item = event.getItem();
        if (!item.getPersistentDataContainer().has(MARKER)) {
            return; // ignore events caused by items that aren't the lightning stick
        }

        // at this point we know that this function was called because a player right clicked
        // with the lightning stick in their main hand

        Player player = event.getPlayer();

        // use ray trace method to find the block the player is looking at
        var result = player.rayTraceBlocks(100);

        if (result == null || result.getHitBlock() == null) {
            return; // player was looking at the sky, or they're looking at a block out of range
        }

        var hitBlockPosition = result.getHitBlock().getLocation();

        // add one block to the location so the lightning strikes on the surface, not inside the block
        hitBlockPosition.add(0, 1, 0);

        player.getWorld()
                .strikeLightning(hitBlockPosition);
    }
}
