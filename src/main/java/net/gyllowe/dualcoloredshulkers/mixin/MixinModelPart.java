package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.gyllowe.dualcoloredshulkers.interfaces.ColoredShulkerModelPart;
import net.gyllowe.dualcoloredshulkers.interfaces.ColoredShulkerModelPartCuboid;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ModelPart.class)
public abstract class MixinModelPart
        implements ColoredShulkerModelPart {
    @Shadow @Final
    private List<ModelPart.Cuboid> cuboids;
    private Boolean isShulkerBase = false;
    private DualShulkerVertexConsumer dualShulkerVertexConsumer;

    public ColoredShulkerModelPart SetShulkerBase(DualShulkerVertexConsumer dualShulkerVertexConsumer) {
        this.isShulkerBase = true;
        this.dualShulkerVertexConsumer = dualShulkerVertexConsumer;
        return (ColoredShulkerModelPart)(Object)this;
    }


    @Inject(
            method = "renderCuboids",
            at = @At(value = "HEAD"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    void inject(MatrixStack.Entry entry, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        if(this.isShulkerBase) {
            for (ModelPart.Cuboid cuboid : this.cuboids) {
                ((ColoredShulkerModelPartCuboid)cuboid).SetShulkerBase(this.dualShulkerVertexConsumer);
                cuboid.renderCuboid(entry, null, light, overlay, red, green, blue, alpha);
            }
            ci.cancel();
        }
    }
}
