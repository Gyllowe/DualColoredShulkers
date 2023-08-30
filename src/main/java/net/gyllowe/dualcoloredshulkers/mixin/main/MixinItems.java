package net.gyllowe.dualcoloredshulkers.mixin.main;

import net.gyllowe.dualcoloredshulkers.replacements.ShulkerBoxItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class MixinItems {
	@Redirect(
			method = "<clinit>",
			slice = @Slice(
					from = @At(
							value = "FIELD",
							opcode = Opcodes.GETSTATIC,
							target = "Lnet/minecraft/block/Blocks;SHULKER_BOX:Lnet/minecraft/block/Block;",
							shift = At.Shift.BY,
							by = -2
					),
					to = @At(
							value = "FIELD",
							opcode = Opcodes.PUTSTATIC,
							target = "Lnet/minecraft/item/Items;BLACK_SHULKER_BOX:Lnet/minecraft/item/Item;"
					)
			),
			at = @At(
					value = "NEW",
					args = "class=net/minecraft/item/BlockItem"
			)
	)
	private static BlockItem replaceBlockItemWithShulkerBoxItem(Block block, Item.Settings settings) {
		return new ShulkerBoxItem(block, settings);
	}

}
