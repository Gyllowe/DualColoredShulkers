package net.gyllowe.dualcoloredshulkers;

import net.fabricmc.api.ModInitializer;
import net.gyllowe.dualcoloredshulkers.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DualColoredShulkers implements ModInitializer {
	public static final String MOD_ID = "dualcoloredshulkers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModRegistries.RegisterAll();
	}
}
