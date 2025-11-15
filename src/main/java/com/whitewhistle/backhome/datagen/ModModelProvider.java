package com.whitewhistle.backhome.datagen;

import com.whitewhistle.backhome.items.IHasModel;
import com.whitewhistle.backhome.blocks.ModBlocks;
import com.whitewhistle.backhome.items.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ModBlocks.entries.forEach(block -> blockStateModelGenerator.registerSingleton(block, getBlockModel(block)));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ModItems.entries.forEach(item -> itemModelGenerator.register(item, getItemModel(item)));
    }

    private static TexturedModel.Factory getBlockModel(Block block) {
        return TexturedModel.CUBE_ALL;
    }

    private static Model getItemModel(Item item) {
        if (item instanceof IHasModel iHasModel) {
            return iHasModel.getModel();
        }

        return Models.GENERATED;
    }
}
