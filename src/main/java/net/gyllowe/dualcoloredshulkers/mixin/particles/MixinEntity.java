package net.gyllowe.dualcoloredshulkers.mixin.particles;

import net.gyllowe.dualcoloredshulkers.particles.DualShulkerParticles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class MixinEntity {
	@Redirect(
			method = "spawnSprintingParticles",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"
			)
	)
	private BlockState replaceWithShulkerSecondaryColor(World world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);

		return DualShulkerParticles.getRandomBlockState(blockState, world, pos);
	}

}
