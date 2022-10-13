package net.gyllowe.dualcoloredshulkers.interfaces;

import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.minecraft.client.model.ModelPart;

public interface ColoredShulkerModelPart<T extends ModelPart> {
    ColoredShulkerModelPart SetShulkerBase(DualShulkerVertexConsumer dualShulkerVertexConsumer);
}
