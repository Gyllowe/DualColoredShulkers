package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerNbt;
import net.gyllowe.dualcoloredshulkers.DualShulkerRendering;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BuiltinModelItemRenderer.class)
public abstract class MixinBuiltInModelItemRenderer {
	private static final String BLOCK_ENTITY_TAG_KEY = "BlockEntityTag";


	@Inject(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/ShulkerBoxBlock;getColor(Lnet/minecraft/item/Item;)Lnet/minecraft/util/DyeColor;",
					shift = At.Shift.BEFORE
			),
			cancellable = true,
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void RenderShulker(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci, Item item) {
		NbtCompound nbt = stack.getNbt();
		if(nbt == null) {
			return;
		}
		if(!nbt.contains(BLOCK_ENTITY_TAG_KEY)) {
			return;
		}
		NbtElement blockEntityNbt = nbt.get(BLOCK_ENTITY_TAG_KEY);
		if(!(blockEntityNbt instanceof NbtCompound blockEntityNbtCompound)) {
			return;
		}
		if(!DualShulkerNbt.Contains(blockEntityNbtCompound)) {
			return;
		}
		ci.cancel();

		DyeColor primaryColor = ShulkerBoxBlock.getColor(item);
		DualShulkerColor secondaryColor = DualShulkerNbt.ReadFrom(blockEntityNbtCompound);

		DualShulkerRendering.RenderWithoutBlockEntity(primaryColor, secondaryColor, matrices, vertexConsumers, light, overlay);
	}
}
