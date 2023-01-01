package net.gyllowe.dualcoloredshulkers.interfaces;

import net.minecraft.client.render.VertexConsumer;

public interface DualSpriteVertexConsumer
		extends VertexConsumer {
	void UseSecondarySprite(boolean input);
}
