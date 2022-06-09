package shiroroku.elisesmagic.Ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import shiroroku.elisesmagic.Block.SigilBlockEntity;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Util;
import shiroroku.elisesmagic.World.BlackSunHandler;

public class BlackSunReversalRitual extends RitualBase {

	public BlackSunReversalRitual(RitualProperties propertiesin) {
		super(propertiesin);
	}

	@Override
	public void doRitual(Level worldin, SigilBlockEntity tile, Player player) {
		super.doRitual(worldin, tile, player);
		if (worldin.isClientSide() && worldin.getGameTime() % 2 == 0) {
			Vec3 origin = Vec3.atBottomCenterOf(tile.getBlockPos());
			for (BlockPos p : circlePositions) {
				Vec3 pos = Vec3.atBottomCenterOf(p);
				float perc = tile.getRitualPercentage();
				Vec3 lerped = pos.lerp(origin, perc);

				float py = (float) Mth.lerp(perc, pos.y, pos.y + 2);
				float py2 = (float) Mth.lerp(perc, origin.y, origin.y + 40);
				float pyf = Mth.lerp(perc, py, py2);

				Vec3 rotated = Util.rotateAround(new Vec3(lerped.x, pyf, lerped.z), origin, perc * 360 * 2);
				worldin.addParticle(ParticleTypes.CLOUD, rotated.x, rotated.y, rotated.z, 0D, 0D, 0D);
			}
		}
	}

	@Override
	public void stopRitual(Level worldin, SigilBlockEntity tile, Player player) {
		super.stopRitual(worldin, tile, player);
		if (worldin instanceof ServerLevel server) {
			BlackSunHandler.setEnabled(server, false);
			ElisesMagic.LOGGER.debug("Toggling black sun");
		}
	}

	@Override
	public boolean canStart(ServerLevel worldin, SigilBlockEntity tile, Player player) {
		return super.canStart(worldin, tile, player) && worldin.dimension() == Level.OVERWORLD && worldin.canSeeSky(tile.getBlockPos());
	}

}
