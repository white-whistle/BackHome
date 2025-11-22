package com.whitewhistle.backhome.blocks.custom;

import com.whitewhistle.backhome.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.List;

public class TurtleWallBlock extends Block {
    public TurtleWallBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        var stack = player.getMainHandStack();

        if (stack.isOf(ModItems.PLASTIC_PICKAXE)) return super.calcBlockBreakingDelta(state, player, world, pos);

        return 0;
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        return List.of(Items.TURTLE_SCUTE.getDefaultStack());
    }
}
