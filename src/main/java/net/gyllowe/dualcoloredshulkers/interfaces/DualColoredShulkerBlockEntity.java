package net.gyllowe.dualcoloredshulkers.interfaces;

import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;

import javax.annotation.Nullable;

public interface DualColoredShulkerBlockEntity {
	boolean HasSecondaryColor();

	@Nullable
	DyeColor GetSecondaryColor();

	ShulkerBoxBlockEntity RemoveSecondaryColor();

	ShulkerBoxBlockEntity SetSecondaryColor(boolean hasSecondaryColor, @Nullable DyeColor color);
}
