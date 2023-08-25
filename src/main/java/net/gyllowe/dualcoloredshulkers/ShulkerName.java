package net.gyllowe.dualcoloredshulkers;

import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;

public abstract class ShulkerName {
	private static final String
			DUAL_COLORED_SYNTAX_KEY = "dcs.dual_syntax",
			LID_COLORED_SYNTAX_KEY = "dcs.only_lid_syntax",
			BASE_COLORED_SYNTAX_KEY = "dcs.only_base_syntax",
			SYNTAX_CAPITALISATION = ".caps",

			SHULKER_KEY = "shulker_box",
			LOWER_KEY = "dcs.lower.",
			FIRST_CAPITALISED_KEY = "dcs.first_capitalized.",
			ALL_CAPITALISED_KEY = "dcs.all_capitalized.";

	// Map for syntaxCapitalisation:
	// 0, x - dual colored
	//	 0, 0 - primary color
	//	 0, 1 - secondary color
	//	 0, 2 - shulker box
	// 1, x - lid colored
	//	 1, 0 - primary color
	//	 1, 1 - shulker box
	// 2, x - base colored
	//	 2, 0 - secondary color
	//	 2, 1 - shulker box
	private static final byte[][] syntaxCapitalisation = new byte[][]{
			{0, 0, 0},
			{0, 0},
			{0, 0}
	};



	public static void reloadSyntaxCaps() {
		char[][] languageCaps = {
				Text.translatable(DUAL_COLORED_SYNTAX_KEY + SYNTAX_CAPITALISATION).getString().toCharArray(),
				Text.translatable(LID_COLORED_SYNTAX_KEY + SYNTAX_CAPITALISATION).getString().toCharArray(),
				Text.translatable(BASE_COLORED_SYNTAX_KEY + SYNTAX_CAPITALISATION).getString().toCharArray()
		};
		for(int i = 0; i < syntaxCapitalisation.length; i++) {
			for(int j = 0; j < syntaxCapitalisation[i].length; j++) {
				syntaxCapitalisation[i][j] = (byte) Character.getNumericValue(languageCaps[i][j]);
			}
		}
	}


	public static Text get(@Nullable DyeColor primaryColor, DualShulkerColor secondaryColor) {
		// It is assumed that secondaryColor isn't NONE and that the two colors aren't the same

		if(primaryColor == null)
			// Lid is not colored
			return Text.translatable(
					BASE_COLORED_SYNTAX_KEY,
					getCapitalisation(secondaryColor.getTranslationKey(), syntaxCapitalisation[2][0]),
					getCapitalisation(SHULKER_KEY, syntaxCapitalisation[2][1])
			);

		if(secondaryColor != DualShulkerColor.BLANK)
			// Both parts have colors
			return Text.translatable(
					DUAL_COLORED_SYNTAX_KEY,
					getCapitalisation(primaryColor.toString(), syntaxCapitalisation[0][0]),
					getCapitalisation(secondaryColor.getTranslationKey(), syntaxCapitalisation[0][1]),
					getCapitalisation(SHULKER_KEY, syntaxCapitalisation[0][2])
			);

		// Base is not colored
		return Text.translatable(
				LID_COLORED_SYNTAX_KEY,
				getCapitalisation(primaryColor.toString(), syntaxCapitalisation[1][0]),
				getCapitalisation(SHULKER_KEY, syntaxCapitalisation[1][1])
		);
	}


	private static Text getCapitalisation(String key, byte capitalisation) {
		return switch(capitalisation) {
			case 0 -> Text.translatable(LOWER_KEY + key);
			case 1 -> Text.translatable(FIRST_CAPITALISED_KEY + key);
			default -> Text.translatable(ALL_CAPITALISED_KEY + key);
		};
	}
}
