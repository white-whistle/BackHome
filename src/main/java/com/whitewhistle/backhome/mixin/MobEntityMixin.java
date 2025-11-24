package com.whitewhistle.backhome.mixin;

import com.whitewhistle.backhome.items.ModItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method="initialize", at = @At("RETURN"))
    private void initializeMob(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        var mob = (MobEntity)(Object)this;

        Random random = world.getRandom();

        if (mob.getMainHandStack().isEmpty()) {
            if (random.nextFloat() < 0.05F) {
                mob.equipStack(EquipmentSlot.MAINHAND, ModItems.PIZZA_SLICE.getDefaultStack());
            }
        }
    }
}
