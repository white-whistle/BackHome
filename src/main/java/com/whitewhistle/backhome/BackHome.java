package com.whitewhistle.backhome;

import com.whitewhistle.backhome.command.ModCommands;
import com.whitewhistle.backhome.blocks.ModBlocks;
import com.whitewhistle.backhome.items.*;
import com.whitewhistle.backhome.network.ServerPacketReceiver;
import com.whitewhistle.backhome.network.payload.ModPayloads;
import com.whitewhistle.backhome.recipe.ModRecipeSerializer;
import com.whitewhistle.backhome.world.ModDimensions;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackHome implements ModInitializer {
	public static final String MOD_ID = "back-home";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Turtle world!");

        ModItems.init();
        ModBlocks.init();
        ModItemGroups.init();
        ModDimensions.init();
        ModComponents.init();
        ModLootTables.init();

        ModCommands.init();
        ModPayloads.init();
        ServerPacketReceiver.init();

        ModRecipeSerializer.init();

        FishingBaitSystem.init();
	}
}