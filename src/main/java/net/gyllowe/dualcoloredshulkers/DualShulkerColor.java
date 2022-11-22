package net.gyllowe.dualcoloredshulkers;

import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public enum DualShulkerColor
		implements StringIdentifiable {
	NONE(-2),
	BLANK(-1),
	WHITE(0),
	ORANGE(1),
	MAGENTA(2),
	LIGHT_BLUE(3, "light blue"),
	YELLOW(4),
	LIME(5),
	PINK(6),
	GRAY(7),
	LIGHT_GRAY(8, "light gray"),
	CYAN(9),
	PURPLE(10),
	BLUE(11),
	BROWN(12),
	GREEN(13),
	RED(14),
	BLACK(15);


	private static final DualShulkerColor[] VALUES;
	private final byte id;
	private final String name;


	DualShulkerColor(int id, String name) {
		this.id = (byte)id;
		this.name = (name == null) ? this.name().toLowerCase() : name;
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


	public static DualShulkerColor getRandom() {
		Random rand = new Random();
		return DualShulkerColor.byId( (byte)( rand.nextInt(256) - 128 ) );
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


	public String toString() {
		return this.name;
	}

	@Override
	public String asString() {
		return this.GetNameNoSpace().toLowerCase();
	}


	public String toStringLowercase() {
		return this.toString().toLowerCase();
	}

	public String GetNameNoSpace() {
		return this.name();
	}

	public String GetNameCapitalized() {
		String name = this.toString();
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}


	@Nullable
	public DyeColor ToDyeColor() {
		if(this.id < 0) {
			return null;
		}
		return DyeColor.byId(this.id);
	}



	static {
		VALUES = Arrays.stream(DualShulkerColor.values()).sorted(Comparator.comparingInt(DualShulkerColor::getId)).toArray(DualShulkerColor[]::new);
	}
}
