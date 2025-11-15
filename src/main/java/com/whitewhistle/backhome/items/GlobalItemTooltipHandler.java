package com.whitewhistle.backhome.items;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class GlobalItemTooltipHandler {
    public static void init() {
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, list) -> {
            if (stack.isOf(Items.FISHING_ROD) && stack.contains(ModComponents.BAIT_TYPE)) {
                list.add(Text.empty());
                list.add(Text.translatable("tooltip.back-home.fishing_rod_pizza_bait"));
            }
        });
    }
}
