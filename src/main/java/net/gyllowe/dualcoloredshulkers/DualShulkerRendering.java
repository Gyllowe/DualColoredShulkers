package net.gyllowe.dualcoloredshulkers;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public abstract class DualShulkerRendering {
	private static ShulkerEntityModel<?> model;


	public static void SetModel(ShulkerEntityModel<?> input) {
		model = input;
	}

	public static void RenderWithoutBlockEntity(@Nullable DyeColor mainColor, DualShulkerColor secondaryColor, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		Direction direction = Direction.UP;
		matrixStack.push();
		matrixStack.translate(0.5, 0.5, 0.5);
		float g = 0.9995f;
		matrixStack.scale(g, g, g);
		matrixStack.multiply(direction.getRotationQuaternion());
		matrixStack.scale(1.0f, -1.0f, -1.0f);
		matrixStack.translate(0.0, -1.0, 0.0);

		SpriteIdentifier spriteIdentifier = (mainColor == null) ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(mainColor.getId());
		SpriteIdentifier secondarySpriteIdentifier = null;
		if(secondaryColor.notNone()) {
			secondarySpriteIdentifier = (secondaryColor == DualShulkerColor.BLANK) ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(secondaryColor.getId());
		}

		DualShulkerVertexConsumer dualShulkerVertexConsumer = new DualShulkerVertexConsumer(
				vertexConsumerProvider.getBuffer(spriteIdentifier.getRenderLayer(RenderLayer::getEntityCutoutNoCull)),
				spriteIdentifier.getSprite(),
				secondaryColor.notNone(),
				(secondaryColor.isNone()) ? null : secondarySpriteIdentifier.getSprite()
		);

		model.render(matrixStack, dualShulkerVertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
		matrixStack.pop();
	}
}
