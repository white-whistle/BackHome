package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.items.components.BaitComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.Optional;

public class GlobalItemClickHandler {


    public static Optional<Boolean> onClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        var cursorStack = cursorStackReference.get();

        if (clickType == ClickType.RIGHT) {
            if (stack.isOf(Items.FISHING_ROD) && FishingBaitSystem.isBait(cursorStack)) {
                var baitStack = cursorStack.split(1);

                // add bait stack
                stack.set(ModComponents.BAIT_TYPE, new BaitComponent(baitStack));

                return Optional.of(true);
            }

            if (stack.isOf(Items.FISHING_ROD) && cursorStack.isEmpty()) {
                var baitData = stack.get(ModComponents.BAIT_TYPE);
                if (baitData == null) return Optional.empty();

                // remove bait
                cursorStackReference.set(baitData.stack());
                stack.remove(ModComponents.BAIT_TYPE);

                return Optional.of(true);
            }
        }

        return Optional.empty();
    }
}
