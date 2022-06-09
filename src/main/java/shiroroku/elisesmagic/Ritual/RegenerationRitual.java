package shiroroku.elisesmagic.Ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import shiroroku.elisesmagic.Block.SigilBlockEntity;

import javax.annotation.Nullable;

public class RegenerationRitual extends RitualBase {

	public RegenerationRitual(RitualProperties propertiesin) {
		super(propertiesin);
	}

	@Override
	public void doRitual(Level worldin, SigilBlockEntity tile, @Nullable Player player) {
		super.doRitual(worldin, tile, player);
		if (worldin.isClientSide()) {
			for (BlockPos p : circlePositions) {
				if (worldin.getGameTime() % 10 == 0) {
					worldin.addParticle(ParticleTypes.HEART, p.getX() + 0.5f, p.getY() + 0.25f, p.getZ() + 0.5f, 0, 0.1, 0);
				}
			}
		} else {
			for (Entity e : getEntitiesInRange(worldin, tile)) {
				if (e instanceof LivingEntity living) {
					living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3));
				}
			}
		}
	}

}
