package com.whitewhistle.backhome.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

public record BaitComponent(ItemStack stack) {
    public static final Codec<BaitComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ItemStack.CODEC.fieldOf("stack").forGetter(BaitComponent::stack)
    ).apply(builder, BaitComponent::new));
}
