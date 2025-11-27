package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.items.components.ItemStackComponent;
import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import com.whitewhistle.backhome.world.HomePlotSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;

import java.util.Optional;

public class GlobalItemClickHandler {


    public static Optional<Boolean> onClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        var cursorStack = cursorStackReference.get();

        if (clickType == ClickType.RIGHT) {
            if (stack.isOf(Items.FISHING_ROD) && FishingBaitSystem.isBait(cursorStack)) {
                var baitStack = cursorStack.split(1);

                // add bait stack
                stack.set(ModComponents.BAIT_TYPE, new ItemStackComponent(baitStack));
                player.playSound(SoundEvents.ITEM_BUNDLE_INSERT);

                return Optional.of(true);
            }

            if (stack.isOf(Items.FISHING_ROD) && cursorStack.isEmpty()) {
                var baitData = stack.get(ModComponents.BAIT_TYPE);
                if (baitData == null) return Optional.empty();

                // remove bait
                cursorStackReference.set(baitData.stack());
                stack.remove(ModComponents.BAIT_TYPE);
                player.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE);

                return Optional.of(true);
            }

            if (stack.isOf(ModItems.TURTLE_ARMOR)) {
                var shellData = stack.get(ModComponents.TURTLE_SHELL_TYPE);

                if (cursorStack.isEmpty()) {
                    if (shellData == null) return Optional.empty();

                    var deedStack = TurtleShellArmorItem.getDeed(stack);

                    // eject deedStack
                    cursorStackReference.set(deedStack);
                    TurtleShellArmorItem.setDeed(stack, null);

                    player.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE);

                    if (HomePlotSystem.isPlayerInHouseDim(player)) {
                        HomePlotSystem.movePlayerToPlot(player, stack);
                    }

                    return Optional.of(true);

                } else if (cursorStack.isOf(ModItems.TURTLE_DEED)) {
                    if (shellData == null) {
                        TurtleShellArmorItem.setDeed(stack, cursorStack.split(1));
                    } else {
                        if (cursorStack.getCount() > 1) return Optional.empty();

                        var oldStack = TurtleShellArmorItem.getDeed(stack);
                        TurtleShellArmorItem.setDeed(stack, cursorStack);

                        cursorStackReference.set(oldStack);
                        player.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE);
                    }

                    player.playSound(SoundEvents.ITEM_BUNDLE_INSERT);

                    if (HomePlotSystem.isPlayerInHouseDim(player)) {
                        HomePlotSystem.movePlayerToPlot(player, stack);
                    }

                    return Optional.of(true);
                }
            }
        }

        return Optional.empty();
    }
}
