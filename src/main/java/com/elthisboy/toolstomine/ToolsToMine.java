package com.elthisboy.toolstomine;

import com.elthisboy.toolstomine.entity.ModEntities;
import com.elthisboy.toolstomine.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsToMine implements ModInitializer {
	public static final String MOD_ID = "toolstomine";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEntities.registerAll();
		ModItems.registerAll();
		LOGGER.info("ToolsToMine loaded!");
	}
}
