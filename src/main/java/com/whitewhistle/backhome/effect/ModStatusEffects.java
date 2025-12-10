package com.whitewhistle.backhome.effect;

import com.whitewhistle.backhome.utils.ModIdentifier;
import com.whitewhistle.backhome.world.HomePlotSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> TRANSPORT = registerStatusEffect("transport", new TransportEffect(StatusEffectCategory.BENEFICIAL, 0x000000));

    // ============= impl ==================

    public static class ModStatusEffect extends StatusEffect {
        protected ModStatusEffect(StatusEffectCategory category, int color) {
            super(category, color);
        }
    }

    public static class TransportEffect extends ModStatusEffect implements IRemoveEffect {

        protected TransportEffect(StatusEffectCategory category, int color) {
            super(category, color);
        }

        @Override
        public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
            System.out.println("REMOVED!");
            if (entity instanceof ServerPlayerEntity player) {
                System.out.println("IS PLAYER!@!!");
                HomePlotSystem.handleArmorTrigger(player);
            }
        }

    }

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, ModIdentifier.of(name), effect);
    }

    public static void registerEffects() {

    }
}
