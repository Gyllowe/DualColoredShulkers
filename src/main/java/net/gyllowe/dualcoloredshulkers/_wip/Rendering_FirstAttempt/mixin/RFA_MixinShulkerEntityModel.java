package net.gyllowe.dualcoloredshulkers._wip.Rendering_FirstAttempt.mixin;

import net.gyllowe.dualcoloredshulkers._wip.Rendering_FirstAttempt.interfaces.ModelPartSetShulkerBase;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerEntityModel.class)
public abstract class RFA_MixinShulkerEntityModel<T extends ShulkerEntity>
        extends CompositeEntityModel<T> {
    @Shadow @Final
    private ModelPart base;


    @Inject(
            method = "<init>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/model/ShulkerEntityModel;base:Lnet/minecraft/client/model/ModelPart;",
                    shift = At.Shift.AFTER
            )
    )
    private void ModelBaseSetShulkerBaseInject(ModelPart root, CallbackInfo ci) {
        ( (ModelPartSetShulkerBase)(Object) this.base ).SetShulkerBase();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        //DualColoredShulkers.LOGGER.info(String.valueOf(((ModelPartSetShulkerBase)(Object)this.base).IsBase()));
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
