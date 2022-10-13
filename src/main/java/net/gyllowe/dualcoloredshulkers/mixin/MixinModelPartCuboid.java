package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.gyllowe.dualcoloredshulkers.interfaces.ColoredShulkerModelPartCuboid;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelPart.Cuboid.class)
public abstract class MixinModelPartCuboid
        implements ColoredShulkerModelPartCuboid {
    private Boolean isShulkerBase = false;
    private DualShulkerVertexConsumer dualShulkerVertexConsumer;


    public ColoredShulkerModelPartCuboid SetShulkerBase(DualShulkerVertexConsumer dualShulkerVertexConsumer) {
        this.isShulkerBase = true;
        this.dualShulkerVertexConsumer = dualShulkerVertexConsumer;
        return (ColoredShulkerModelPartCuboid)(Object)this;
    }


    @Redirect(
            method = "renderCuboid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/VertexConsumer;vertex(FFFFFFFFFIIFFF)V"
            )
    )
    private void inject(VertexConsumer instance, float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ) {
        // !! method being mixed into (renderCuboid) takes
        //  LIGHT  - OVERLAY
        // but VertexConsumer.vertex takes
        // OVERLAY -  LIGHT
        // HOWEVER:
        // The parameters given are from a call to VertexConsumer.vertex,
        // so the parameters given are
        // OVERLAY -  LIGHT
        if(isShulkerBase) {
            this.dualShulkerVertexConsumer.vertex(x, y, z, red, green, blue, alpha, u, v, overlay, light, normalX, normalY, normalZ, true);
        } else {
            instance.vertex(x, y, z, red, green, blue, alpha, u, v, overlay, light, normalX, normalY, normalZ);
        }
    }
}
