package net.gyllowe.dualcoloredshulkers;

import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

import javax.annotation.Nullable;

public abstract class ItemNameGenerator {
	public static Text GenerateText(@Nullable DyeColor primaryColor, DualShulkerColor secondaryColor) {
		DualShulkerColor dualMainColor = DualShulkerColor.FromDyeColor(primaryColor);

		StringBuilder stringBuilder = new StringBuilder();
		if(dualMainColor != DualShulkerColor.BLANK) {
			stringBuilder
					.append(dualMainColor.GetNameCapitalized())
					.append(" And ");
		} else {
			stringBuilder.append("Partly ");
		}
		stringBuilder
				.append(secondaryColor.GetNameCapitalized())
				.append(" Shulker Box");

		return Text.of(stringBuilder.toString());
	}
}
