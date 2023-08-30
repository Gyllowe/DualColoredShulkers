package net.gyllowe.dualcoloredshulkers.replacements;

import net.gyllowe.dualcoloredshulkers.language.ShulkerName;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerNbt;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;

public class ShulkerBoxItem
		extends BlockItem {
	@Nullable
	public final DyeColor color;


	public ShulkerBoxItem(Block block, Settings settings, @Nullable DyeColor color) {
		super(block, settings);
		this.color = color;
	}

	public ShulkerBoxItem(Block block, Settings settings) {
		this(block, settings, ( (ShulkerBoxBlock) block ).getColor());
	}


	@Override
	public Text getName(ItemStack stack) {
		DualShulkerColor secondaryColor = DualShulkerNbt.readFrom(stack);
		if(secondaryColor.isNone())
			return super.getName(stack);

		return ShulkerName.get(this.color, secondaryColor);
	}


	@Nullable
	public DyeColor getColor() {
		return this.color;
	}

}
