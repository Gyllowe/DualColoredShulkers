package net.gyllowe.dualcoloredshulkers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import javax.annotation.Nullable;

public abstract class DualShulkerNbt {
	private static final String BLOCK_ENTITY_TAG_KEY = "BlockEntityTag";
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


	/**
	 * Returns null if the stack doesn't contain secondary color nbt
	 */
	@Nullable
	public static DualShulkerColor ReadFromItemStack(ItemStack stack) {
		NbtCompound nbt = stack.getNbt();
		if(nbt == null) {
			return null;
		}
		if(!nbt.contains(BLOCK_ENTITY_TAG_KEY)) {
			return null;
		}
		NbtElement blockEntityNbt = nbt.get(BLOCK_ENTITY_TAG_KEY);
		if(!(blockEntityNbt instanceof NbtCompound blockEntityNbtCompound)) {
			return null;
		}
		if(!Contains(blockEntityNbtCompound)) {
			return null;
		}
		return ReadFrom(blockEntityNbtCompound);
	}
}
