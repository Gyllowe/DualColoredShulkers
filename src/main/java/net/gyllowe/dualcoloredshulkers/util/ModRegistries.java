package net.gyllowe.dualcoloredshulkers.util;

public abstract class ModRegistries {
	public static void RegisterAll() {
		RegisterLootTableModifierEvents();
	}


	private static void RegisterLootTableModifierEvents() {
		LootTableRegistration.RegisterShulkerBoxLootTableEvent();
	}
}
