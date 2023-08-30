package net.gyllowe.dualcoloredshulkers.mixin.main;

import net.gyllowe.dualcoloredshulkers.util.DualShulkerNbt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface MixinCauldronBehavior {
	@Shadow @Final CauldronBehavior CLEAN_SHULKER_BOX = null;
	@Shadow @Final Map<Item, CauldronBehavior> WATER_CAULDRON_BEHAVIOR = null;


	@Inject(
			method = "registerBehavior",
			at = @At(
					value = "FIELD",
					opcode = Opcodes.GETSTATIC,
					target = "Lnet/minecraft/item/Items;WHITE_SHULKER_BOX:Lnet/minecraft/item/Item;",
					shift = At.Shift.BEFORE
			)
	)
	private static void cleanableBlankShulkers(CallbackInfo ci) {
		WATER_CAULDRON_BEHAVIOR.put(Items.SHULKER_BOX, CLEAN_SHULKER_BOX);
	}

	@Inject(
			method = "method_32215",
			at = @At(
					value = "JUMP",
					opcode = Opcodes.IFNE,
					ordinal = 0
			),
			cancellable = true,
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private static void returnIfShulkerClean(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, CallbackInfoReturnable<ActionResult> cir, Block block) {
		if(block == Blocks.SHULKER_BOX && !DualShulkerNbt.contains(stack))
			cir.setReturnValue(ActionResult.PASS);
	}

	@Inject(
			method = "method_32215",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;setNbt(Lnet/minecraft/nbt/NbtCompound;)V",
					shift = At.Shift.AFTER
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private static void removeSecondaryColor(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, CallbackInfoReturnable<ActionResult> cir, Block block, ItemStack itemStack) {
		NbtCompound nbt = itemStack.getNbt();
		DualShulkerNbt.removeFrom(nbt);
		if(nbt.isEmpty())
			itemStack.setNbt(null);
	}

}
