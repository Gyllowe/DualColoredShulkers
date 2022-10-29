package net.gyllowe.dualcoloredshulkers.commands;

import net.minecraft.util.StringIdentifiable;

public enum ShulkerPart
        implements StringIdentifiable {
    LID("lid"),
    BASE("base"),
    BOTH("both");

    public static final Codec<ShulkerPart> CODEC;
    private final String id;
    private final String name;

    ShulkerPart(String id) {
        this.id = id;
        this.name = "shulker_part." + id;
    }

    @Override
    public String asString() {
        return this.id;
    }

    static {
        CODEC = StringIdentifiable.createCodec(ShulkerPart::values);
    }
}

