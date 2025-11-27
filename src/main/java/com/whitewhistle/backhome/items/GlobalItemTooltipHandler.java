package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.client.ModKeyBindings;
import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import com.whitewhistle.backhome.world.HomePlotSystem;
import com.whitewhistle.backhome.world.ModDimensions;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GlobalItemTooltipHandler {
    public static void init() {
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, list) -> {
            var client = MinecraftClient.getInstance();
            var player = client.player;

            if (stack.isOf(Items.FISHING_ROD) && stack.contains(ModComponents.BAIT_TYPE)) {
                var bait = stack.get(ModComponents.BAIT_TYPE);
                if (bait == null) return;

                var baitStack = bait.stack();

                list.add(Text.empty());
                list.add(Text.translatable("tooltip.back-home.fishing_bait1"));
                list.add(Text.translatable("tooltip.back-home.fishing_bait2", baitStack.getName()).styled(s -> s.withColor(Formatting.GRAY)));
            }

            if (stack.contains(ModComponents.PLOT_TYPE)) {
                var plotData = stack.get(ModComponents.PLOT_TYPE);
                if (plotData == null) return;

                list.add(Text.translatable("tooltip.back-home.plot_index", plotData.plot()).styled(s -> s.withColor(Formatting.GRAY)));
            }

            if (stack.isOf(ModItems.TURTLE_ARMOR)) {
                var shellData = stack.get(ModComponents.TURTLE_SHELL_TYPE);
                var deedStack = TurtleShellArmorItem.getDeed(stack);
                var isExitMsg = false;
                var noPlot = shellData == null || deedStack.isEmpty();

                if (player != null) {
                    var world = player.getEntityWorld();
                    isExitMsg = world.getRegistryKey().equals(ModDimensions.HOUSE_WORLD_KEY);
                }

                if (noPlot) {
                    list.add(Text.empty());
                    list.add(Text.translatable("tooltip.back-home.turtle_armor.no_plot1").styled(s -> s.withColor(Formatting.GRAY)));
                    list.add(Text.translatable("tooltip.back-home.turtle_armor.no_plot2").styled(s -> s.withColor(Formatting.GRAY)));
                }

                list.add(Text.empty());
                if (!deedStack.isEmpty()) {
                    var name = deedStack.getCustomName();
                    if (name != null) {
                        list.add(Text.translatable("tooltip.back-home.named_plot", name.copy().styled(s -> s.withColor(Formatting.WHITE))).styled(s -> s.withColor(Formatting.GRAY)));
                    } else {
                        list.add(Text.translatable("tooltip.back-home.plot_index", HomePlotSystem.getArmorPlotIndex(stack)).styled(s -> s.withColor(Formatting.GRAY)));
                    }
                } else {
                    list.add(Text.translatable("tooltip.back-home.plot_index", HomePlotSystem.getArmorPlotIndex(stack)).styled(s -> s.withColor(Formatting.GRAY)));
                }
                list.add(Text.translatable(isExitMsg ? "tooltip.back-home.turtle_armor.exit_plot" : "tooltip.back-home.turtle_armor.enter_plot", ModKeyBindings.ACTIVATE_TURTLE_ARMOR.getBoundKeyLocalizedText().copy().styled(s -> s.withColor(Formatting.BLUE))).styled(s -> s.withColor(Formatting.GRAY)));

            }
        });
    }
}
