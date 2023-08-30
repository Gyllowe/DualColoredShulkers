package net.gyllowe.dualcoloredshulkers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class DualColoredShulkersClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		if(FabricLoader.getInstance().isModLoaded("entity_texture_features"))
			DualColoredShulkers.LOGGER.warn("Mod incompatibility: Entity Texture Features. Disable \"enable custom block entities\" (and \"enable emissive block entities\" if emissive shulkers are used) in ETFs config to render shulker boxes' base correctly.");

		if(FabricLoader.getInstance().isModLoaded("enhancedblockentities"))
			DualColoredShulkers.LOGGER.warn("Mod incompatibility: Enhanced Block Entities. Disable \"render enhanced shulker boxes\" in EBEs config to render shulker boxes' base correctly.");
	}

}
