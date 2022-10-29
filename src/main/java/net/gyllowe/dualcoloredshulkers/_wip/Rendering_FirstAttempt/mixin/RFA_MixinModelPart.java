package net.gyllowe.dualcoloredshulkers._wip.Rendering_FirstAttempt.mixin;

import net.gyllowe.dualcoloredshulkers._wip.Rendering_FirstAttempt.interfaces.ModelPartSetShulkerBase;
import net.gyllowe.dualcoloredshulkers._wip.Rendering_FirstAttempt.interfaces.ModelPartCuboidSetShulkerBase;
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
public abstract class RFA_MixinModelPart
        implements ModelPartSetShulkerBase {
    @Shadow @Final
    private List<ModelPart.Cuboid> cuboids;
    private Boolean isShulkerBase = false;

    public ModelPartSetShulkerBase SetShulkerBase() {
        this.isShulkerBase = true;
        return (ModelPartSetShulkerBase)(Object)this;
    }
    public boolean IsBase() {return this.isShulkerBase;}


    @Inject(
            method = "renderCuboids",
            at = @At(value = "HEAD"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    void inject(MatrixStack.Entry entry, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        if(this.isShulkerBase) {
            for (ModelPart.Cuboid cuboid : this.cuboids) {
                ( (ModelPartCuboidSetShulkerBase) cuboid ).SetShulkerBase();
                cuboid.renderCuboid(entry, vertexConsumer, light, overlay, red, green, blue, alpha);
            }
            ci.cancel();
        }
    }
}
