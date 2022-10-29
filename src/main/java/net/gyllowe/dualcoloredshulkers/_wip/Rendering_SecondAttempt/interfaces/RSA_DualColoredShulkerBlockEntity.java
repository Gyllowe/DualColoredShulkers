package net.gyllowe.dualcoloredshulkers._wip.Rendering_SecondAttempt.interfaces;

import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;

import javax.annotation.Nullable;

public interface RSA_DualColoredShulkerBlockEntity {
    boolean HasSecondaryColor();

    @Nullable
    DyeColor GetSecondaryColor();

    ShulkerBoxBlockEntity RemoveSecondaryColor();

    ShulkerBoxBlockEntity SetSecondaryColor(boolean hasSecondaryColor, @Nullable DyeColor color);
}
