package shiroroku.elisesmagic.Mixin.Client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shiroroku.elisesmagic.World.BlackSunHandler;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

	@Inject(at = @At("RETURN"), method = "getSkyDarken", cancellable = true)
	public void getSkyDarken(float pticks, CallbackInfoReturnable<Float> cir) {
		if (BlackSunHandler.blackSunEnabledClient && ((ClientLevel) (Object) this).dimension().equals(Level.OVERWORLD)) {
			cir.setReturnValue(BlackSunHandler.getSkyDarkenClient(cir.getReturnValueF()));
		}
	}

	@Inject(at = @At("RETURN"), method = "getCloudColor", cancellable = true)
	public void getCloudColor(float pticks, CallbackInfoReturnable<Vec3> cir) {
		if (BlackSunHandler.blackSunEnabledClient && ((ClientLevel) (Object) this).dimension().equals(Level.OVERWORLD)) {
			cir.setReturnValue(BlackSunHandler.getCloudColor(cir.getReturnValue()));
		}
	}

	@Inject(at = @At("RETURN"), method = "getSkyColor", cancellable = true)
	public void getSkyColor(Vec3 vec3, float f, CallbackInfoReturnable<Vec3> cir) {
		if (BlackSunHandler.blackSunEnabledClient && ((ClientLevel) (Object) this).dimension().equals(Level.OVERWORLD)) {
			cir.setReturnValue(BlackSunHandler.getDaySkyColor(cir.getReturnValue()));
		}
	}
}
