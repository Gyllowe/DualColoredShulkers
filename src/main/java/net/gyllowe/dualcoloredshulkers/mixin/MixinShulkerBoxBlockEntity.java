package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class MixinShulkerBoxBlockEntity
		implements DualColoredShulkerBlockEntity {
	@Shadow @Final @org.jetbrains.annotations.Nullable
	private DyeColor cachedColor;
	//@Final @Mutable
	private boolean hasSecondaryColor = false;
	@org.jetbrains.annotations.Nullable //@Final @Mutable
	private DyeColor secondaryColor = null;


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
