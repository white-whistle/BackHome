package com.whitewhistle.backhome.mixin;

import com.whitewhistle.backhome.effect.IRemoveEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method="onStatusEffectsRemoved", at = @At("HEAD"))
    private void onStatusEffectRemoved(Collection<StatusEffectInstance> effects, CallbackInfo ci) {
        var livingEntity = (LivingEntity)(Object) this;

        System.out.println("STATUS REMOVAL!!!???");
        if (!livingEntity.getEntityWorld().isClient()) {
            effects.forEach(instance -> {
                System.out.println("in instace");
                System.out.println(instance);
                if (instance.getEffectType().value() instanceof IRemoveEffect iRemoveEffect) {
                    System.out.println("wooo is iremoveeffect");
                    iRemoveEffect.onRemoved(livingEntity, livingEntity.getAttributes(), instance.getAmplifier());
                }
            });
        }
    }
}
