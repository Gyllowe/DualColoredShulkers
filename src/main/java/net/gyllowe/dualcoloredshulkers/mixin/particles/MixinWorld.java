package net.gyllowe.dualcoloredshulkers.mixin.particles;

import net.gyllowe.dualcoloredshulkers.replacements.ShulkerBlockState;
import net.gyllowe.dualcoloredshulkers.particles.DualShulkerParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(World.class)
public abstract class MixinWorld {
	@Inject(
			method = "breakBlock",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;syncWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V",
					shift = At.Shift.AFTER
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void shulkerSecondaryColorParticles(BlockPos pos, boolean drop, Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir, BlockState blockState) {
		if(!(blockState instanceof ShulkerBlockState ))
			return;

		BlockState secondaryColorBlockstate = DualShulkerParticles.getSecondaryColorBlockstate( ( ( ShulkerBoxBlockEntity ) ( ( World ) ( Object ) this ).getBlockEntity(pos) ) );
		if(secondaryColorBlockstate == null)
			return;

		( ( World ) ( Object ) this ).syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(secondaryColorBlockstate));
	}

}
