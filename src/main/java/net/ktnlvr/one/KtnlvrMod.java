package net.ktnlvr.one;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KtnlvrMod implements ModInitializer {
	public static final String MOD_ID = "ktnlvr";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Items.registerMod();
	}
}