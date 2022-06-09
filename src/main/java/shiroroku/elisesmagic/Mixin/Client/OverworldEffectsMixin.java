package shiroroku.elisesmagic.Mixin.Client;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shiroroku.elisesmagic.World.BlackSunHandler;

@Mixin(DimensionSpecialEffects.OverworldEffects.class)
public class OverworldEffectsMixin {

	@Inject(at = @At("RETURN"), method = "getBrightnessDependentFogColor", cancellable = true)
	public void getBrightnessDependentFogColor(Vec3 color, float brightness, CallbackInfoReturnable<Vec3> cir) {
		if (BlackSunHandler.blackSunEnabledClient) {
			cir.setReturnValue(BlackSunHandler.getOverworldFogColor(cir.getReturnValue()));
		}
	}
}
