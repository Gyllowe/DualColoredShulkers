package net.gyllowe.dualcoloredshulkers._wip.Rendering_VertexConsumerBaseField.mixin;

import net.gyllowe.dualcoloredshulkers._wip.Rendering_VertexConsumerBaseField.RVCBF_DualShulkerVertexConsumer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShulkerEntityModel.class)
public abstract class RVCBF_ShulkerEntityModelMixin<T extends ShulkerEntity>
		extends CompositeEntityModel<T> {
	@Shadow @Final
	private ModelPart lid, base;

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.lid.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		( (RVCBF_DualShulkerVertexConsumer) vertices ).renderingShulkerBase = true;
		this.base.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		( (RVCBF_DualShulkerVertexConsumer) vertices ).renderingShulkerBase = false;
	}
}
