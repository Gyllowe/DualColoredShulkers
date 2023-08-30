package net.gyllowe.dualcoloredshulkers.mixin.main;

import net.gyllowe.dualcoloredshulkers.util.DualShulkerCrafting;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShulkerBoxColoringRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerBoxColoringRecipe.class)
public abstract class MixinShulkerBoxColoringRecipe {
	@Inject(
			method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z",
			at = @At("HEAD"),
			cancellable = true
	)
	private void customMatching(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir) {
		if(craftingInventory.size() == 9)
			cir.setReturnValue(DualShulkerCrafting.matches3x3(craftingInventory));
		else if(craftingInventory.size() == 4)
			cir.setReturnValue(DualShulkerCrafting.matches2x2(craftingInventory));
	}

	@Inject(
			method = "craft(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;",
			at = @At("HEAD"),
			cancellable = true
	)
	private void customCrafting(CraftingInventory craftingInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir) {
		if(craftingInventory.size() == 9)
			cir.setReturnValue(DualShulkerCrafting.craft3x3(craftingInventory));
		else if(craftingInventory.size() == 4)
			cir.setReturnValue(DualShulkerCrafting.craft2x2(craftingInventory));
	}

}
