package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerRendering;
import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.function.Function;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public abstract class MixinShulkerBoxBlockEntityRenderer
		implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
	private ShulkerBoxBlockEntity shulkerBoxBlockEntity;


	@ModifyArg(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/entity/model/ShulkerEntityModel;<init>(Lnet/minecraft/client/model/ModelPart;)V"
			)
	)
	private ModelPart SetDualShulkerRenderingModel(ModelPart root) {
		DualShulkerRendering.SetModel(new ShulkerEntityModel<>(root));
		return root;
	}

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
		DualShulkerColor secondaryColor = DualShulkerColor.NONE;
		@Nullable SpriteIdentifier secondarySpriteIdentifier = null;

		DualColoredShulkerBlockEntity dualShulkerBE = (DualColoredShulkerBlockEntity)shulkerBoxBlockEntity;
		if(dualShulkerBE.DualColoredShulkers$getSecondaryColor().notNone()) {
			secondaryColor = dualShulkerBE.DualColoredShulkers$getSecondaryColor();
			secondarySpriteIdentifier = (secondaryColor == DualShulkerColor.BLANK) ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(secondaryColor.getId());
		}

		return new DualShulkerVertexConsumer(
				vertexConsumerProvider.getBuffer(spriteIdentifier.getRenderLayer(layerFactory)),
				spriteIdentifier.getSprite(),
				secondaryColor.notNone(),
				(secondaryColor.isNone()) ? null : secondarySpriteIdentifier.getSprite()
		);
	}
}
