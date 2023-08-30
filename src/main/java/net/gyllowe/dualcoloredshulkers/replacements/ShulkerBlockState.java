package net.gyllowe.dualcoloredshulkers.replacements;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class ShulkerBlockState extends BlockState {
	public ShulkerBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<BlockState> mapCodec) {
		super(block, immutableMap, mapCodec);
	}

	@Override
	public MapColor getMapColor(BlockView world, BlockPos pos) {
		if(this.get(ShulkerBoxBlock.FACING) != Direction.DOWN)
			return super.getMapColor(world, pos);

		if(!(world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity))
			return super.getMapColor(world, pos);

		DualShulkerColor secondaryColor = ( (DualColoredShulkerBlockEntity) shulkerBoxBlockEntity ).dualcoloredshulkers$getSecondaryColor();

		if(secondaryColor.isNone())
			return super.getMapColor(world, pos);

		return ShulkerBoxBlock.get(secondaryColor.toDyeColor()).getDefaultMapColor();
	}

}
