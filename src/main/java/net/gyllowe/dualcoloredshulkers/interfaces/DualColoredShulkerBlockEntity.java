package net.gyllowe.dualcoloredshulkers.interfaces;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;

public interface DualColoredShulkerBlockEntity {
	DualShulkerColor DualColoredShulkers$getSecondaryColor();

	void DualColoredShulkers$setSecondaryColor(DualShulkerColor secondaryColor);

	void DualColoredShulkers$removeSecondaryColor();
}
