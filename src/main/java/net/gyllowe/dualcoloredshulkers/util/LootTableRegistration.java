package net.gyllowe.dualcoloredshulkers.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.gyllowe.dualcoloredshulkers.DualShulkerColorLootFunction;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

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


	public static void RegisterShulkerBoxLootTableEvent() {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if(!source.isBuiltin()) {
				return;
			}
			if(
					_shulkerBoxLootTableIds[0].equals(id) ||
					_shulkerBoxLootTableIds[1].equals(id) ||
					_shulkerBoxLootTableIds[2].equals(id) ||
					_shulkerBoxLootTableIds[3].equals(id) ||
					_shulkerBoxLootTableIds[4].equals(id) ||
					_shulkerBoxLootTableIds[5].equals(id) ||
					_shulkerBoxLootTableIds[6].equals(id) ||
					_shulkerBoxLootTableIds[7].equals(id) ||
					_shulkerBoxLootTableIds[8].equals(id) ||
					_shulkerBoxLootTableIds[9].equals(id) ||
					_shulkerBoxLootTableIds[10].equals(id) ||
					_shulkerBoxLootTableIds[11].equals(id) ||
					_shulkerBoxLootTableIds[12].equals(id) ||
					_shulkerBoxLootTableIds[13].equals(id) ||
					_shulkerBoxLootTableIds[14].equals(id) ||
					_shulkerBoxLootTableIds[15].equals(id) ||
					_shulkerBoxLootTableIds[16].equals(id)
			) {
				tableBuilder.apply(
						DualShulkerColorLootFunction.builder()
				);
			}
		});
	}
}
