package net.gyllowe.dualcoloredshulkers.mixin.particles;

import net.gyllowe.dualcoloredshulkers.replacements.ShulkerBlockState;
import net.gyllowe.dualcoloredshulkers.particles.DualShulkerParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

@Mixin(PistonBlock.class)
public abstract class MixinPistonBlock {
	@Inject(
			method = "move",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;addBlockBreakParticles(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
					shift = At.Shift.AFTER
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void addSecondaryColorParticles(
			World world,

			BlockPos unused_0,
			Direction unused_1,
			boolean unused_2,

			CallbackInfoReturnable<Boolean> cir,

			BlockPos unused_3,
			PistonHandler unused_4,
			Map unused_5,
			List unused_6,
			List unused_7,
			List unused_8,
			BlockState[] unused_9,
			Direction unused_10,
			int unused_11,
			int unused_12,

			BlockPos blockPos,
			BlockState blockState,
			BlockEntity blockEntity
	) {
		if(!(blockState instanceof ShulkerBlockState) || !(blockEntity instanceof ShulkerBoxBlockEntity shulkerBlockEntity))
			return;

		BlockState secondaryColorBlockstate = DualShulkerParticles.getSecondaryColorBlockstate(shulkerBlockEntity);
		if(secondaryColorBlockstate == null)
			return;

		world.addBlockBreakParticles(blockPos, secondaryColorBlockstate);
	}

}
