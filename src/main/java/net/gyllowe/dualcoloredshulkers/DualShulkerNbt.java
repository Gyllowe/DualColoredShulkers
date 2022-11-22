package net.gyllowe.dualcoloredshulkers;

import net.minecraft.nbt.NbtCompound;

public abstract class DualShulkerNbt {
	private static final String DUAL_SHULKER_COLOR_KEY = "BaseColor";
	//private static final boolean nbtSaveNoColor = false;


	public static boolean Contains(NbtCompound nbt) {
		return nbt.contains(DUAL_SHULKER_COLOR_KEY);
	}

	public static DualShulkerColor ReadFrom(NbtCompound nbt) {
		if(!Contains(nbt)) {
			return DualShulkerColor.NONE;
		}
		return DualShulkerColor.byId(nbt.getByte(DUAL_SHULKER_COLOR_KEY));
	}

	public static void WriteTo(NbtCompound nbt, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.isNone()) {
			return;
		}
		nbt.putByte(DUAL_SHULKER_COLOR_KEY, dualShulkerColor.getId());
	}

	public static void RemoveNbt(NbtCompound nbt) {
		nbt.remove(DUAL_SHULKER_COLOR_KEY);
	}
}
