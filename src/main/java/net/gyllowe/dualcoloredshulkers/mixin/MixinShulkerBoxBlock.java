package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerNbt;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlock.class)
public abstract class MixinShulkerBoxBlock {
	@Inject(
			method = "onPlaced",
			at = @At("TAIL")
	)
	private void SetSecondaryColorFromItemStack(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		DualShulkerColor itemStackSecondaryColor = DualShulkerNbt.ReadFrom(itemStack);
		if(itemStackSecondaryColor.notNone())
			( (DualColoredShulkerBlockEntity) blockEntity ).dualcoloredshulkers$setSecondaryColor(itemStackSecondaryColor);
	}
}
