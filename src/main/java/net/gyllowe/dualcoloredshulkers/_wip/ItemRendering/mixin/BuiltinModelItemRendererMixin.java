package net.gyllowe.dualcoloredshulkers._wip.ItemRendering.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BuiltinModelItemRenderer.class)
public abstract class BuiltinModelItemRendererMixin {
	@ModifyVariable(
			method = "render",
			at = @At("STORE")
	)
	private DyeColor BlockEntityShulkerBaseColor(DyeColor value) {
		return null;
	}

	@Inject(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/ShulkerBoxBlock;getColor(Lnet/minecraft/item/Item;)Lnet/minecraft/util/DyeColor;",
					shift = At.Shift.BY,
					by = 2
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void BlockEntityShulkerBaseColor__(BlockEntity blockEntity) {

	}
}
