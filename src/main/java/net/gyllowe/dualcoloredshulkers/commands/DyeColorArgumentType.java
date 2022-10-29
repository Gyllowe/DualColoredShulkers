package net.gyllowe.dualcoloredshulkers.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Codec;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;

public class DyeColorArgumentType
    extends EnumArgumentType<DyeColor> {

    private DyeColorArgumentType() {
        super(CODEC, DyeColor::values);
    }

    public static DyeColorArgumentType dyeColor() {
        return new DyeColorArgumentType();
    }

    @Nullable
    public static DyeColor getDyeColor(CommandContext<ServerCommandSource> context, String id) {
        return context.getArgument(id, DyeColor.class);
    }

    public static final Codec<DyeColor> CODEC;

    static {
        CODEC = StringIdentifiable.createCodec(DyeColor::values);
    }
}