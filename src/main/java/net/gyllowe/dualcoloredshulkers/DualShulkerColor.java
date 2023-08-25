package net.gyllowe.dualcoloredshulkers;

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
		this.translationKey = ( (translationKeyEnd == null) ? this.name().toLowerCase() : translationKeyEnd );
	}

	DualShulkerColor(int id) {
		this(id, null);
	}



	public boolean isNone() {
		return this == DualShulkerColor.NONE;
	}
	public boolean notNone() {
		return !isNone();
	}


	public byte getId() {
		return this.id;
	}

	public byte GetDyeColorCompatibleId() {
		if(this.id < 0) {
			return 0;
		}
		return this.getId();
	}

	public static DualShulkerColor byId(byte id) {
		id += 2;
		if(id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}


	public String getTranslationKey() {
		return this.translationKey;
	}


	@Nullable
	public DyeColor ToDyeColor() {
		if(this.id < 0) {
			return null;
		}
		return DyeColor.byId(this.id);
	}

	public static DualShulkerColor FromDyeColor(@Nullable DyeColor dyeColor) {
		return (dyeColor == null) ? DualShulkerColor.BLANK : DualShulkerColor.byId( (byte) dyeColor.getId() );
	}



	static {
		VALUES = Arrays.stream(DualShulkerColor.values()).sorted(Comparator.comparingInt(DualShulkerColor::getId)).toArray(DualShulkerColor[]::new);
	}
}
