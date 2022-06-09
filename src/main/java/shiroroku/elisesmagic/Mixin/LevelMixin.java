package shiroroku.elisesmagic.Mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shiroroku.elisesmagic.World.BlackSunHandler;

@Mixin(Level.class)
public class LevelMixin {

	@Shadow
	private int skyDarken;

	@Inject(at = @At("RETURN"), method = "updateSkyBrightness")
	public void updateSkyBrightness(CallbackInfo ci) {
		Level world = (Level) (Object) this;
		if (world instanceof ServerLevel se) {
			if (BlackSunHandler.isEnabled(se) && world.dimension().equals(Level.OVERWORLD)) {
				skyDarken = BlackSunHandler.getSkyDarken();
			}
		} else {
			if (BlackSunHandler.blackSunEnabledClient && world.dimension().equals(Level.OVERWORLD)) {
				skyDarken = BlackSunHandler.getSkyDarken();
			}
		}
	}
}
