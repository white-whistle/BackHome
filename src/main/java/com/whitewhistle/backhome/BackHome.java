package com.whitewhistle.backhome;

import com.whitewhistle.backhome.items.ModItemGroups;
import com.whitewhistle.backhome.items.ModItems;
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
        ModItemGroups.init();
        ModDimensions.init();
	}
}