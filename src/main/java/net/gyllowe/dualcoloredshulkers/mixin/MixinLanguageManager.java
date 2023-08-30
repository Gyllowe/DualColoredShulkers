package net.gyllowe.dualcoloredshulkers.mixin;

import net.gyllowe.dualcoloredshulkers.language.ShulkerName;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LanguageManager.class)
public abstract class MixinLanguageManager {
	@Inject(
			method = "reload",
			at = @At(
					value = "TAIL"
			)
	)
	private void generateSyntaxCaps(ResourceManager manager, CallbackInfo ci) {
		ShulkerName.reloadSyntaxCaps();
	}

}
