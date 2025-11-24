package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.items.custom.TurtlePickaxeItem;
import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItems {
    public static final List<Item> entries = new ArrayList<>();

    public static final Item TURTLE_ARMOR = register("turtle_armor", TurtleShellArmorItem::new, new Item.Settings().armor(ModArmorMaterials.SHELLITE, EquipmentType.CHESTPLATE).rarity(Rarity.EPIC));
    public static final Item PLASTIC_PICKAXE = register("plastic_pickaxe", TurtlePickaxeItem::new, new Item.Settings().pickaxe(ModToolMaterials.PLASTIC, 1.0F, -2.8F).rarity(Rarity.UNCOMMON));
    public static final Item PLASTIC_BAG = register("plastic_bag", Item::new, new Item.Settings());
    public static final Item PLASTIC_INGOT = register("plastic_ingot", Item::new, new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item SHELLITE_INGOT = register("shellite_ingot", Item::new, new Item.Settings().rarity(Rarity.RARE));
    public static final Item TURTLE_DEED = register("turtle_deed", Item::new, new Item.Settings().rarity(Rarity.RARE));

    // === FOOD ===
    public static final Item PIZZA_SLICE = register("pizza_slice", new Item.Settings().food(new FoodComponent.Builder().build()).rarity(Rarity.UNCOMMON));
    public static final Item GOLDEN_PIZZA_SLICE = register("golden_pizza_slice", new Item.Settings()
            .food(
                    new FoodComponent.Builder().build(),
                    ConsumableComponents.food()
                            .consumeEffect(new ApplyEffectsConsumeEffect(
                                            List.of(
                                                    new StatusEffectInstance(StatusEffects.LUCK, 400, 1),
                                                    new StatusEffectInstance(StatusEffects.REGENERATION, 1200, 0),
                                                    new StatusEffectInstance(StatusEffects.WATER_BREATHING, 6000, 0),
                                                    new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3)
                                            )
                                    )
                            )
                            .build()
            ).rarity(Rarity.RARE)
    );

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
