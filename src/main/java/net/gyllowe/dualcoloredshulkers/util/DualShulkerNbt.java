package net.gyllowe.dualcoloredshulkers.util;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public abstract class DualShulkerNbt {
	private static final String DUAL_SHULKER_COLOR_KEY = "BaseColor";



		/**
	 * Accepts null values
	 */
	public static boolean contains(NbtCompound nbt) {
		if(nbt == null)
			return false;
		return nbt.contains(DUAL_SHULKER_COLOR_KEY);
	}

	/**
	 * See DualShulkerNbt.Contains(NbtCompound) for null acceptance
	 */
	public static DualShulkerColor readFrom(NbtCompound nbt) {
		if(!contains(nbt))
			return DualShulkerColor.NONE;
		return DualShulkerColor.byId(nbt.getByte(DUAL_SHULKER_COLOR_KEY));
	}

	/**
	 * Doesn't accept null values
	 */
	public static void writeTo(NbtCompound nbt, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.isNone())
			return;
		nbt.putByte(DUAL_SHULKER_COLOR_KEY, dualShulkerColor.getId());
	}

	/**
	 * Doesn't accept null values
	 */
	public static void removeFrom(NbtCompound nbt) {
		nbt.remove(DUAL_SHULKER_COLOR_KEY);
	}

	public static void setNbt(NbtCompound nbt, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.isNone() && contains(nbt))
				removeFrom(nbt);
		else {
			writeTo(nbt, dualShulkerColor);
		}
	}



	/**
	 * Doesn't accept null values for the ItemStack. See DualShulkerNbt.Contains(NbtCompound) for nbt null acceptance
	 */
	public static boolean contains(ItemStack stack) {
		return contains(stack.getNbt());
	}

	/**
	 * Doesn't accept null values for the ItemStack. See DualShulkerNbt.ReadFrom(NbtCompound) for nbt null acceptance
	 */
	public static DualShulkerColor readFrom(ItemStack stack) {
		return readFrom(stack.getNbt());
	}

	/**
	 * Doesn't accept null values for the ItemStack. See DualShulkerNbt.WriteTo(NbtCompound) for nbt null acceptance
	 */
	public static void writeTo(ItemStack stack, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.isNone())
			return;
		writeTo(stack.getNbt(), dualShulkerColor);
	}

	public static void removeFrom(ItemStack stack) {
		removeFrom(stack.getNbt());
	}

	/**
	 * Only used in ShulkerBoxBlockEntity.setStackNbt(ItemStack).
	 * Requires ItemStack to not be null.
	 */
	public static void removeWithinBlockEntityNbt(ItemStack stack) {
		NbtCompound blockEntityNbt = BlockItem.getBlockEntityNbtFromStack(stack);
		if(blockEntityNbt == null)
			return;
		if(blockEntityNbt.getSize() == 2 && blockEntityNbt.contains(DUAL_SHULKER_COLOR_KEY))
			stack.removeSubNbt(BlockItem.BLOCK_ENTITY_TAG_KEY);
		else
			removeFrom(blockEntityNbt);
	}


	public static void setNbt(ItemStack stack, DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor == readFrom(stack))
			return;
		if(dualShulkerColor.notNone()) {
			writeTo(stack.getOrCreateNbt(), dualShulkerColor);
		} else {
			NbtCompound nbt = stack.getNbt();
			if(contains(nbt)) {
				removeFrom(nbt);
			}
		}
	}

}
