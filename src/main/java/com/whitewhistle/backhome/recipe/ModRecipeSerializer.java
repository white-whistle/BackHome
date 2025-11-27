package com.whitewhistle.backhome.recipe;

import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface ModRecipeSerializer {
    RecipeSerializer<DeedCloningRecipe> DEED_CLONING = register(
            "crafting_special_deedcloning", new SpecialCraftingRecipe.SpecialRecipeSerializer<>(DeedCloningRecipe::new)
    );

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, ModIdentifier.of(id), serializer);
    }

    static void init() {

    }
}
