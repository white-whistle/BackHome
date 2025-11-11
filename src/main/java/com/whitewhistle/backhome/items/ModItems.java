package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItems {
    public static final List<Item> entries = new ArrayList<>();

    public static final Item TURTLE_ARMOR = register("turtle_armor", new Item.Settings().armor(ArmorMaterials.TURTLE_SCUTE, EquipmentType.CHESTPLATE));
    public static final Item TURTLE_PICKAXE = register("turtle_pickaxe", TurtlePickaxeItem::new, new Item.Settings().pickaxe(ToolMaterial.WOOD, 1.0F, -2.8F));

    public static Item register(String path, Item.Settings settings) {
        return register(path, Item::new, settings);
    }

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, ModIdentifier.of(path));

        var item = Items.register(registryKey, factory, settings);

        entries.add(item);

        return item;
    }

    public static void init() {
    }
}
