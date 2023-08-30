package net.gyllowe.dualcoloredshulkers.interfaces;

import com.mojang.blaze3d.vertex.VertexConsumer;

public interface DualSpriteVertexConsumer
		extends VertexConsumer {
	void useSecondarySprite(boolean input);

}
