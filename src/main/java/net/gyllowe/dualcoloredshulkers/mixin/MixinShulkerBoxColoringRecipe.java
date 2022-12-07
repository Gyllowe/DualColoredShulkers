package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerNbt;
import net.gyllowe.dualcoloredshulkers.ShulkerBoxItem;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShulkerBoxColoringRecipe;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerBoxColoringRecipe.class)
public abstract class MixinShulkerBoxColoringRecipe {
	// P.S: every if statement check:
	// if(itemStack.getItem() instanceof ShulkerBoxItem)
	// could be replaced with
	// if(Block.getBlockFromItem(item) instanceof ShulkerBoxBlock)
	// It is less efficient, but might be slightly more friendly to other mods,
	// as they might implement other shulker box items (but this is highly unlikely)

	//private static int _matchesInjectDyeCount = 0;


	@Inject(
			method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z",
			at = @At("HEAD"),
			cancellable = true
	)
	private void CustomMatching(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir) {
		if(craftingInventory.size() == 9)
			cir.setReturnValue(Matches3x3(craftingInventory));
		else if(craftingInventory.size() == 4)
			cir.setReturnValue(Matches2x2(craftingInventory));
	}

	@Inject(
			method = "craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;",
			at = @At("HEAD"),
			cancellable = true
	)
	private void CustomCrafting(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> cir) {
		if(craftingInventory.size() == 9)
			cir.setReturnValue(Craft3x3(craftingInventory));
		else if(craftingInventory.size() == 4)
			cir.setReturnValue(Craft2x2(craftingInventory));
	}


	// Safety net matching mixin(s)
	/*
	@ModifyVariable(
			method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z",
			at = @At("STORE"),
			ordinal = 1
	)
	private int DecreaseDyeCount(int value) {
		return -100;
	}

	@Inject(
			method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z",
			at = @At(
					value = "JUMP",
					opcode = Opcodes.IFEQ,
					ordinal = 3,
					shift = At.Shift.AFTER
			)
	)
	private void CountDyes(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir) {
		_matchesInjectDyeCount++;
	}

	@Inject(
			method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z",
			at = @At("TAIL"),
			locals = LocalCapture.PRINT,
			cancellable = true
	)
	private void CustomReturn(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir, int i) {
		cir.setReturnValue(i == 1 && _matchesInjectDyeCount > 0 && _matchesInjectDyeCount < 3);
	}
	 */

	// Safety net crafting mixin


	private boolean Matches3x3(CraftingInventory craftingInventory) {
		
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
					shulkerColor = DualShulkerColor.FromDyeColor(shulkerBoxItem.getColor());
					shulkerBottomColor = DualShulkerNbt.ReadFrom(stack);
					if(shulkerBottomColor.isNone())
						shulkerBottomColor = shulkerColor;
				} else if(item instanceof DyeItem dyeItem) {
					int row = i / 3;

					if(row != 2 && dyeingFullShulker == null) {
						dyeingFullShulker = (row == 1);
						fullOrPrimaryColor = DualShulkerColor.FromDyeColor(dyeItem.getColor());
					} else if(row == 2 && !Boolean.TRUE.equals(dyeingFullShulker) && secondaryColor.isNone()) {
						dyeingFullShulker = false;
						secondaryColor = DualShulkerColor.FromDyeColor(dyeItem.getColor());
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

	private boolean Matches2x2(CraftingInventory craftingInventory) {

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


	private ItemStack Craft3x3(CraftingInventory craftingInventory) {
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
			if(firstItemSlot > 3 && i == 3)
				i = firstItemSlot;

			ItemStack stack = craftingInventory.getStack(i);
			if(stack.isEmpty())
				continue;

			Item item = stack.getItem();
			if(item instanceof ShulkerBoxItem)
				shulkerItemStack = stack;

			else if(item instanceof DyeItem dyeItem) {
				int rowOrColumn = i / rowAndColumnDivider;

				if(!foundFirstDye && rowOrColumn != 2) {
					foundFirstDye = true;
					fullOrPrimaryColor = dyeItem.getColor();
					if(rowOrColumn == 1)
						secondaryColor = DualShulkerColor.NONE;
				} else
					secondaryColor = DualShulkerColor.FromDyeColor(dyeItem.getColor());
			}
		}

		if(secondaryColor.isNone())
			// coloring the whole shulker
			return GenerateCraftingResult(shulkerItemStack, fullOrPrimaryColor);
		else
			// coloring parts individually
			return GenerateCraftingResult(shulkerItemStack, fullOrPrimaryColor, secondaryColor);
	}

	private ItemStack Craft2x2(CraftingInventory craftingInventory) {

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
				return GenerateCraftingResult(shulkerItemStack, firstColor);

			if(shulkerRow + 1 == singleColorRow)
				return GenerateCraftingResult(shulkerItemStack, null, DualShulkerColor.FromDyeColor(firstColor));
		}

		return GenerateCraftingResult(shulkerItemStack, firstColor, DualShulkerColor.FromDyeColor(secondColor));
	}


	/**
	 * If a parts color is unchanged, pass the corresponding dye value for the default shulker box color
	 * (null for DyeColor/primaryColor and DualShulkerColor.BLANK for secondaryColor)
	 * @param inputStack
	 * @param primaryColor
	 * @param secondaryColor
	 */
	private ItemStack GenerateCraftingResult(ItemStack inputStack, @Nullable DyeColor primaryColor, DualShulkerColor secondaryColor) {
		if(primaryColor == null) {
			primaryColor = ( (ShulkerBoxItem) inputStack.getItem() ).getColor();
			//primaryColor = ShulkerBoxBlock.getColor(inputStack.getItem());
		}
		ItemStack craftedStack = ShulkerBoxBlock.getItemStack(primaryColor);

		if(secondaryColor == DualShulkerColor.BLANK) {
			secondaryColor = DualShulkerNbt.ReadFrom(inputStack);
			if(secondaryColor.isNone()) {
				secondaryColor = DualShulkerColor.FromDyeColor(ShulkerBoxBlock.getColor(inputStack.getItem()));
			}
		}
		if(primaryColor == secondaryColor.ToDyeColor())
			secondaryColor = DualShulkerColor.NONE;
		if(inputStack.hasNbt())
			craftedStack.setNbt(inputStack.getNbt().copy());
		DualShulkerNbt.SetNbt(craftedStack, secondaryColor);
		return craftedStack;
	}

	/**
	 * Coloring the whole shulker box a single color
	 * @param inputStack
	 * @param primaryColor
	 */
	private ItemStack GenerateCraftingResult(ItemStack inputStack, DyeColor primaryColor) {
		return GenerateCraftingResult(inputStack, primaryColor, DualShulkerColor.NONE);
	}
}
