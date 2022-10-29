package net.gyllowe.dualcoloredshulkers._wip.Rendering_VertexConsumerBaseField.mixin;

import net.gyllowe.dualcoloredshulkers._wip.Rendering_SecondAttempt.interfaces.RSA_DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class RVCBF_MixinShulkerBoxBlockEntity
        implements RSA_DualColoredShulkerBlockEntity {
    //@Final @Mutable
    boolean hasSecondaryColor = true;//false;
    @Nullable //@Final @Mutable
    DyeColor secondaryColor = DyeColor.WHITE;//null;


    public boolean HasSecondaryColor() {
        return hasSecondaryColor;
    }
    @Nullable
    public DyeColor GetSecondaryColor() {
        return secondaryColor;
    }
}
