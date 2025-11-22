package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.items.custom.TurtlePickaxeItem;
import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItems {
    public static final List<Item> entries = new ArrayList<>();

    public static final Item TURTLE_ARMOR = register("turtle_armor", TurtleShellArmorItem::new, new Item.Settings().armor(ModArmorMaterials.SHELLITE, EquipmentType.CHESTPLATE));
    public static final Item PLASTIC_PICKAXE = register("plastic_pickaxe", TurtlePickaxeItem::new, new Item.Settings().pickaxe(ModToolMaterials.PLASTIC, 1.0F, -2.8F));
    public static final Item PLASTIC_BAG = register("plastic_bag", Item::new, new Item.Settings());
    public static final Item PLASTIC_INGOT = register("plastic_ingot", Item::new, new Item.Settings());
    public static final Item SHELLITE_INGOT = register("shellite_ingot", Item::new, new Item.Settings());

    public static final Item PIZZA_SLICE = register("pizza_slice", new Item.Settings().food(new FoodComponent.Builder().build()));

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

    public static class Tags {
        public static TagKey<Item> REPAIRS_PLASTIC_ARMOR = of("repairs_plastic_armor");

        private static TagKey<Item> of(String id) {
            return TagKey.of(RegistryKeys.ITEM, ModIdentifier.of(id));
        }
    }
}
