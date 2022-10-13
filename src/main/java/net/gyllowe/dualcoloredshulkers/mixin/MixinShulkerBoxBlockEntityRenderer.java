package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.gyllowe.dualcoloredshulkers.interfaces.DSVC_ShulkerEntityModel_Render;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public abstract class MixinShulkerBoxBlockEntityRenderer {
    private ShulkerBoxBlockEntity shulkerBoxBlockEntity;
    private VertexConsumerProvider vertexConsumerProvider;
    private SpriteIdentifier spriteIdentifier;


    // Setting shulkerBoxBlockEntity, vertexConsumerProvider and spriteIdentifier
    // to their values in the method, so they can be used later.
    @Inject(
            method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At("HEAD"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void injected(ShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        this.shulkerBoxBlockEntity = shulkerBoxBlockEntity;
        this.vertexConsumerProvider = vertexConsumerProvider;
    }
    @ModifyVariable(
            method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private SpriteIdentifier injected(SpriteIdentifier value) {
        this.spriteIdentifier = value;
        return value;
    }

    @Redirect(
            method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/model/ShulkerEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"
            )
    )
    private void inject(ShulkerEntityModel instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, float r, float g, float b, float a) {
        DualColoredShulkerBlockEntity dualShulkerBE = (DualColoredShulkerBlockEntity)shulkerBoxBlockEntity;
        if(!dualShulkerBE.hasSecondaryColor) {
            instance.render(matrixStack, vertexConsumer, i, j, r, g, b, a);
            return;
        }
        DyeColor secondaryColor = dualShulkerBE.secondaryColor;
        SpriteIdentifier secondarySprite = secondaryColor == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(secondaryColor.getId());

        DualShulkerVertexConsumer dualShulkerVertexConsumer =
                new DualShulkerVertexConsumer(
                        vertexConsumerProvider.getBuffer(this.spriteIdentifier.getRenderLayer(RenderLayer::getEntityCutoutNoCull)),
                        this.spriteIdentifier.getSprite(),
                        dualShulkerBE.hasSecondaryColor,
                        secondarySprite.getSprite()
                );

        ((DSVC_ShulkerEntityModel_Render)instance).render(matrixStack, dualShulkerVertexConsumer, i, j, r, g, b, a);
    }


    /*
    new ShulkerBaseVertexConsumer(
            vertexConsumerProvider.getBuffer(spriteIdentifier.getRenderLayer(RenderLayer::getEntityCutoutNoCull))
     */
}
