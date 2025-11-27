package com.whitewhistle.backhome.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.whitewhistle.backhome.world.ModDimensions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public record TurtleShellComponent(Vec3d worldPos, RegistryKey<World> worldKey) {

    public static final Codec<TurtleShellComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Vec3d.CODEC.fieldOf("world_pos").forGetter(TurtleShellComponent::worldPos),
            ModDimensions.WORLD_KEY_CODEC.fieldOf("world_key").forGetter(TurtleShellComponent::worldKey)
    ).apply(builder, TurtleShellComponent::new));

}
