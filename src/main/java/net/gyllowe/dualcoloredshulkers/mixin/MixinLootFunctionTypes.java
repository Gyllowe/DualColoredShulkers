package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.DualShulkerColorLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.util.JsonSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootFunctionTypes.class)
public abstract class MixinLootFunctionTypes {
	@Shadow private static LootFunctionType register(String id, JsonSerializer<? extends LootFunction> jsonSerializer) {return null;}

	@Inject(
			method = "<clinit>",
			at = @At("HEAD")
	)
	private static void inject(CallbackInfo ci) {
		DualShulkerColorLootFunction.setDualShulkerColorFunctionType(register("dual_shulker_color", new DualShulkerColorLootFunction.Serializer()));
	}
}
