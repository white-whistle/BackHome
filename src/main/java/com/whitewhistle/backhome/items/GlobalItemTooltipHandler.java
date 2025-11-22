package com.whitewhistle.backhome.items;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GlobalItemTooltipHandler {
    public static void init() {
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, list) -> {
            if (stack.isOf(Items.FISHING_ROD) && stack.contains(ModComponents.BAIT_TYPE)) {
                var bait = stack.get(ModComponents.BAIT_TYPE);
                if (bait == null) return;

                var baitStack = bait.stack();

                list.add(Text.empty());
                list.add(Text.translatable("tooltip.back-home.fishing_bait1"));
                list.add(Text.translatable("tooltip.back-home.fishing_bait2", baitStack.getName()).styled(s -> s.withColor(Formatting.GRAY)));
            }
        });
    }
}
