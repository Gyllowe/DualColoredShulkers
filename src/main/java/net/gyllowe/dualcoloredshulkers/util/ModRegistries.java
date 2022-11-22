package net.gyllowe.dualcoloredshulkers.util;

import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.gyllowe.dualcoloredshulkers.commands.DualShulkerBlockCommand;
import net.gyllowe.dualcoloredshulkers.commands.DualShulkerColorArgumentType;
import net.gyllowe.dualcoloredshulkers.commands.ShulkerPartArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

import static net.gyllowe.dualcoloredshulkers.DualColoredShulkers.MOD_ID;

public abstract class ModRegistries {
	public static void RegisterAll() {
		RegisterCommandsAndArgumentTypes();
		RegisterLootTableModifierEvents();
	}


	private static void RegisterCommandsAndArgumentTypes() {
		ArgumentTypeRegistry.registerArgumentType(
				new Identifier(MOD_ID, "dyecolor"),
				DualShulkerColorArgumentType.class,
				ConstantArgumentSerializer.of(DualShulkerColorArgumentType::dualShulkerColor)
		);
		ArgumentTypeRegistry.registerArgumentType(
				new Identifier(MOD_ID, "shulkerpart"),
				ShulkerPartArgumentType.class,
				ConstantArgumentSerializer.of(ShulkerPartArgumentType::shulkerPart)
		);
		CommandRegistrationCallback.EVENT.register(DualShulkerBlockCommand::register);
		//CommandRegistrationCallback.EVENT.register(DualShulkerHoldingCommand::register);
	}

	private static void RegisterLootTableModifierEvents() {
		LootTableRegistration.RegisterShulkerBoxLootTableEvent();
	}
}
