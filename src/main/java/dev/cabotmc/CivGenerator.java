package dev.cabotmc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CivGenerator extends ChunkGenerator {

    static List<Material> VALID_BLOCKS;
    static List<Material> ORES;

    static {
        VALID_BLOCKS = Arrays.stream(Material.values())
                .filter(Material::isBlock)
                .filter(material -> !material.hasGravity())
                .filter(material -> material != Material.WATER && material != Material.LAVA)
                .filter(material -> material.isSolid() || material.toString().toLowerCase().contains("sapling"))
                .toList();
        ORES = VALID_BLOCKS
                .stream()
                .filter(material -> material.toString().toLowerCase().contains("ore"))
                .toList();
    }

    @Override
    public @Nullable Location getFixedSpawnLocation(@NotNull World world, @NotNull Random random) {
        return new Location(world, 0, 65, 0);
    }

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return List.of(new ParkourPopulator());
    }

    @Override
    public @Nullable BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return new VoidBiomeProvider();
    }

    @Override
    public void generateCaves(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {

    }

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {

    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        for (int x = 0; x < 16; x+= 2) {
            for (int z = 0; z < 16; z+= 2) {
                for (int y = -64; y < 32; y += 32) {
                    chunkData.setBlock(x, y, z, ORES.get(random.nextInt(ORES.size())));
                }

                chunkData.setBlock(x, 64, z, Material.GRASS_BLOCK);
                if (random.nextFloat() > 0.98) {
                    chunkData.setBlock(x, 65, z, Material.OAK_SAPLING);
                }
                chunkData.setBlock(x, 128, z, VALID_BLOCKS.get(random.nextInt(VALID_BLOCKS.size())));
                chunkData.setBlock(x, 128 + 64, z, Material.DIAMOND_BLOCK);
                chunkData.setBlock(x, 256, z, Material.NETHERITE_BLOCK);
            }
        }

        if (chunkX == 0 && chunkZ == 0) {
            chunkData.setBlock(0, 64, 0, Material.BEDROCK);
        }
    }



    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {

    }

    private static class VoidBiomeProvider extends BiomeProvider {

        @Override
        public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) { return Biome.THE_VOID; }

        @Override
        public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) { return List.of(Biome.THE_VOID); }

    }
}
