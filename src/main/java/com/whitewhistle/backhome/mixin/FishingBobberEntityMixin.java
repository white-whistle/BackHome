package com.whitewhistle.backhome.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.whitewhistle.backhome.items.FishingBaitSystem;
import com.whitewhistle.backhome.items.ModComponents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {

    @WrapOperation(method="use", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootWorldContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
    private ObjectArrayList<ItemStack> getFishingLoot(LootTable instance, LootWorldContext parameters, Operation<ObjectArrayList<ItemStack>> original, @Local(argsOnly = true) ItemStack usedItem) {
        if (usedItem.contains(ModComponents.BAIT_TYPE)) {
            Supplier<ObjectArrayList<ItemStack>> boundOriginal = () -> original.call(instance, parameters);

            var entity = (FishingBobberEntity)(Object)(this);
            var world = entity.getEntityWorld();
            if (world instanceof ServerWorld serverWorld) {
                return FishingBaitSystem.generateLoot(serverWorld, usedItem, boundOriginal);
            }

        }

        return original.call(instance, parameters);
    }

}
