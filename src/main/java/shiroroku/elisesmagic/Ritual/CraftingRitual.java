package shiroroku.elisesmagic.Ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import shiroroku.elisesmagic.Block.SigilBlockEntity;

import javax.annotation.Nullable;

public class CraftingRitual extends RitualBase {

	private final ItemStack output;

	public CraftingRitual(ItemStack output, RitualProperties propertiesin) {
		super(propertiesin);
		this.output = output;
	}

	public ItemStack getOutput() {
		return output;
	}

	@Override
	public void doRitual(Level worldin, SigilBlockEntity tile, Player player) {
		super.doRitual(worldin, tile, player);
		if (worldin.isClientSide()) {
			Vec3 origin = Vec3.atBottomCenterOf(tile.getBlockPos());
			for (BlockPos p : circlePositions) {
				float perc = tile.getRitualPercentage();
				Vec3 pos = Vec3.atBottomCenterOf(p);

				Vec3 control = pos.lerp(origin, 0.5D).add(0, this.getRitualProperties().getCircleSize() / 2f, 0);

				Vec3 lerp1 = pos.lerp(control, perc);
				Vec3 lerp2 = control.lerp(origin, perc);
				Vec3 finalpos = lerp1.lerp(lerp2, perc);

				worldin.addParticle(ParticleTypes.ENCHANT, finalpos.x, finalpos.y, finalpos.z, 0, 0, 0);
			}
		}
	}

	@Override
	public void stopRitual(Level worldin, SigilBlockEntity tile, @Nullable Player player) {
		super.stopRitual(worldin, tile, player);
		ItemEntity output_item = new ItemEntity(worldin, tile.getBlockPos().getX() + 0.5f, tile.getBlockPos().getY() + 0.5f, tile.getBlockPos().getZ() + 0.5f, output);
		if (worldin.isClientSide()) {
			Vec3 origin = Vec3.atBottomCenterOf(tile.getBlockPos());
			for (int i = 0; i < 5; i++) {
				worldin.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, origin.x, origin.y + 0.2, origin.z, 0, 0, 0);
			}
			worldin.playLocalSound(origin.x, origin.y, origin.z, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1, 1, true);
		}
		worldin.addFreshEntity(output_item);
	}
}
