package net.gyllowe.dualcoloredshulkers.particles;

import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.gyllowe.dualcoloredshulkers.replacements.ShulkerBlockState;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerColor;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DualShulkerParticles {

	private static BlockState getRandomBlockState(BlockState blockState, World world, BlockEntity blockEntity, BlockPos pos) {
		if(!(blockState instanceof ShulkerBlockState ))
			return blockState;


		Direction facing = blockState.get(ShulkerBoxBlock.FACING);
		if(facing == Direction.UP)
			return blockState;
		else if(facing != Direction.DOWN && !world.random.nextBoolean())
			return blockState;


		if(blockEntity == null)
			blockEntity = world.getBlockEntity(pos);

		if(!(blockEntity instanceof ShulkerBoxBlockEntity ))
			return blockState;

		DualShulkerColor second = ( ( DualColoredShulkerBlockEntity ) blockEntity ).dualcoloredshulkers$getSecondaryColor();
		DyeColor first = ( ( ShulkerBoxBlockEntity ) blockEntity ).getColor();


		return ShulkerBoxBlock.get((second.isNone()) ? first : second.toDyeColor()).getDefaultState();
	}

	public static BlockState getRandomBlockState(BlockState blockState, World world, BlockEntity blockEntity) {
		return getRandomBlockState(blockState, world, blockEntity, null);
	}

	public static BlockState getRandomBlockState(BlockState blockState, World world, BlockPos pos) {
		return getRandomBlockState(blockState, world, null, pos);
	}


	@Nullable
	public static BlockState getSecondaryColorBlockstate(ShulkerBoxBlockEntity blockEntity) {
		DualShulkerColor secondaryColor = ( ( DualColoredShulkerBlockEntity ) blockEntity ).dualcoloredshulkers$getSecondaryColor();

		if(secondaryColor.isNone())
			return null;

		return ShulkerBoxBlock.get(secondaryColor.toDyeColor()).getDefaultState();
	}

}
