package com.whitewhistle.backhome.datagen;

import com.whitewhistle.backhome.items.IHasModel;
import com.whitewhistle.backhome.items.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.Models;
import net.minecraft.item.Item;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ModItems.entries.forEach(item -> itemModelGenerator.register(item, getItemModel(item)));
    }

    private static Model getItemModel(Item item) {
        if (item instanceof IHasModel iHasModel) {
            return iHasModel.getModel();
        }

        return Models.GENERATED;
    }
}
