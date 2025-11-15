package com.whitewhistle.backhome.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.whitewhistle.backhome.blocks.ModBlocks;
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
        super(new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomeKeys.HOUSE)));
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
        BlockState soft = ModBlocks.TURTLE_WALL.getDefaultState();
        BlockState air = Blocks.AIR.getDefaultState();
        BlockState barrier = ModBlocks.DIMENSION_BARRIER.getDefaultState();

        var chunkStartX = chunk.getPos().getStartX();
        var chunkStartZ = chunk.getPos().getStartZ();

        // Loop through local chunk coordinates (0..15)
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int localX = 0; localX < 16; localX++) {
            for (int localZ = 0; localZ < 16; localZ++) {
                // Convert to world coords
                int worldX = chunkStartX + localX;
                int worldZ = chunkStartZ + localZ;

                // Fill blocks up to height
                for (int y = chunk.getBottomY(); y <= HomePlotSystem.PLOT_SIZE; y++) {
                    mutable.set(localX, y, localZ);
                    BlockState state;

                    if (isWall(worldX, y, worldZ)) {
                        state = barrier;
                    } else if (isSoftWall(worldX, y, worldZ)) {
                        state = soft;
                    } else {
                        state = air;
                    }

                    chunk.setBlockState(mutable, state);
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    private static boolean isInThreshold(int i, int threshold) {
        var modI = Math.abs(i % HomePlotSystem.PLOT_SIZE);
        if (i < 0) {
            modI--;
        }
        return modI < threshold || modI >= HomePlotSystem.PLOT_SIZE - threshold;
    }

    private static boolean isInThreshold(int x, int y, int z, int threshold) {
        return isInThreshold(x, threshold) || isInThreshold(y, threshold) || isInThreshold(z, threshold);
    }

    private static boolean isWall(int x, int y, int z) {
        return isInThreshold(x, y, z, HomePlotSystem.BARRIER_THICKNESS);
    }

    private static boolean isSoftWall(int x, int y, int z) {
        return isInThreshold(x, y, z, (HomePlotSystem.PLOT_SIZE / 2) - HomePlotSystem.AIR_POCKET_SIZE / 2);
    }

    @Override
    public int getWorldHeight() {
        return HomePlotSystem.MAX_HEIGHT;
    }

    @Override
    public int getSeaLevel() {
        return HomePlotSystem.MAX_HEIGHT / 2;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        return HomePlotSystem.PLOT_SIZE;
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
        var gridPos = HomePlotSystem.worldToGridCoordinate(pos);
        var index = HomePlotSystem.gridCoordinateToPlotIndex(gridPos.x, gridPos.y);

        text.add("++==House====");
        text.add("||  grid pos: " + gridPos.x + "_" + gridPos.y);
        text.add("||  index: " + index);
        text.add("++==House====");
    }
}
