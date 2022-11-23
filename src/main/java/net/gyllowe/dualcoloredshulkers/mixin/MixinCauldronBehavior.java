package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerNbt;
import net.minecraft.block.Block;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CauldronBehavior.class)
public interface MixinCauldronBehavior {
	@Inject(
			method = "method_32215",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;setNbt(Lnet/minecraft/nbt/NbtCompound;)V",
					shift = At.Shift.AFTER
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private static void RemoveSecondaryColor3(CallbackInfoReturnable<ActionResult> cir, Block block, ItemStack itemStack) {
		NbtCompound stackBlockEntitySubNbt = BlockItem.getBlockEntityNbt(itemStack);
		if(stackBlockEntitySubNbt == null) {
			return;
		}
		DualShulkerNbt.RemoveNbt(stackBlockEntitySubNbt);
		BlockItem.setBlockEntityNbt(itemStack, BlockEntityType.SHULKER_BOX, stackBlockEntitySubNbt);
	}
}
