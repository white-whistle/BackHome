package com.whitewhistle.backhome.mixin;

import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EquippableComponent.class)
public class EquippableComponentMixin {

    @Shadow
    @Final
    private EquipmentSlot slot;

    @Inject(method = "equip", at = @At("HEAD"), cancellable = true)
    private void onEquip(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ActionResult> cir) {
        if (!TurtleShellArmorItem.canTakeItems(player.getEquippedStack(slot), slot, player)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
