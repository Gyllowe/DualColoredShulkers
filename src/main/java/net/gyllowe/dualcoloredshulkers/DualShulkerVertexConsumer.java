package net.gyllowe.dualcoloredshulkers;

import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;

public class DualShulkerVertexConsumer
		extends SpriteTexturedVertexConsumer
		implements VertexConsumer {
	private final VertexConsumer parent;
	private final Sprite sprite;
	private final boolean hasSecondaryColor;
	private final Sprite secondSprite;
	private boolean useSecondarySprite = false;


	public DualShulkerVertexConsumer(VertexConsumer parent, Sprite sprite, Boolean hasSecondaryColor, Sprite secondSprite) {
		super(parent, sprite);
		this.parent = parent;
		this.sprite = sprite;
		this.hasSecondaryColor = hasSecondaryColor;
		this.secondSprite = secondSprite;
	}

	public void RenderingShulkerBase(boolean input) {
		useSecondarySprite = (input && hasSecondaryColor);
	}

	private Sprite UsingSprite() {
		return (useSecondarySprite) ? secondSprite : sprite;
	}

	@Override
	public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ) {
		this.parent.vertex(x, y, z, red, green, blue, alpha, this.UsingSprite().getFrameU(u * 16.0f), this.UsingSprite().getFrameV(v * 16.0f), overlay, light, normalX, normalY, normalZ);
	}
}
