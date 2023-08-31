package net.gyllowe.dualcoloredshulkers.mixin.particles;

import net.gyllowe.dualcoloredshulkers.particles.DualShulkerParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShulkerBoxBlock.class)
public abstract class MixinShulkerBoxBlockBreakParticles
		extends BlockWithEntity {
	protected MixinShulkerBoxBlockBreakParticles(Settings settings) {super(settings);}


	@Override
	protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
		super.spawnBreakParticles(world, player, pos, state);

		BlockState blockState = DualShulkerParticles.getSecondaryColorBlockstate( ( ( ShulkerBoxBlockEntity ) world.getBlockEntity(pos) ) );
		if(blockState == null)
			return;

		world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, pos, getRawIdFromState(blockState));
	}

}
