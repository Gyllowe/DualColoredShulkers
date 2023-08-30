package net.gyllowe.dualcoloredshulkers.mixin.main;

import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerColor;
import net.gyllowe.dualcoloredshulkers.util.DualShulkerNbt;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
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
	@Shadow @Final @Nullable private DyeColor cachedColor;


	@Unique
	private DualShulkerColor secondaryColor = DualShulkerColor.NONE;


	@Inject(
			method = "readNbt",
			at = @At("TAIL")
	)
	private void readNbtInject(NbtCompound nbt, CallbackInfo ci) {
		secondaryColor = DualShulkerNbt.readFrom(nbt);
	}

	@Inject(
			method = "writeNbt",
			at = @At("TAIL")
	)
	private void writeNbtInject(NbtCompound nbt, CallbackInfo ci) {
		DualShulkerNbt.writeTo(nbt, secondaryColor);
	}


	@Override
	public void writeNbtToStack(ItemStack stack) {
		super.writeNbtToStack(stack);
		if(this.secondaryColor.notNone()) {
			DualShulkerNbt.removeWithinBlockEntityNbt(stack);
			DualShulkerNbt.setNbt(stack, this.secondaryColor);
		}
	}


	@Override
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}
	@Override
	public NbtCompound toSyncedNbt() {
		//return this.toNbt();
		NbtCompound nbtCompound = new NbtCompound();
		DualShulkerNbt.writeTo(nbtCompound, secondaryColor);
		return nbtCompound;
	}


	@Unique
	public DualShulkerColor dualcoloredshulkers$getSecondaryColor() {
		return secondaryColor;
	}
	@Unique
	public void dualcoloredshulkers$setSecondaryColor(DualShulkerColor dualShulkerColor) {
		if(dualShulkerColor.toDyeColor() == cachedColor) {
			this.secondaryColor = DualShulkerColor.NONE;
			return;
		}
		secondaryColor = dualShulkerColor;
	}
	@Unique
	public void dualcoloredshulkers$removeSecondaryColor() {
		secondaryColor = DualShulkerColor.NONE;
	}

}
