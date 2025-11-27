package com.whitewhistle.backhome.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

public record ItemStackComponent(ItemStack stack) {
    public static final Codec<ItemStackComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ItemStack.CODEC.fieldOf("stack").forGetter(ItemStackComponent::stack)
    ).apply(builder, ItemStackComponent::new));

    public static ItemStackComponent of(ItemStack stack) {
        return new ItemStackComponent(stack);
    }
}
