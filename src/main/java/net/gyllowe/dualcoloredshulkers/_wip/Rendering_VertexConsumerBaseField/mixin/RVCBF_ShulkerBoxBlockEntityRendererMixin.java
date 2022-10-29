package net.gyllowe.dualcoloredshulkers._wip.Rendering_VertexConsumerBaseField.mixin;

import net.gyllowe.dualcoloredshulkers._wip.Rendering_SecondAttempt.interfaces.RSA_DualColoredShulkerBlockEntity;
import net.gyllowe.dualcoloredshulkers._wip.Rendering_VertexConsumerBaseField.RVCBF_DualShulkerVertexConsumer;
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
public abstract class RVCBF_ShulkerBoxBlockEntityRendererMixin
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

		RSA_DualColoredShulkerBlockEntity dualShulkerBE = (RSA_DualColoredShulkerBlockEntity)shulkerBoxBlockEntity;
		if(dualShulkerBE.HasSecondaryColor()) {
			hasSecondaryColor = true;
			@Nullable DyeColor secondaryColor = dualShulkerBE.GetSecondaryColor();
			secondarySpriteIdentifier = (secondaryColor == null) ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(secondaryColor.getId());
		}

		return new RVCBF_DualShulkerVertexConsumer(
				vertexConsumerProvider.getBuffer(spriteIdentifier.getRenderLayer(layerFactory)),
				spriteIdentifier.getSprite(),
				hasSecondaryColor,
				(secondarySpriteIdentifier == null) ? null : secondarySpriteIdentifier.getSprite()
		);
	}
}
