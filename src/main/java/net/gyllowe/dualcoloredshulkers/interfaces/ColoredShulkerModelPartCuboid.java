package net.gyllowe.dualcoloredshulkers.interfaces;

import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.minecraft.client.model.ModelPart;

public interface ColoredShulkerModelPartCuboid<T extends ModelPart.Cuboid> {
    ColoredShulkerModelPartCuboid SetShulkerBase(DualShulkerVertexConsumer dualShulkerVertexConsumer);
}
