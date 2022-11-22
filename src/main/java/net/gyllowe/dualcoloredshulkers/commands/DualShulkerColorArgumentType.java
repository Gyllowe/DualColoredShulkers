package net.gyllowe.dualcoloredshulkers.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Codec;
import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;

public class DualShulkerColorArgumentType
    extends EnumArgumentType<DualShulkerColor> {

    private DualShulkerColorArgumentType() {
        super(CODEC, DualShulkerColor::values);
    }

    public static DualShulkerColorArgumentType dualShulkerColor() {
        return new DualShulkerColorArgumentType();
    }

    @Nullable
    public static DualShulkerColor getDualShulkerColor(CommandContext<ServerCommandSource> context, String id) {
        return context.getArgument(id, DualShulkerColor.class);
    }

    public static final Codec<DualShulkerColor> CODEC;

    static {
        CODEC = StringIdentifiable.createCodec(DualShulkerColor::values);
    }
}