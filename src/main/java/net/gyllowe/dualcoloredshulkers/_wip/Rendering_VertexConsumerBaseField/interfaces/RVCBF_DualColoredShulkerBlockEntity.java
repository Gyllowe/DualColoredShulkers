package net.gyllowe.dualcoloredshulkers._wip.Rendering_VertexConsumerBaseField.interfaces;

import net.minecraft.util.DyeColor;

import javax.annotation.Nullable;

public interface RVCBF_DualColoredShulkerBlockEntity {
    boolean HasSecondaryColor();

    @Nullable
    DyeColor GetSecondaryColor();
}
