package net.gyllowe.dualcoloredshulkers.mixin.particles;

import net.gyllowe.dualcoloredshulkers.particles.DualShulkerParticles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity
		extends Entity {
	public MixinLivingEntity(EntityType<?> variant, World world) {super(variant, world);}

	@Unique
	private BlockPos landedPosition = null;



	@Inject(
			method = "fall",
			at = @At("HEAD")
	)
	private void captureBlockPos(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition, CallbackInfo ci) {
		this.landedPosition = landedPosition;
	}


	@ModifyArg(
			method = "fall",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/particle/BlockStateParticleEffect;<init>(Lnet/minecraft/particle/ParticleType;Lnet/minecraft/block/BlockState;)V"
			)
	)
	private BlockState replaceWithShulkerSecondaryColor(BlockState blockState) {
		BlockPos landedPosition = this.landedPosition;
		this.landedPosition = null;

		return DualShulkerParticles.getRandomBlockState(blockState, world, landedPosition);
	}

}
