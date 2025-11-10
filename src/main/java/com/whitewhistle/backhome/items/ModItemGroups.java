package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.utils.ModIdentifier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import static com.whitewhistle.backhome.BackHome.MOD_ID;

public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            ModIdentifier.of("mod_item_group"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup." + MOD_ID))
                    .icon(ModItems.TURTLE_ARMOR::getDefaultStack)
                    .entries((displayContext, entries) -> {
                        for (var item : ModItems.entries) {
                            entries.add(item);
                        }
                    })
                    .build()
    );

    public static void init() {
    }
}
