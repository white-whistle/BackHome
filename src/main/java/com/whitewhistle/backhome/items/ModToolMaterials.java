package com.whitewhistle.backhome.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class ModToolMaterials {
    public static final ToolMaterial TURTLE = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            10,
            10.0F,
            1.5F,
            22,
            ItemTags.REPAIRS_TURTLE_HELMET
    );
}
