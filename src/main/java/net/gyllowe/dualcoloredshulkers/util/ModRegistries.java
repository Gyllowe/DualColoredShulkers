package net.gyllowe.dualcoloredshulkers.util;

import net.gyllowe.dualcoloredshulkers.loottables.LootTableRegistration;

public abstract class ModRegistries {
	public static void registerAll() {
		registerLootTableModifierEvents();
	}


	private static void registerLootTableModifierEvents() {
		LootTableRegistration.registerShulkerBoxLootTableEvent();
	}

}
