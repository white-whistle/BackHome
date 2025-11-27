package com.whitewhistle.backhome.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PlotComponent(int plot) {
    public static final Codec<PlotComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("stack").forGetter(PlotComponent::plot)
    ).apply(builder, PlotComponent::new));
}
