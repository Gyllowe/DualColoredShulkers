package net.gyllowe.dualcoloredshulkers.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.ServerCommandSource;

public class ShulkerPartArgumentType
		extends EnumArgumentType<ShulkerPart> {

	private ShulkerPartArgumentType() {
		super(ShulkerPart.CODEC, ShulkerPart::values);
	}

	public static ShulkerPartArgumentType shulkerPart() {
		return new ShulkerPartArgumentType();
	}

	public static ShulkerPart getShulkerPart(CommandContext<ServerCommandSource> context, String id) {
		return context.getArgument(id, ShulkerPart.class);
	}
}
