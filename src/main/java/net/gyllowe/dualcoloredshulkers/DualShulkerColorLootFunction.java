package net.gyllowe.dualcoloredshulkers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.nbt.NbtCompound;

public class DualShulkerColorLootFunction
		extends ConditionalLootFunction {
	protected DualShulkerColorLootFunction(LootCondition[] conditions) {
		super(conditions);
	}

	@Override
	public LootFunctionType getType() {
		return DualShulkerColorFunctionType.FUNCTION_TYPE_DUAL_SHULKER_COLOR;
	}

	@Override
	protected ItemStack process(ItemStack stack, LootContext context) {
		if (stack.isEmpty()) {
			return stack;
		}
		NbtCompound itemBlockEntitySubNbt = BlockItem.getBlockEntityNbt(stack);
		if(itemBlockEntitySubNbt == null) {
			// This should never happen I think
			DualColoredShulkers.LOGGER.warn("DualShulkerColorLootFunction.process, the ItemStack had no Nbt");
			return stack;
		}
		BlockEntity blockEntity;
		if( (blockEntity = context.get(LootContextParameters.BLOCK_ENTITY)) != null) {
			DualShulkerNbt.WriteTo(itemBlockEntitySubNbt, ( (DualColoredShulkerBlockEntity) blockEntity ).DualColoredShulkers$getSecondaryColor());
		}
		return stack;
	}

	public static ConditionalLootFunction.Builder<?> builder() {
		return DualShulkerColorLootFunction.builder(DualShulkerColorLootFunction::new);
	}

	public static class Serializer
			extends ConditionalLootFunction.Serializer<DualShulkerColorLootFunction> {
		@Override
		public void toJson(JsonObject jsonObject, DualShulkerColorLootFunction dualShulkerColorLootFunction, JsonSerializationContext jsonSerializationContext) {
			super.toJson(jsonObject, dualShulkerColorLootFunction, jsonSerializationContext);
		}

		@Override
		public DualShulkerColorLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] lootConditions) {
			return new DualShulkerColorLootFunction(lootConditions);
		}
	}
}
