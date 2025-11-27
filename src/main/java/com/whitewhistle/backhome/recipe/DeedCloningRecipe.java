package com.whitewhistle.backhome.recipe;

import com.whitewhistle.backhome.items.ModComponents;
import com.whitewhistle.backhome.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class DeedCloningRecipe extends SpecialCraftingRecipe {
    public DeedCloningRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        if (craftingRecipeInput.getStackCount() < 2) {
            return false;
        } else {
            boolean bl = false;
            boolean bl2 = false;

            for (int i = 0; i < craftingRecipeInput.size(); i++) {
                ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
                if (!itemStack.isEmpty()) {
                    if (itemStack.isOf(ModItems.TURTLE_DEED) && itemStack.contains(ModComponents.PLOT_TYPE)) {
                        if (bl2) {
                            return false;
                        }

                        bl2 = true;
                    } else {
                        if (!itemStack.isOf(Items.PAPER)) {
                            return false;
                        }

                        bl = true;
                    }
                }
            }

            return bl2 && bl;
        }
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        int i = 0;
        ItemStack deedStack = ItemStack.EMPTY;

        for (int j = 0; j < craftingRecipeInput.size(); j++) {
            ItemStack itemStack2 = craftingRecipeInput.getStackInSlot(j);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isOf(ModItems.TURTLE_DEED) && itemStack2.contains(ModComponents.PLOT_TYPE)) {
                    // cannot have two plot items, terminate recipe
                    if (!deedStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    deedStack = itemStack2;
                } else {
                    // if stack is not paper, terminate recipe early
                    if (!itemStack2.isOf(Items.PAPER)) {
                        return ItemStack.EMPTY;
                    }

                    i++;
                }
            }
        }

        return !deedStack.isEmpty() && i >= 1 ? deedStack.copyWithCount(i + 1) : ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<DeedCloningRecipe> getSerializer() {
        return ModRecipeSerializer.DEED_CLONING;
    }
}