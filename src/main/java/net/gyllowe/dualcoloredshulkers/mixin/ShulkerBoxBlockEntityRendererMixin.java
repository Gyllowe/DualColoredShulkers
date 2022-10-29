package net.gyllowe.dualcoloredshulkers.mixin;

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
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.function.Function;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public abstract class ShulkerBoxBlockEntityRendererMixin
		implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
	private ShulkerBoxBlockEntity shulkerBoxBlockEntity;


	@ModifyVariable(
			method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
			at = @At("HEAD"),
			argsOnly = true
	)
	private ShulkerBoxBlockEntity BlockEntityCapture(ShulkerBoxBlockEntity value) {
		return this.shulkerBoxBlockEntity = value;
	}


	@Redirect(
			method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"
			)
	)
	private VertexConsumer ReplaceWithDualShulkerVC(SpriteIdentifier spriteIdentifier, VertexConsumerProvider vertexConsumerProvider, Function<Identifier, RenderLayer> layerFactory) {
		boolean hasSecondaryColor = false;
		@Nullable SpriteIdentifier secondarySpriteIdentifier = null;

		DualColoredShulkerBlockEntity dualShulkerBE = (DualColoredShulkerBlockEntity)shulkerBoxBlockEntity;
		if(dualShulkerBE.HasSecondaryColor()) {
			hasSecondaryColor = true;
			@Nullable DyeColor secondaryColor = dualShulkerBE.GetSecondaryColor();
			secondarySpriteIdentifier = (secondaryColor == null) ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(secondaryColor.getId());
		}

		return new DualShulkerVertexConsumer(
				vertexConsumerProvider.getBuffer(spriteIdentifier.getRenderLayer(layerFactory)),
				spriteIdentifier.getSprite(),
				hasSecondaryColor,
				(secondarySpriteIdentifier == null) ? null : secondarySpriteIdentifier.getSprite()
		);
	}
}
