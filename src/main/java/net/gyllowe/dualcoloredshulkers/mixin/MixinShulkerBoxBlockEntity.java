package net.gyllowe.dualcoloredshulkers.mixin;

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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class MixinShulkerBoxBlockEntity extends LootableContainerBlockEntity
		implements DualColoredShulkerBlockEntity {
	@Shadow @Final @org.jetbrains.annotations.Nullable
	private DyeColor cachedColor;

	protected MixinShulkerBoxBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	//@Final @Mutable
	private boolean hasSecondaryColor = false;
	@org.jetbrains.annotations.Nullable //@Final @Mutable
	private DyeColor secondaryColor = null;

	private final String HAS_SECONDARY_COLOR_KEY = "DualShulkerHasBaseColor";
	private final String SECONDARY_COLOR_KEY = "DualShulkerBaseColor";


	@Inject(
			method = "readNbt",
			at = @At("TAIL")
	)
	private void readNbtInject(NbtCompound nbt, CallbackInfo ci) {
		if(!nbt.contains(HAS_SECONDARY_COLOR_KEY)) {
			return;
		}
		hasSecondaryColor = nbt.getBoolean(HAS_SECONDARY_COLOR_KEY); // Should always return true, could simplify
		secondaryColor = (nbt.getInt(SECONDARY_COLOR_KEY) == -1) ? null : DyeColor.byId(nbt.getInt(SECONDARY_COLOR_KEY));
	}
	@Inject(
			method = "writeNbt",
			at = @At("TAIL")
	)
	private void writeNbtInject(NbtCompound nbt, CallbackInfo ci) {
		if(!hasSecondaryColor) {
			return;
		}
		nbt.putBoolean(HAS_SECONDARY_COLOR_KEY, true);
		short i = (secondaryColor == null) ? -1 : (short) secondaryColor.getId();
		nbt.putShort(SECONDARY_COLOR_KEY, i);
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return this.createNbt();
	}

	public boolean HasSecondaryColor() {
		return hasSecondaryColor;
	}
	@Nullable
	public DyeColor GetSecondaryColor() {
		return secondaryColor;
	}

	public ShulkerBoxBlockEntity RemoveSecondaryColor() {
		hasSecondaryColor = false;
		secondaryColor = null;
		return (ShulkerBoxBlockEntity)(Object)this;
	}
	public ShulkerBoxBlockEntity SetSecondaryColor(boolean hasColor, @Nullable DyeColor color) {
		if(!hasColor || color == cachedColor) {
			return this.RemoveSecondaryColor();
		}
		hasSecondaryColor = true;
		secondaryColor = color;
		return (ShulkerBoxBlockEntity)(Object)this;
	}
}
