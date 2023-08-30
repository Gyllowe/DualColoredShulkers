package net.gyllowe.dualcoloredshulkers.replacements;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gyllowe.dualcoloredshulkers.interfaces.DualSpriteVertexConsumer;
import net.minecraft.client.render.SpriteTexturedVertexConsumer;
import net.minecraft.client.texture.Sprite;
import org.jetbrains.annotations.Nullable;

public class DualSpriteTexturedVertexConsumer
		extends SpriteTexturedVertexConsumer
		implements DualSpriteVertexConsumer {
	private final Sprite firstSprite;
	private final boolean hasTwoSprites;
	@Nullable
	private final Sprite secondSprite;


	/**
	 * Pass null into secondSprite to only use 1 sprite
	 */
	public DualSpriteTexturedVertexConsumer(VertexConsumer delegate, Sprite sprite, @Nullable Sprite secondSprite) {
		super(delegate, sprite);
		this.firstSprite = sprite;
		this.hasTwoSprites = (secondSprite != null);
		this.secondSprite = secondSprite;
	}

	public DualSpriteTexturedVertexConsumer(VertexConsumer delegate, Sprite sprite, Boolean hasTwoSprites, @Nullable Sprite secondSprite) {
		this(delegate, sprite, (hasTwoSprites && secondSprite != null) ? secondSprite : null);
	}


	public void useSecondarySprite(boolean input) {
		sprite = (input && hasTwoSprites) ? secondSprite : firstSprite;
	}

}
