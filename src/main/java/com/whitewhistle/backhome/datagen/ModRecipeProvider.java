package com.whitewhistle.backhome.datagen;

import com.whitewhistle.backhome.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.whitewhistle.backhome.BackHome.MOD_ID;

public class ModRecipeProvider extends FabricRecipeProvider {


    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void addSmeltingRecipe(RecipeGenerator generator, Item from, Item to, float xp) {
        generator.offerSmelting(List.of(from), RecipeCategory.MISC, to, xp, 200, MOD_ID);
        generator.offerBlasting(List.of(from), RecipeCategory.MISC, to, xp, 100, MOD_ID);
    }


    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                // === SMELTING ===
                addSmeltingRecipe(this, ModItems.PLASTIC_BAG, ModItems.PLASTIC_INGOT, 0.2f);

                // === SHAPELESS ===
                createShapeless(RecipeCategory.TOOLS, ModItems.SHELLITE_INGOT)
                        .input(Items.TURTLE_SCUTE, 4)
                        .input(Items.IRON_INGOT, 4)
                        .criterion(hasItem(Items.TURTLE_SCUTE), conditionsFromItem(Items.TURTLE_SCUTE))
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                // === SHAPED ===
                createShaped(RecipeCategory.TOOLS, ModItems.PLASTIC_PICKAXE)
                        .pattern("PPP")
                        .pattern(" | ")
                        .pattern(" | ")
                        .input('P', ModItems.PLASTIC_INGOT)
                        .input('|', Items.STICK)
                        .criterion(hasItem(ModItems.PLASTIC_INGOT), conditionsFromItem(ModItems.PLASTIC_INGOT))
                        .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                        .offerTo(exporter);

                createShaped(RecipeCategory.TOOLS, ModItems.GOLDEN_PIZZA_SLICE)
                        .pattern("GGG")
                        .pattern("GPG")
                        .pattern("GGG")
                        .input('P', ModItems.PIZZA_SLICE)
                        .input('G', Items.GOLD_INGOT)
                        .criterion(hasItem(ModItems.PIZZA_SLICE), conditionsFromItem(ModItems.PIZZA_SLICE))
                        .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);

                // === SMITHING ===
                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItem(ModItems.TURTLE_DEED),
                                Ingredient.ofItem(Items.DIAMOND_CHESTPLATE),
                                Ingredient.ofItem(ModItems.SHELLITE_INGOT),
                                RecipeCategory.COMBAT,
                                ModItems.TURTLE_ARMOR
                        )
                        .criterion("has_shellite_ingot", this.conditionsFromItem(ModItems.SHELLITE_INGOT))
                        .offerTo(this.exporter, getItemPath(ModItems.TURTLE_ARMOR) + "_smithing");

            }
        };
    }

    @Override
    public String getName() {
        return MOD_ID + ":recipe_generator";
    }
}