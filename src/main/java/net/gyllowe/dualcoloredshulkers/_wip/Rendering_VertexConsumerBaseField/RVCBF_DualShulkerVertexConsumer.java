package net.gyllowe.dualcoloredshulkers._wip.Rendering_VertexConsumerBaseField;

import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;

public class RVCBF_DualShulkerVertexConsumer
        extends SpriteTexturedVertexConsumer
        implements VertexConsumer {
    private final VertexConsumer parent;
    private final Sprite sprite;
    private final boolean hasSecondaryColor;
    private final Sprite secondSprite;
    public boolean renderingShulkerBase = false;


    public RVCBF_DualShulkerVertexConsumer(VertexConsumer parent, Sprite sprite, Boolean hasSecondaryColor, Sprite secondSprite) {
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
        Sprite usingSprite;
        if(renderingShulkerBase && hasSecondaryColor) {
            usingSprite = this.secondSprite;
        } else {
            usingSprite = this.sprite;
        }
        this.parent.vertex(x, y, z, red, green, blue, alpha, usingSprite.getFrameU(u * 16.0f), usingSprite.getFrameV(v * 16.0f), overlay, light, normalX, normalY, normalZ);
    }
}
