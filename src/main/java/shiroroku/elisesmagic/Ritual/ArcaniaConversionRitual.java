package shiroroku.elisesmagic.Ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import shiroroku.elisesmagic.Block.SigilBlockEntity;
import shiroroku.elisesmagic.Registry.BlockRegistry;

import javax.annotation.Nullable;

public class ArcaniaConversionRitual extends RitualBase {

	public ArcaniaConversionRitual(RitualProperties propertiesin) {
		super(propertiesin);
	}

	@Override
	public void stopRitual(Level worldin, SigilBlockEntity tile, @Nullable Player player) {
		super.stopRitual(worldin, tile, player);
		int radius = getRitualProperties().getCircleSize();
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				BlockPos pos = tile.getBlockPos().offset(x, 0, z);
				if (worldin.getBlockState(pos).getTags().anyMatch(t -> (t == BlockTags.FLOWERS))) {
					worldin.setBlock(pos, BlockRegistry.arcania_flower.get().defaultBlockState(), 3);
				}
			}
		}
	}

}
