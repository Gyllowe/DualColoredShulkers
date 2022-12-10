package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public abstract class MixinShulkerBoxBlockEntityRenderer
		implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
	private ShulkerBoxBlockEntity shulkerBoxBlockEntity;


	@Inject(
			method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
			at = @At("HEAD")
	)
	private void BlockEntityCapture(ShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
		this.shulkerBoxBlockEntity = shulkerBoxBlockEntity;
	}

	@Redirect(
			method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"
			)
	)
	private VertexConsumer ReplaceWithDualShulkerVC(SpriteIdentifier spriteIdentifier, VertexConsumerProvider vertexConsumerProvider, Function<Identifier, RenderLayer> layerFactory) {
		@Nullable SpriteIdentifier secondarySpriteIdentifier = null;

		DualShulkerColor secondaryColor = ( (DualColoredShulkerBlockEntity) shulkerBoxBlockEntity ).dualcoloredshulkers$getSecondaryColor();
		if(secondaryColor.notNone())
			secondarySpriteIdentifier = (secondaryColor == DualShulkerColor.BLANK) ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(secondaryColor.getId());

		return new DualShulkerVertexConsumer(
				vertexConsumerProvider.getBuffer(spriteIdentifier.getRenderLayer(layerFactory)),
				spriteIdentifier.getSprite(),
				secondaryColor.notNone(),
				(secondaryColor.isNone()) ? null : secondarySpriteIdentifier.getSprite()
		);
	}
}
