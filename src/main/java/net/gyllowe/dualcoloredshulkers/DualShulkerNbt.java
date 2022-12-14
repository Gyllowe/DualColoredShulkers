package net.gyllowe.dualcoloredshulkers;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public abstract class DualShulkerNbt {
	private static final String DUAL_SHULKER_COLOR_KEY = "BaseColor";



		/**
	 * Accepts null values
	 */
	public static boolean Contains(NbtCompound nbt) {
		if(nbt == null)
			return false;
		return nbt.contains(DUAL_SHULKER_COLOR_KEY);
	}

	/**
	 * See DualShulkerNbt.Contains(NbtCompound) for null acceptance
	 */
	public static DualShulkerColor ReadFrom(NbtCompound nbt) {
		if(!Contains(nbt))
			return DualShulkerColor.NONE;
		return DualShulkerColor.byId(nbt.getByte(DUAL_SHULKER_COLOR_KEY));
	}

	/**
	 * Doesn't accept null values
	 */
	public static void WriteTo(NbtCompound nbt, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.isNone())
			return;
		nbt.putByte(DUAL_SHULKER_COLOR_KEY, dualShulkerColor.getId());
	}

	/**
	 * Doesn't accept null values
	 */
	public static void RemoveNbt(NbtCompound nbt) {
		nbt.remove(DUAL_SHULKER_COLOR_KEY);
	}

	public static void SetNbt(NbtCompound nbt, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.isNone() && Contains(nbt))
				RemoveNbt(nbt);
		else {
			WriteTo(nbt, dualShulkerColor);
		}
	}



	/**
	 * Doesn't accept null values for the ItemStack. See DualShulkerNbt.Contains(NbtCompound) for nbt null acceptance
	 */
	public static boolean Contains(ItemStack stack) {
		return Contains(stack.getNbt());
	}

	/**
	 * Doesn't accept null values for the ItemStack. See DualShulkerNbt.ReadFrom(NbtCompound) for nbt null acceptance
	 */
	public static DualShulkerColor ReadFrom(ItemStack stack) {
		return ReadFrom(stack.getNbt());
	}

	/**
	 * Doesn't accept null values for the ItemStack. See DualShulkerNbt.WriteTo(NbtCompound) for nbt null acceptance
	 */
	public static void WriteTo(ItemStack stack, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.isNone())
			return;
		WriteTo(stack.getNbt(), dualShulkerColor);
	}

	/**
	 * Only used in ShulkerBoxBlockEntity.setStackNbt(ItemStack).
	 * Requires ItemStack to not be null.
	 */
	public static void RemoveWithinBlockEntityNbt(ItemStack stack) {
		NbtCompound blockEntityNbt = BlockItem.getBlockEntityNbt(stack);
		if(blockEntityNbt == null)
			return;
		if(blockEntityNbt.getSize() == 2 && blockEntityNbt.contains(DUAL_SHULKER_COLOR_KEY))
			stack.removeSubNbt(BlockItem.BLOCK_ENTITY_TAG_KEY);
		else
			RemoveNbt(blockEntityNbt);
	}

	public static void RemoveNbt(ItemStack stack) {
		RemoveNbt(stack.getNbt());
	}


	public static void SetNbt(ItemStack stack, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor == ReadFrom(stack))
			return;
		if(dualShulkerColor.notNone()) {
			WriteTo(stack.getOrCreateNbt(), dualShulkerColor);
		} else {
			NbtCompound nbt = stack.getNbt();
			if(Contains(nbt)) {
				RemoveNbt(nbt);
			}
		}
	}
}
