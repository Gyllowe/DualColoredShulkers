package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerNbt;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.gyllowe.dualcoloredshulkers.replacing_mc_classes.ShulkerBlockState;
import net.minecraft.block.*;
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
	private void SetShulkerBlockState(DyeColor color, AbstractBlock.Settings settings, CallbackInfo ci) {
		StateManager.Builder<Block, BlockState> builder = new StateManager.Builder<>(this);
		this.appendProperties(builder);
		this.stateManager = builder.build(Block::getDefaultState, ShulkerBlockState::new);
		this.setDefaultState(this.stateManager.getDefaultState());
	}


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
