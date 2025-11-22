package com.whitewhistle.backhome.items;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class FishingBaitSystem {
    private static final HashMap<Item, BaitHandler> entries = new HashMap<>();

    private static void register(Item item, BaitHandler handler) {
        entries.put(item, handler);
    }

    public static boolean isBait(ItemStack stack) {
        return entries.containsKey(stack.getItem());
    }

    public static ObjectArrayList<ItemStack> generateLoot(ItemStack usedItem, Supplier<ObjectArrayList<ItemStack>> original) {
        var bait = usedItem.get(ModComponents.BAIT_TYPE);
        if (bait == null) return ObjectArrayList.of();

        var baitStack = bait.stack();
        var item = baitStack.getItem();

        var handler = entries.get(item);
        if (handler == null) return ObjectArrayList.of();

        return new ObjectArrayList<>(handler.generateLoot(usedItem, original));
    }

    public static void init() {
        register(ModItems.PIZZA_SLICE, (stack, original) -> {
            stack.remove(ModComponents.BAIT_TYPE);
            return ObjectArrayList.of(Items.TURTLE_SCUTE.getDefaultStack());
        });

        BaitHandler bundleBaitHandler = (stack, original) -> {
            var bait = stack.get(ModComponents.BAIT_TYPE);
            if (bait == null) return ObjectArrayList.of();

            var bundle = bait.stack();

            var content = bundle.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (content == null) return ObjectArrayList.of();

            var contentBuilder = new BundleContentsComponent.Builder(content);

            for (var i = 0 ; i < 9 ; i ++) {
                var loot = original.get();

                // will ignore stack if full
                loot.forEach(contentBuilder::add);
            }

            bundle.set(DataComponentTypes.BUNDLE_CONTENTS, contentBuilder.build());

            stack.remove(ModComponents.BAIT_TYPE);
            return ObjectArrayList.of(bundle);
        };

        List.of(
                Items.BUNDLE,
                Items.WHITE_BUNDLE,
                Items.ORANGE_BUNDLE,
                Items.MAGENTA_BUNDLE,
                Items.LIGHT_BLUE_BUNDLE,
                Items.YELLOW_BUNDLE,
                Items.LIME_BUNDLE,
                Items.PINK_BUNDLE,
                Items.GRAY_BUNDLE,
                Items.LIGHT_GRAY_BUNDLE,
                Items.CYAN_BUNDLE,
                Items.PURPLE_BUNDLE,
                Items.BLUE_BUNDLE,
                Items.BROWN_BUNDLE,
                Items.GREEN_BUNDLE,
                Items.RED_BUNDLE,
                Items.BLACK_BUNDLE
        ).forEach(item -> register(item, bundleBaitHandler));

    }



    @FunctionalInterface
    interface BaitHandler {
        Collection<ItemStack> generateLoot(ItemStack usedItem, Supplier<ObjectArrayList<ItemStack>> original);
    }
}
