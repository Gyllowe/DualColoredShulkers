package net.gyllowe.dualcoloredshulkers;

import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;

public class DualShulkerVertexConsumer
        extends SpriteTexturedVertexConsumer
        implements VertexConsumer {
    private final VertexConsumer parent;
    private final Sprite sprite;
    private final Boolean hasSecondaryColor;
    private final Sprite secondSprite;

    public DualShulkerVertexConsumer(VertexConsumer parent, Sprite sprite, Boolean hasSecondaryColor, Sprite secondSprite) {
        super(parent, sprite);
        this.parent = parent;
        this.sprite = sprite;
        this.hasSecondaryColor = hasSecondaryColor;
        this.secondSprite = secondSprite;
    }

    @Override
    public void vertex(float x, float y, float z,
                       float red, float green, float blue, float alpha,
                       float u, float v,
                       int overlay,
                       int light,
                       float normalX, float normalY, float normalZ) {
        vertex(x, y, z, red, green, blue, alpha, u, v, overlay, light, normalX, normalY, normalZ, false);
    }

    public void vertex(float x, float y, float z,
                       float red, float green, float blue, float alpha,
                       float u, float v,
                       int overlay,
                       int light,
                       float normalX, float normalY, float normalZ,
                       Boolean isShulkerBase) {
        Sprite usingSprite;
        if(isShulkerBase && hasSecondaryColor) {
            usingSprite = this.secondSprite;
        } else {
            usingSprite = this.sprite;
        }
        this.parent.vertex(x, y, z, red, green, blue, alpha, usingSprite.getFrameU(u * 16.0f), usingSprite.getFrameV(v * 16.0f), overlay, light, normalX, normalY, normalZ);
    }
}
