package net.gyllowe.dualcoloredshulkers.util;

import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.gyllowe.dualcoloredshulkers.commands.DyeColorArgumentType;
import net.gyllowe.dualcoloredshulkers.commands.ShulkerPartArgumentType;
import net.gyllowe.dualcoloredshulkers.commands.DualShulkerBlockCommand;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

import static net.gyllowe.dualcoloredshulkers.DualColoredShulkers.MOD_ID;

public class ModRegistries {
    public static void RegisterAll() {
        RegisterCommandsAndArgumentTypes();
    }


    private static void RegisterCommandsAndArgumentTypes() {
        ArgumentTypeRegistry.registerArgumentType(
            new Identifier(MOD_ID, "dyecolor"),
            DyeColorArgumentType.class,
            ConstantArgumentSerializer.of(DyeColorArgumentType::dyeColor)
        );
        ArgumentTypeRegistry.registerArgumentType(
            new Identifier(MOD_ID, "shulkerpart"),
            ShulkerPartArgumentType.class,
            ConstantArgumentSerializer.of(ShulkerPartArgumentType::shulkerPart)
        );
        CommandRegistrationCallback.EVENT.register(DualShulkerBlockCommand::register);
        //CommandRegistrationCallback.EVENT.register(DualShulkerHoldingCommand::register);
    }
}
