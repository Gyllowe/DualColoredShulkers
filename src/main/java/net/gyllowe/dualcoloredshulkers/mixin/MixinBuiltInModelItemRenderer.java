package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerNbt;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
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
	private void RenderShulker(ItemStack stack, ModelTransformation.Mode var2, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci, Item item, Block block, BlockEntity blockEntity) {
		DualShulkerColor secondaryColor = DualShulkerNbt.ReadFrom(stack);
		( (DualColoredShulkerBlockEntity) blockEntity ).dualcoloredshulkers$setSecondaryColor(secondaryColor);
	}
}
