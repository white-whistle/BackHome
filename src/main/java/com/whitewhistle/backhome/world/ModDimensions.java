package com.whitewhistle.backhome.world;

import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> HOUSE_DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION, ModIdentifier.of("house"));

    public static final RegistryKey<World> HOUSE_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, HOUSE_DIMENSION_KEY.getValue());

    public static void init() {
        Registry.register(Registries.CHUNK_GENERATOR, ModIdentifier.of("house"), HomeChunkGenerator.CODEC);
    }
}
