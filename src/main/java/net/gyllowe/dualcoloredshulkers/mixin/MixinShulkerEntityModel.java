package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.gyllowe.dualcoloredshulkers.interfaces.ColoredShulkerModelPart;
import net.gyllowe.dualcoloredshulkers.interfaces.DSVC_ShulkerEntityModel_Render;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShulkerEntityModel.class)
public abstract class MixinShulkerEntityModel<T extends ShulkerEntity>
        extends CompositeEntityModel<T>
        implements DSVC_ShulkerEntityModel_Render {
    @Shadow @Final
    private ModelPart lid;
    @Shadow @Final
    private ModelPart base;


    public void render(MatrixStack matrices, DualShulkerVertexConsumer dualShulkerVertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.lid.render(matrices, dualShulkerVertexConsumer, light, overlay, red, green, blue, alpha);
        // TODO: says "<?>" should be at the end of cast. Unsure if this is necessary
        ((ColoredShulkerModelPart)(Object)this.base).SetShulkerBase(dualShulkerVertexConsumer);
        this.base.render(matrices, dualShulkerVertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
