package com.whitewhistle.backhome.mixin;

import com.whitewhistle.backhome.items.GlobalItemClickHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "onClicked", at = @At("HEAD"), cancellable = true)
    private void onClicked(ItemStack _stack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        var stack = (ItemStack) (Object) this;
        var optionalResult = GlobalItemClickHandler.onClicked(stack, slot, clickType, player, cursorStackReference);

        optionalResult.ifPresent(cir::setReturnValue);
    }

}
