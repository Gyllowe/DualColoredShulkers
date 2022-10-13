package net.gyllowe.dualcoloredshulkers.interfaces;

import net.minecraft.util.DyeColor;

import javax.annotation.Nullable;

public interface DualColoredShulkerBlockEntity {
    // TODO: change interface to have getters (and setters if needed) instead of finals
    boolean hasSecondaryColor = true;
    @Nullable
    DyeColor secondaryColor = DyeColor.WHITE;
}
