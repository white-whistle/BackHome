package com.whitewhistle.backhome.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;

public interface IRemoveEffect {
    void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier);
}
