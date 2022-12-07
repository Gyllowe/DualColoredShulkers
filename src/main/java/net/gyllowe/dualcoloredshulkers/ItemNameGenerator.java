package net.gyllowe.dualcoloredshulkers;

import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;

public abstract class ItemNameGenerator {
	public static Text GenerateText(@Nullable DyeColor primaryColor, DualShulkerColor secondaryColor) {
		DualShulkerColor dualPrimaryColor = DualShulkerColor.FromDyeColor(primaryColor);

		StringBuilder stringBuilder = new StringBuilder();
		if(dualPrimaryColor != DualShulkerColor.BLANK) {
			if(secondaryColor != DualShulkerColor.BLANK) {
				stringBuilder
						.append(dualPrimaryColor.GetNameCapitalized())
						.append(" And ")
						.append(secondaryColor.GetNameCapitalized());
			} else {
				stringBuilder
						.append("Mostly ")
						.append(dualPrimaryColor.GetNameCapitalized());
			}
		}	else {
			stringBuilder
					.append("Partly ")
					.append(secondaryColor.GetNameCapitalized());
		}
		stringBuilder.append(" Shulker Box");

		return Text.of(stringBuilder.toString());
	}
}
