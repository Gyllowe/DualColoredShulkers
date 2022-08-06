package net.gyllowe.dualcoloredshulkers.mixin;

import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import org.apache.commons.compress.harmony.pack200.NewAttributeBands;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public abstract class MixinShulkerBoxBlockEntityRenderer {
    @Shadow
    private ShulkerEntityModel<?> model;

    @Inject(method = "render", at = @At(value = "FIELD", shift = At.Shift.AFTER,
            target = "net/minecraft/client/model/ModelPart.yaw : F",
            opcode = Opcodes.PUTFIELD), cancellable = true)
    private void onRender(SpriteIdentifier spriteIdentifier, ShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        //SpriteIdentifier bottomTexture = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(DyeColor.RED.getId());

        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutoutNoCull);
        this.model.render(matrixStack, vertexConsumer, i, j, 0.0f, 0.0f, 1.0f, 0.5f);
        matrixStack.pop();

        ci.cancel();
    }
}
