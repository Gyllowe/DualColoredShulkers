package net.gyllowe.dualcoloredshulkers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.gyllowe.dualcoloredshulkers.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DualColoredShulkers implements ModInitializer {
	public static final String MOD_ID = "dualcoloredshulkers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModRegistries.registerAll();

		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			if(FabricLoader.getInstance().isModLoaded("entity_texture_features"))
				LOGGER.warn("Mod incompatibility: Entity Texture Features. Disable \"enable custom block entities\" (and optionally \"enable emissive block entities\") in ETFs config to render shulker boxes' base correctly.");

			if(FabricLoader.getInstance().isModLoaded("enhancedblockentities"))
				LOGGER.warn("Mod incompatibility: Enhanced Block Entities. Disable \"render enhanced shulker boxes\" in EBEs config to render shulker boxes' base correctly.");
		}
	}

}
