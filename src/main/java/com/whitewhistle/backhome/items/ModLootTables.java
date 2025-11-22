package com.whitewhistle.backhome.items;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;

public class ModLootTables {

    public static void init() {
        LootTableEvents.MODIFY.register((key, builder, source, lookup) -> {
            if (key.equals(LootTables.FISHING_JUNK_GAMEPLAY)) {
                builder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.PLASTIC_BAG).weight(20));
                });
            }
        });
    }
}
