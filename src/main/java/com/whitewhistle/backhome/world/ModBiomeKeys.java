package com.whitewhistle.backhome.world;

import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public class ModBiomeKeys {
    public static final RegistryKey<Biome> HOUSE = keyOf("house");

    private static RegistryKey<Biome> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, ModIdentifier.of(id));
    }
}
