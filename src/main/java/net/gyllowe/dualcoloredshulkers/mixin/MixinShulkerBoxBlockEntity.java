package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.DualShulkerNbt;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class MixinShulkerBoxBlockEntity
		extends LootableContainerBlockEntity
		implements DualColoredShulkerBlockEntity {
	protected MixinShulkerBoxBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {super(blockEntityType, blockPos, blockState);}
	@Shadow @Final @org.jetbrains.annotations.Nullable
	private DyeColor cachedColor;

	@Unique
	private DualShulkerColor secondaryColor = DualShulkerColor.NONE;


	@Inject(
			method = "readNbt",
			at = @At("TAIL")
	)
	private void readNbtInject(NbtCompound nbt, CallbackInfo ci) {
		secondaryColor = DualShulkerNbt.ReadFrom(nbt);
	}

	@Inject(
			method = "writeNbt",
			at = @At("TAIL")
	)
	private void writeNbtInject(NbtCompound nbt, CallbackInfo ci) {
		DualShulkerNbt.WriteTo(nbt, secondaryColor);
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	@Override
	public NbtCompound toInitialChunkDataNbt() {
		//return this.createNbt();
		NbtCompound nbtCompound = new NbtCompound();
		DualShulkerNbt.WriteTo(nbtCompound, secondaryColor);
		return nbtCompound;
	}


	@Unique
	public DualShulkerColor DualColoredShulkers$getSecondaryColor() {
		return secondaryColor;
	}
	@Unique
	public void DualColoredShulkers$setSecondaryColor(DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.ToDyeColor() == cachedColor) {
			this.secondaryColor = DualShulkerColor.NONE;
			return;
		}
		secondaryColor = dualShulkerColor;
	}
}
