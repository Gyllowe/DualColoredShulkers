package net.gyllowe.dualcoloredshulkers.interfaces;

import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;

import javax.annotation.Nullable;

public interface DualColoredShulkerBlockEntity<T extends ShulkerBoxBlockEntity> {
    // TODO: change interface to have getters (and setters if needed) instead of finals
    //boolean hasSecondaryColor = true;
    //@Nullable
    //DyeColor secondaryColor = DyeColor.WHITE;

    boolean HasSecondaryColor();

    @Nullable
    DyeColor GetSecondaryColor();
}
