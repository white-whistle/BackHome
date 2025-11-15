package com.whitewhistle.backhome.blocks;

import com.whitewhistle.backhome.blocks.custom.TurtleWallBlock;
import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModBlocks {
    public static final List<Block> entries = new ArrayList<>();

    public static final Block TURTLE_WALL = register("turtle_wall", TurtleWallBlock::new, Block.Settings.create().strength(3f, 3600000.0F));
    public static final Block DIMENSION_BARRIER = register("dimension_barrier", Block.Settings.create().strength(-1.0f, 3600000.0F));

    private static Block register(String path, AbstractBlock.Settings settings) { return register(path, Block::new, settings); }

    private static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, ModIdentifier.of(path));

        final Block block = Blocks.register(registryKey, factory, settings);
        Items.register(block);
        entries.add(block);
        return block;
    }

    public static void init() {
    }
}
