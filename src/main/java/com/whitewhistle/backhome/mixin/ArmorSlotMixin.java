package com.whitewhistle.backhome.mixin;

import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.screen.slot.ArmorSlot;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin {

    @Shadow
    @Final
    private EquipmentSlot equipmentSlot;

    @Inject(method="canTakeItems", at = @At("HEAD"), cancellable = true)
    private void onTryTakeItems(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = ((Slot)(Object)(this)).getStack();
        if (!TurtleShellArmorItem.canTakeItems(itemStack, equipmentSlot, playerEntity)) {
            cir.setReturnValue(false);
        }
    }

}
