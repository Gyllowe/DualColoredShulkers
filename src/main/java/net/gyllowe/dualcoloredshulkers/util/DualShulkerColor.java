package net.gyllowe.dualcoloredshulkers.util;

import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public enum DualShulkerColor {
	NONE(-2),
	BLANK(-1),
	WHITE(0),
	ORANGE(1),
	MAGENTA(2),
	LIGHT_BLUE(3),
	YELLOW(4),
	LIME(5),
	PINK(6),
	GRAY(7),
	LIGHT_GRAY(8),
	CYAN(9),
	PURPLE(10),
	BLUE(11),
	BROWN(12),
	GREEN(13),
	RED(14),
	BLACK(15);


	private static final DualShulkerColor[] VALUES;
	private final byte id;
	private final String translationKey;


	DualShulkerColor(int id, String translationKeyEnd) {
		this.id = (byte)id;
		this.translationKey = (translationKeyEnd == null) ? this.name().toLowerCase() : translationKeyEnd;
	}

	DualShulkerColor(int id) {
		this(id, null);
	}



	public static DualShulkerColor byId(byte id) {
		return (id < -2 || id + 2 >= VALUES.length) ? DualShulkerColor.NONE : VALUES[id + 2];
	}

	public byte getId() {
		return this.id;
	}


	public boolean isNone() {
		return this == DualShulkerColor.NONE;
	}
	public boolean notNone() {
		return !isNone();
	}


	@Nullable
	public DyeColor toDyeColor() {
		return (this.id < 0) ? null : DyeColor.byId(this.id);
	}

	public static DualShulkerColor fromDyeColor(@Nullable DyeColor dyeColor) {
		return (dyeColor == null) ? DualShulkerColor.BLANK : DualShulkerColor.byId( (byte) dyeColor.getId() );
	}


	public byte getDyeColorCompatibleId() {
		return (this.id < 0) ? 0 : this.id;
	}

	public String getTranslationKey() {
		return this.translationKey;
	}



	static {
		VALUES = Arrays.stream(DualShulkerColor.values()).sorted(Comparator.comparingInt(DualShulkerColor::getId)).toArray(DualShulkerColor[]::new);
	}
}
