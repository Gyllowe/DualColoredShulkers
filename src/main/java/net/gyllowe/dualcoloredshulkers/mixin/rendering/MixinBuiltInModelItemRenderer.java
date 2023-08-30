package net.gyllowe.dualcoloredshulkers.mixin.rendering;

import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerNbt;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BuiltinModelItemRenderer.class)
public abstract class MixinBuiltInModelItemRenderer {
	@Inject(
			method = "render",
			at = {
					@At(
							value = "FIELD",
							target = "Lnet/minecraft/client/render/item/BuiltinModelItemRenderer;RENDER_SHULKER_BOX:Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;",
							shift = At.Shift.BY,
							by = 2
					),
					@At(
							value = "FIELD",
							target = "Lnet/minecraft/client/render/item/BuiltinModelItemRenderer;RENDER_SHULKER_BOX_DYED:[Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;",
							shift = At.Shift.BY,
							by = 5
					)
			},
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void renderShulker(ItemStack stack, ModelTransformationMode unused_1, MatrixStack unused_2, VertexConsumerProvider unused_3, int unused_4, int unused_5, CallbackInfo ci, Item unused_6, Block unused_7, BlockEntity blockEntity) {
		( (DualColoredShulkerBlockEntity) blockEntity ).dualcoloredshulkers$setSecondaryColor(DualShulkerNbt.readFrom(stack));
	}

	@Inject(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderDispatcher;renderEntity(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)Z",
					shift = At.Shift.AFTER
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void resetShulkerSecondaryColor(ItemStack stack, ModelTransformationMode unused_1, MatrixStack unused_2, VertexConsumerProvider unused_3, int unused_4, int unused_5, CallbackInfo ci, Item unused_6, Block unused_7, BlockEntity blockEntity) {
		if(blockEntity instanceof ShulkerBoxBlockEntity)
			( (DualColoredShulkerBlockEntity) blockEntity ).dualcoloredshulkers$removeSecondaryColor();
	}

}
