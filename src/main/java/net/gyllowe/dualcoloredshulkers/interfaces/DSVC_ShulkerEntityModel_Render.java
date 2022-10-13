package net.gyllowe.dualcoloredshulkers.interfaces;

import net.gyllowe.dualcoloredshulkers.DualShulkerVertexConsumer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public interface DSVC_ShulkerEntityModel_Render<T extends ShulkerEntityModel> {
    void render(MatrixStack matrices,
                DualShulkerVertexConsumer dualShulkerVertexConsumer,
                int light,
                int overlay,
                float red, float green, float blue, float alpha);
}
