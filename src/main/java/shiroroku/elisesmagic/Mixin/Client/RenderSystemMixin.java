package shiroroku.elisesmagic.Mixin.Client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shiroroku.elisesmagic.World.BlackSunHandler;
import shiroroku.elisesmagic.ElisesMagic;

import java.util.Objects;

import static com.mojang.blaze3d.systems.RenderSystem.*;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

	private static final ResourceLocation SUN = new ResourceLocation(ElisesMagic.MODID, "textures/environment/sun.png");

	//I SHOULD REALLY CHANGE THIS SOMETIME
	@Inject(at = @At("HEAD"), method = "setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", cancellable = true)
	private static void setShaderTexture(int i, ResourceLocation texture, CallbackInfo ci) {
		if (BlackSunHandler.blackSunEnabledClient && Objects.equals(texture, new ResourceLocation("textures/environment/sun.png"))) {
			if (!isOnRenderThread()) {
				recordRenderCall(() -> {
					_setShaderTexture(i, SUN);
				});
			} else {
				_setShaderTexture(i, SUN);
			}
			ci.cancel();
		}
	}
}
