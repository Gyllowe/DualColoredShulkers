package net.gyllowe.dualcoloredshulkers;

import net.gyllowe.dualcoloredshulkers.interfaces.DualSpriteVertexConsumer;
import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;

public class DualSpriteTexturedVertexConsumer
		extends SpriteTexturedVertexConsumer
		implements DualSpriteVertexConsumer {
	private final Sprite firstSprite;
	private final boolean hasTwoSprites;
	private final Sprite secondSprite;


	/**
	 * Pass null as secondSprite to have only 1 sprite
	 */
	public DualSpriteTexturedVertexConsumer(VertexConsumer delegate, Sprite sprite, Sprite secondSprite) {
		super(delegate, sprite);
		this.firstSprite = sprite;
		this.hasTwoSprites = (secondSprite != null);
		this.secondSprite = secondSprite;
	}

	public DualSpriteTexturedVertexConsumer(VertexConsumer delegate, Sprite sprite, Boolean hasTwoSprites, Sprite secondSprite) {
		this(delegate, sprite, (hasTwoSprites) ? secondSprite : null);
	}


	public void UseSecondarySprite(boolean input) {
		sprite = (input && hasTwoSprites) ? secondSprite : firstSprite;
	}
}
