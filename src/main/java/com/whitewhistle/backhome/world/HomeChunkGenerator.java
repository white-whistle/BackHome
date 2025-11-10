package com.whitewhistle.backhome.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeChunkGenerator extends ChunkGenerator {
    public static final MapCodec<HomeChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME))
                    .apply(instance, instance.stable(HomeChunkGenerator::new)));

    public HomeChunkGenerator(RegistryEntryLookup<Biome> biomeRegistry) {
        super(new FixedBiomeSource(biomeRegistry.getOrThrow(BiomeKeys.PLAINS)));
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk) {

    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        // Parameters for sine wave
        double amplitude = 20;  // how tall
        double frequency = 32;  // how wide the waves are
        int baseHeight = 64;    // average ground height

        BlockState solid = Blocks.STONE.getDefaultState();
        BlockState top = Blocks.GRASS_BLOCK.getDefaultState();
        BlockState barrier = Blocks.BEDROCK.getDefaultState();

        // Loop through local chunk coordinates (0..15)
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int localX = 0; localX < 16; localX++) {
            for (int localZ = 0; localZ < 16; localZ++) {
                // Convert to world coords
                int worldX = chunk.getPos().getStartX() + localX;
                int worldZ = chunk.getPos().getStartZ() + localZ;

                // Sine wave height
                // double height = baseHeight + amplitude * Math.sin(worldX / frequency) * Math.cos(worldZ / frequency);
                // int intHeight = (int) Math.floor(height);

                var isWall = (worldX % frequency == 0) || (worldZ % frequency == 0);
                int intHeight = isWall ? 60 : 10;

                // Fill blocks up to height
                for (int y = chunk.getBottomY(); y <= intHeight; y++) {
                    mutable.set(localX, y, localZ);
                    BlockState state =
                              y == chunk.getBottomY() ? barrier
                            : isWall ? barrier
                            : y == intHeight ? top
                            : solid;

                    if (y == chunk.getBottomY()) {
                        state = barrier;
                    } else if (isWall) {
                        state = barrier;
                    } else if (y == intHeight) {
                        state = top;
                    } else {
                        state = solid;
                    }

                    chunk.setBlockState(mutable, state);
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getWorldHeight() {
        return 384;
    }

    @Override
    public int getSeaLevel() {
        return 63;
    }

    @Override
    public int getMinimumY() {
        return -64;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        double amplitude = 20;
        double frequency = 32;
        int baseHeight = 64;
        return (int) (baseHeight + amplitude * Math.sin(x / frequency) * Math.cos(z / frequency));
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        int height = getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG, world, noiseConfig);
        BlockState[] states = new BlockState[Math.max(1, height - getMinimumY() + 1)];
        for (int i = 0; i < states.length; i++) {
            states[i] = i == states.length - 1 ? Blocks.GRASS_BLOCK.getDefaultState() : Blocks.STONE.getDefaultState();
        }
        return new VerticalBlockSample(getMinimumY(), states);
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {}

    @Override
    public void populateEntities(ChunkRegion region) {}

    @Override
    public void appendDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
        text.add("HomeChunkGenerator: sine wave terrain");
    }
}
