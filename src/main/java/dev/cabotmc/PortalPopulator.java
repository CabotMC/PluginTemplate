package dev.cabotmc;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PortalPopulator extends BlockPopulator {
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        if (chunkX % 64 == 0 && chunkZ % 64 == 0 && (chunkX != 0 ^ chunkZ != 0)) {
            // generate an end portal at y=128

            // first clear out the area around the portal, 6x6

            for (int x = 0; x <= 6; x++) {
                for (int z = 0; z <= 6; z++) {
                    limitedRegion.setType(chunkX * 16 + x, 128, chunkZ * 16 + z, Material.AIR);
                }
            }

            var facingEast = (EndPortalFrame) Material.END_PORTAL_FRAME.createBlockData();
            facingEast.setFacing(BlockFace.EAST);

            var facingWest = (EndPortalFrame) Material.END_PORTAL_FRAME.createBlockData();
            facingWest.setFacing(BlockFace.WEST);

            var facingNorth = (EndPortalFrame) Material.END_PORTAL_FRAME.createBlockData();
            facingNorth.setFacing(BlockFace.NORTH);

            var facingSouth = (EndPortalFrame) Material.END_PORTAL_FRAME.createBlockData();
            facingSouth.setFacing(BlockFace.SOUTH);

            var x = chunkX * 16;
            var z = chunkZ * 16;
            x++;
            z++;

            limitedRegion.setBlockData(x + 1, 128, z, facingSouth);
            limitedRegion.setBlockData(x + 2, 128, z, facingSouth);
            limitedRegion.setBlockData(x + 3, 128, z, facingSouth);

            limitedRegion.setBlockData(x, 128, z + 1, facingEast);
            limitedRegion.setBlockData(x, 128, z + 2, facingEast);
            limitedRegion.setBlockData(x, 128, z + 3, facingEast);

            limitedRegion.setBlockData(x + 1, 128, z + 4, facingNorth);
            limitedRegion.setBlockData(x + 2, 128, z + 4, facingNorth);
            limitedRegion.setBlockData(x + 3, 128, z + 4, facingNorth);

            limitedRegion.setBlockData(x + 4, 128, z + 1, facingWest);
            limitedRegion.setBlockData(x + 4, 128, z + 2, facingWest);
            limitedRegion.setBlockData(x + 4, 128, z + 3, facingWest);



        }
    }
}
