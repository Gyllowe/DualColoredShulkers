package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class MixinShulkerBoxBlockEntity
		implements DualColoredShulkerBlockEntity {
	//@Final @Mutable
	boolean hasSecondaryColor = true;//false;
	@Nullable //@Final @Mutable
	DyeColor secondaryColor = DyeColor.WHITE;//null;


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
		if(!hasColor) {
			return this.RemoveSecondaryColor();
		}
		hasSecondaryColor = true;
		secondaryColor = color;
		return (ShulkerBoxBlockEntity)(Object)this;
	}
}
