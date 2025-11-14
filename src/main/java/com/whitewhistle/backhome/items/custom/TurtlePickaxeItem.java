package com.whitewhistle.backhome.items.custom;

import com.whitewhistle.backhome.items.IHasModel;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.Models;
import net.minecraft.item.Item;

public class TurtlePickaxeItem extends Item implements IHasModel {
    public TurtlePickaxeItem(Settings settings) {
        super(settings);
    }

    @Override
    public Model getModel() {
        return Models.HANDHELD;
    }
}
