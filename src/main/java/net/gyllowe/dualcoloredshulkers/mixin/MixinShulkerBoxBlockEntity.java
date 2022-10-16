package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class MixinShulkerBoxBlockEntity
        implements DualColoredShulkerBlockEntity {
    @Shadow @Final
    private DyeColor cachedColor;

    @Final @Mutable
    boolean hasSecondaryColor;
    @Nullable @Final @Mutable
    DyeColor secondaryColor;


    public boolean HasSecondaryColor() {
        return hasSecondaryColor;
    }
    @Nullable
    public DyeColor GetSecondaryColor() {
        return secondaryColor;
    }


    @Inject(
            method = "<init>(Lnet/minecraft/util/DyeColor;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            at = @At("TAIL")
    )
    private void inject(DyeColor color, BlockPos pos, BlockState state, CallbackInfo ci) {
        ConstructorInject();
    }
    @Inject(
            method = "<init>(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            at = @At("TAIL")
    )
    private void inject(BlockPos pos, BlockState state, CallbackInfo ci) {
        ConstructorInject();
    }
    private void ConstructorInject() {
        Random rand = new Random();
        if(!rand.nextBoolean()) {
            hasSecondaryColor = false;
            secondaryColor = null;
        } else {
            hasSecondaryColor = true;

            secondaryColor = RandomColor();
            while(secondaryColor == cachedColor) {
                secondaryColor = RandomColor();
            }
        }
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
