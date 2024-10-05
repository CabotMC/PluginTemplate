package dev.cabotmc;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ParkourPopulator extends BlockPopulator {

    // the offsets for a clockwise loop around the perimeter of a 16x16 minecraft chunk.
    private final Vector2[] CHUNK_PERIMETER = generatePerimeter();

    public static Vector2[] generatePerimeter() {
        Vector2[] perimeter = new Vector2[61];
        for (int i = 0; i < 16; i++) {
            perimeter[i] = new Vector2(i, 0);
            perimeter[i + 15] = new Vector2(15, i);
            perimeter[i + 30] = new Vector2(15 - i, 15);
            perimeter[i + 45] = new Vector2(0, 15 - i);
        }
        return perimeter;
    }

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        if (random.nextFloat() < 0.98) {
            return;
        }

        // generate parkour spiraling up around the outside of the chunk

        int x = chunkX * 16;
        int z = chunkZ * 16;

        var arrOffset = 0;

        var reverse = random.nextBoolean();

        for (int y = -64; y < 128; y++) {
            var offset = CHUNK_PERIMETER[arrOffset];
            var blockX = x + offset.x;
            var blockZ = z + offset.y;
            limitedRegion.setType(blockX, y, blockZ, Material.BEDROCK);

            // choose a random number between 3 and 5, add it to the current offset
            if (reverse) {
                arrOffset -= random.nextInt(3) + 2;
            } else {
                arrOffset += random.nextInt(3) + 2;
            }
            arrOffset %= CHUNK_PERIMETER.length;
            if (arrOffset < 0) {
                arrOffset += CHUNK_PERIMETER.length;
            }
        }

    }

    private static class Vector2 {
        public int x;
        public int y;

        public Vector2(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

