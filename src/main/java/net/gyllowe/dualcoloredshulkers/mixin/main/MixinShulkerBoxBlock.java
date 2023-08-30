package net.gyllowe.dualcoloredshulkers.mixin.main;

import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.gyllowe.dualcoloredshulkers.replacements.ShulkerBlockState;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerNbt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlock.class)
public abstract class MixinShulkerBoxBlock
		extends BlockWithEntity {
	protected MixinShulkerBoxBlock(Settings settings) {super(settings);}



	@Inject(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/BlockWithEntity;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V",
					shift = At.Shift.AFTER
			)
	)
	private void setShulkerBlockState(DyeColor color, Settings settings, CallbackInfo ci) {
		StateManager.Builder<Block, BlockState> builder = new StateManager.Builder<>(this);
		this.appendProperties(builder);
		this.stateManager = builder.build(Block::getDefaultState, ShulkerBlockState::new);
	}


	@Inject(
			method = "onPlaced",
			at = @At("TAIL")
	)
	private void setSecondaryColorFromItemStack(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		DualShulkerColor itemStackSecondaryColor = DualShulkerNbt.readFrom(itemStack);
		if(itemStackSecondaryColor.notNone())
			( (DualColoredShulkerBlockEntity) blockEntity ).dualcoloredshulkers$setSecondaryColor(itemStackSecondaryColor);
	}

}
