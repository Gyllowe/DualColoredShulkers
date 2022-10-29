package net.gyllowe.dualcoloredshulkers._wip.Rendering_SecondAttempt.mixin;

import net.gyllowe.dualcoloredshulkers.DualColoredShulkers;
import net.gyllowe.dualcoloredshulkers._wip.Rendering_SecondAttempt.interfaces.RSA_DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class RSA_MixinShulkerBoxBlockEntity
        implements RSA_DualColoredShulkerBlockEntity {
    //@Final @Mutable
    boolean hasSecondaryColor = true;//false;
    @Nullable //@Final @Mutable
    DyeColor secondaryColor = null;
    private final String HAS_SECONDARY_COLOR_KEY = "DualShulkerHasBaseColor";
    private final String SECONDARY_COLOR_KEY = "DualShulkerBaseColor";


    public boolean HasSecondaryColor() {
        return hasSecondaryColor;
    }
    @Nullable
    public DyeColor GetSecondaryColor() {
        return secondaryColor;
    }


    @Inject(
            method = "readNbt",
            at = @At("TAIL")
    )
    private void readNbtInject(NbtCompound nbt, CallbackInfo ci) {
        /*if(!nbt.contains(HAS_SECONDARY_COLOR_KEY)) {
            return;
        }
        hasSecondaryColor = nbt.getBoolean(HAS_SECONDARY_COLOR_KEY); // Should always return true, could simplify
        secondaryColor = (nbt.getInt(SECONDARY_COLOR_KEY) == -1) ? null : DyeColor.byId(nbt.getInt(SECONDARY_COLOR_KEY));
        //DyeColor temp = nbt.getInt(SECONDARY_COLOR_KEY) == -1 ? null : DyeColor.byId(nbt.getInt(SECONDARY_COLOR_KEY));
        //secondaryColor = temp == DyeColor.RED ? DyeColor.BLUE : DyeColor.RED;*/
        hasSecondaryColor = true;
        secondaryColor = DyeColor.LIGHT_GRAY;
        DualColoredShulkers.LOGGER.info("read nbt: {}, {}", hasSecondaryColor, secondaryColor);
    }
    @Inject(
            method = "writeNbt",
            at = @At("TAIL")
    )
    private void writeNbtInject(NbtCompound nbt, CallbackInfo ci) {
        /*if(!hasSecondaryColor) {
            return;
        }
        //*
        nbt.putBoolean(HAS_SECONDARY_COLOR_KEY, true);
        int i = (secondaryColor == null) ? -1 : secondaryColor.getId();
        nbt.putInt(SECONDARY_COLOR_KEY, i);
        DualColoredShulkers.LOGGER.info("write nbt: {}, {}", hasSecondaryColor, secondaryColor);
        //*/

        //nbt.putBoolean(HAS_SECONDARY_COLOR_KEY, true);
        //nbt.putInt(SECONDARY_COLOR_KEY, DyeColor.BLACK.getId() );
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
    @Nullable
    private DyeColor RandomColor() {
        Random rand = new Random();
        int randInt = rand.nextInt(17);
        if(randInt == 0) {
            return null;
        }
        return DyeColor.byId(randInt - 1);
    }
}
