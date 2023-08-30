package net.gyllowe.dualcoloredshulkers.util;

import net.gyllowe.dualcoloredshulkers.replacements.ShulkerBoxItem;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;

public abstract class DualShulkerCrafting {
	// P.S: every if statement check:
	// if(itemStack.getItem() instanceof ShulkerBoxItem)
	// could be replaced with
	// if(Block.getBlockFromItem(item) instanceof ShulkerBoxBlock)
	// It is less efficient, but might be slightly more friendly to other mods,
	// as they might implement other shulker box items (but this is highly unlikely)


	public static boolean matches3x3(CraftingInventory craftingInventory) {

		byte firstItemSlot = -1;
		for(byte i = 3; i < 9; i++) {
			if(!craftingInventory.getStack(i).isEmpty()) {
				firstItemSlot = i;
				break;
			}
		}


		boolean shulkerFound = false;

		if(firstItemSlot == -1) {
			// quick crafting
			boolean dyeFound = false;

			for(byte i = 0; i < 3; i++) {
				ItemStack stack = craftingInventory.getStack(i);
				if(stack.isEmpty())
					continue;

				Item item = stack.getItem();
				if(item instanceof ShulkerBoxItem) {
					if(shulkerFound)
						return false;
					shulkerFound = true;

				} else if(item instanceof DyeItem) {
					dyeFound = true;

				} else
					return false;
			}

			return shulkerFound && dyeFound;

		} else {
			// normal crafting

			// The shulker colors are stored as DualShulkerColors, as their only use is to compare to other DualShulkerColors
			DualShulkerColor shulkerColor = DualShulkerColor.NONE, shulkerBottomColor = DualShulkerColor.NONE;

			// Also used to indicate if any dyes have been found
			Boolean dyeingFullShulker = null;
			DualShulkerColor fullOrPrimaryColor = DualShulkerColor.NONE, secondaryColor = DualShulkerColor.NONE;


			for(byte i = 0; i < 9; i++) {
				if(firstItemSlot > 3 && i == 3)
					i = firstItemSlot;

				ItemStack stack = craftingInventory.getStack(i);
				if(stack.isEmpty())
					continue;

				Item item = stack.getItem();
				if(item instanceof ShulkerBoxItem shulkerBoxItem) {
					if(shulkerFound)
						return false;
					shulkerFound = true;
					shulkerColor = DualShulkerColor.fromDyeColor(shulkerBoxItem.getColor());
					shulkerBottomColor = DualShulkerNbt.readFrom(stack);
					if(shulkerBottomColor.isNone())
						shulkerBottomColor = shulkerColor;
				} else if(item instanceof DyeItem dyeItem) {
					int row = i / 3;

					if(row != 2 && dyeingFullShulker == null) {
						dyeingFullShulker = (row == 1);
						fullOrPrimaryColor = DualShulkerColor.fromDyeColor(dyeItem.getColor());
					} else if(row == 2 && !Boolean.TRUE.equals(dyeingFullShulker) && secondaryColor.isNone()) {
						dyeingFullShulker = false;
						secondaryColor = DualShulkerColor.fromDyeColor(dyeItem.getColor());
					} else
						return false;

				} else
					return false;
			}

			// Fail if no shulker or no dyes were found
			if(!shulkerFound || dyeingFullShulker == null)
				return false;

			// Fail if the dyes are wasteful
			if(Boolean.TRUE.equals(dyeingFullShulker)) {
				if(fullOrPrimaryColor == shulkerColor && fullOrPrimaryColor == shulkerBottomColor)
					return false;
			} else if(fullOrPrimaryColor == shulkerColor || secondaryColor == shulkerBottomColor)
				return false;

			// yay!
			return true;
		}
	}

	public static boolean matches2x2(CraftingInventory craftingInventory) {

		boolean shulkerFound = false;
		byte dyes = 0;

		for(byte i = 0; i < 4; i++) {
			ItemStack stack = craftingInventory.getStack(i);
			if(stack.isEmpty())
				continue;

			Item item = stack.getItem();
			if(item instanceof ShulkerBoxItem) {
				if(shulkerFound)
					return false;
				shulkerFound = true;

			} else if(item instanceof DyeItem) {
				dyes++;

			} else
				return false;
		}

		return shulkerFound && dyes > 0 && dyes < 3;
	}


	public static ItemStack craft3x3(CraftingInventory craftingInventory) {
		// The logic for quick crafting requires that all items are in the top row
		boolean quickCraft = true;
		byte firstItemSlot = -1;

		for(byte i = 3; i < 9; i++) {
			if(!craftingInventory.getStack(i).isEmpty()) {
				quickCraft = false;
				firstItemSlot = i;
				break;
			}
		}

		ItemStack shulkerItemStack = ItemStack.EMPTY;

		boolean foundFirstDye = false;
		DyeColor fullOrPrimaryColor = null;
		DualShulkerColor secondaryColor = DualShulkerColor.BLANK;

		int endAt = (quickCraft) ? 3 : 9;
		int rowAndColumnDivider = (quickCraft) ? 1 : 3;

		for(byte i = 0; i < endAt; i++) {
			// Skip empty slots checked in quick craft checking
			if(firstItemSlot > 3 && i == 3)
				i = firstItemSlot;

			ItemStack stack = craftingInventory.getStack(i);
			if(stack.isEmpty())
				continue;

			Item item = stack.getItem();
			if(item instanceof ShulkerBoxItem) {
				shulkerItemStack = stack;
				continue;
			}
			if(!(item instanceof DyeItem dyeItem))
				continue;

			// The region being scanned
			// Normal and quick crafting have different regions:
			// columns in quick crafting and rows in normal crafting
			int region = i / rowAndColumnDivider;

			if(!foundFirstDye && region != 2) {
				foundFirstDye = true;
				fullOrPrimaryColor = dyeItem.getColor();
				if(region == 1)
					secondaryColor = DualShulkerColor.NONE;
			} else {
				secondaryColor = DualShulkerColor.fromDyeColor(dyeItem.getColor());
			}
		}

		if(secondaryColor.isNone())
			// coloring the whole shulker
			return generateCraftingResult(shulkerItemStack, fullOrPrimaryColor);
		else
			// coloring parts individually
			return generateCraftingResult(shulkerItemStack, fullOrPrimaryColor, secondaryColor);
	}

	public static ItemStack craft2x2(CraftingInventory craftingInventory) {

		int shulkerRow = 0;
		ItemStack shulkerItemStack = ItemStack.EMPTY;

		int singleColorRow = -1;
		boolean secondColorFound = false;
		DyeColor firstColor = null, secondColor = null;

		for(byte i = 0; i < 4; i++) {
			ItemStack stack = craftingInventory.getStack(i);
			if(stack.isEmpty())
				continue;

			int row = i / 2;

			Item item = stack.getItem();
			if(item instanceof ShulkerBoxItem) {
				shulkerItemStack = stack;
				shulkerRow = row;
			} else if(item instanceof DyeItem dyeItem) {
				if(singleColorRow == -1) {
					firstColor = dyeItem.getColor();
					singleColorRow = row;
				} else {
					secondColor = dyeItem.getColor();
					secondColorFound = true;
				}
			}
		}

		if(!secondColorFound) {
			// Only one color found
			if(shulkerRow == singleColorRow)
				return generateCraftingResult(shulkerItemStack, firstColor);

			if(shulkerRow + 1 == singleColorRow)
				return generateCraftingResult(shulkerItemStack, null, DualShulkerColor.fromDyeColor(firstColor));
		}

		return generateCraftingResult(shulkerItemStack, firstColor, DualShulkerColor.fromDyeColor(secondColor));
	}


	/**
	 * If a parts color is unchanged, pass the corresponding dye value for the default shulker box color
	 * (null for DyeColor/primaryColor and DualShulkerColor.BLANK for secondaryColor)
	 * @param inputStack
	 * @param primaryColor
	 * @param secondaryColor
	 */
	public static ItemStack generateCraftingResult(ItemStack inputStack, @Nullable DyeColor primaryColor, DualShulkerColor secondaryColor) {
		if(primaryColor == null)
			primaryColor = ( (ShulkerBoxItem) inputStack.getItem() ).getColor();

		ItemStack craftedStack = ShulkerBoxBlock.getItemStack(primaryColor);

		if(secondaryColor == DualShulkerColor.BLANK) {
			secondaryColor = DualShulkerNbt.readFrom(inputStack);
			if(secondaryColor.isNone())
				secondaryColor = DualShulkerColor.fromDyeColor(ShulkerBoxBlock.getColor(inputStack.getItem()));
		}
		if(primaryColor == secondaryColor.toDyeColor())
			secondaryColor = DualShulkerColor.NONE;
		if(inputStack.hasNbt())
			craftedStack.setNbt(inputStack.getNbt().copy());
		DualShulkerNbt.setNbt(craftedStack, secondaryColor);
		return craftedStack;
	}

	/**
	 * Coloring the whole shulker box a single color
	 * @param inputStack
	 * @param primaryColor
	 */
	public static ItemStack generateCraftingResult(ItemStack inputStack, DyeColor primaryColor) {
		return generateCraftingResult(inputStack, primaryColor, DualShulkerColor.NONE);
	}

}
