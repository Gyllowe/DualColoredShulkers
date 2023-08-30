package net.gyllowe.dualcoloredshulkers.loottables;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public abstract class LootTableRegistration {
	private static final Identifier[] _shulkerBoxLootTableIds = {
			Blocks.SHULKER_BOX.getLootTableId(),
			Blocks.WHITE_SHULKER_BOX.getLootTableId(),
			Blocks.ORANGE_SHULKER_BOX.getLootTableId(),
			Blocks.MAGENTA_SHULKER_BOX.getLootTableId(),
			Blocks.LIGHT_BLUE_SHULKER_BOX.getLootTableId(),
			Blocks.YELLOW_SHULKER_BOX.getLootTableId(),
			Blocks.LIME_SHULKER_BOX.getLootTableId(),
			Blocks.PINK_SHULKER_BOX.getLootTableId(),
			Blocks.GRAY_SHULKER_BOX.getLootTableId(),
			Blocks.LIGHT_GRAY_SHULKER_BOX.getLootTableId(),
			Blocks.CYAN_SHULKER_BOX.getLootTableId(),
			Blocks.PURPLE_SHULKER_BOX.getLootTableId(),
			Blocks.BLUE_SHULKER_BOX.getLootTableId(),
			Blocks.BROWN_SHULKER_BOX.getLootTableId(),
			Blocks.GREEN_SHULKER_BOX.getLootTableId(),
			Blocks.RED_SHULKER_BOX.getLootTableId(),
			Blocks.BLACK_SHULKER_BOX.getLootTableId()
	};


	public static void registerShulkerBoxLootTableEvent() {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if(!source.isBuiltin())
				return;

			if(Arrays.asList(_shulkerBoxLootTableIds).contains(id)) {
				tableBuilder.apply(
						DualShulkerColorLootFunction.builder()
				);
			}
		});
	}

}
