package shiroroku.elisesmagic.Ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.RecipeMatcher;
import shiroroku.elisesmagic.Block.SigilBlockEntity;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.BlockRegistry;
import shiroroku.elisesmagic.Util;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RitualBase {

	private final RitualProperties properties;
	private final Player creator = null;
	public List<BlockPos> circlePositions = new ArrayList<>();

	public RitualBase(RitualProperties propertiesin) {
		properties = propertiesin;
	}

	/**
	 * Called once when the center sigil is activated and canStart returns true. Keep in mind this also handles the consumption of catalyst items.
	 */
	public void startRitual(Level world, SigilBlockEntity tile, @Nullable Player player) {
		circlePositions = Util.midpointCircle(tile.getBlockPos(), this.getRitualProperties().getCircleSize());
		tile.setRitualCounter(getRitualProperties().getLength());

		ElisesMagic.LOGGER.debug("Getting items");
		for (ItemEntity e : getItemsOnSigil(world, tile)) {
			for (Ingredient c : this.getRitualProperties().getRequirements()) {
				ItemStack itemStack = e.getItem();
				if (c.test(itemStack)) {
					if (world.isClientSide()) {
						Vec3 p = e.position();
						world.addParticle(ParticleTypes.CLOUD, p.x(), p.y() + 0.5f, p.z(), 0D, 0D, 0D);
					} else {
						ElisesMagic.LOGGER.debug("Consuming item : " + itemStack.getItem());
						itemStack.shrink(1);
					}
				}
			}
		}
	}

	/**
	 * Called every tick by the ritual when it's running.
	 */
	public void doRitual(Level worldin, SigilBlockEntity tile, @Nullable Player player) {
		if (worldin.isClientSide()) {
			for (BlockPos p : circlePositions) {
				worldin.addParticle(ParticleTypes.SNOWFLAKE, p.getX() + 0.5f, p.getY(), p.getZ() + 0.5f, 0, 0, 0);
			}
		}
	}

	/**
	 * Called once when the ritual is finished.
	 */
	public void stopRitual(Level worldin, SigilBlockEntity tile, @Nullable Player player) {
		ElisesMagic.LOGGER.debug("Finished ritual");
		for (BlockPos p : circlePositions) {
			if (tile.getLevel().getBlockState(p) == BlockRegistry.sigil.get().defaultBlockState()) {
				worldin.removeBlock(p, true);
			}
		}
	}

	public RitualProperties getRitualProperties() {
		return properties;
	}

	/**
	 * Returns true if the ritual has the correct ingredients and proper circle size.
	 */
	public boolean canStart(ServerLevel worldin, SigilBlockEntity tile, @Nullable Player player) {
		RitualProperties prop = this.getRitualProperties();
		List<ItemStack> items = new ArrayList<>();
		List<Ingredient> requirements = prop.getRequirements();

		for (ItemEntity e : getItemsOnSigil(worldin, tile)) {
			for (int c = 0; c < e.getItem().getCount(); c++) {
				items.add(e.getItem());
			}
		}

		return !items.isEmpty() && RecipeMatcher.findMatches(items, requirements) != null && hasValidRing(tile, prop.getCircleSize());
	}

	public boolean hasValidRing(SigilBlockEntity tile, int requiredRadius) {
		for (BlockPos p : Util.midpointCircle(tile.getBlockPos(), requiredRadius)) {
			if (tile.getLevel().getBlockState(p) != BlockRegistry.sigil.get().defaultBlockState()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns list of ItemEntities on the sigil center.
	 */
	public static List<ItemEntity> getItemsOnSigil(Level worldin, SigilBlockEntity tile) {
		AABB aabb = new AABB(tile.getBlockPos().getX() - 0.5, tile.getBlockPos().getY(), tile.getBlockPos().getZ() - 0.5, tile.getBlockPos().getX() + 1.5, tile.getBlockPos().getY() + 1, tile.getBlockPos().getZ() + 1.5);
		List<ItemEntity> itemEntities = new ArrayList<>();
		for (Entity e : worldin.getEntities((Entity) null, aabb)) {
			if (e instanceof ItemEntity) {
				itemEntities.add((ItemEntity) e);
			}
		}
		return itemEntities;
	}

	/**
	 * Returns entities within the ritual circle.
	 */
	public List<Entity> getEntitiesInRange(Level worldin, SigilBlockEntity tile) {
		AABB aabb = new AABB(tile.getBlockPos().getX() - getRitualProperties().getCircleSize() + 0.5, tile.getBlockPos().getY(), tile.getBlockPos().getZ() - getRitualProperties().getCircleSize() + 0.5, tile.getBlockPos().getX() + getRitualProperties().getCircleSize() + 0.5, tile.getBlockPos().getY() + 3, tile.getBlockPos().getZ() + getRitualProperties().getCircleSize() + 0.5);
		List<Entity> entities = new ArrayList<>();
		for (Entity e : worldin.getEntities((Entity) null, aabb)) {
			if (e.distanceToSqr(tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY(), tile.getBlockPos().getZ() + 0.5) <= getRitualProperties().getCircleSize() * getRitualProperties().getCircleSize()) {
				entities.add(e);
			}
		}
		return entities;
	}

	public static class RitualProperties {
		private final int length;
		private final int circleSize;
		private final List<Ingredient> requirements;
		private final ResourceLocation name;

		public RitualProperties(ResourceLocation name, int length, int minimumcircleSize, List<Ingredient> requirements) {
			this.name = name;
			this.length = length;
			this.circleSize = minimumcircleSize;
			this.requirements = requirements;
		}

		/**
		 * Item(s) required to start the ritual.
		 */
		public List<Ingredient> getRequirements() {
			return requirements;
		}

		/**
		 * How long the ritual runs in ticks.
		 */
		public int getLength() {
			return length;
		}

		/**
		 * The sigil circle radius this ritual needs.
		 */
		public int getCircleSize() {
			return circleSize;
		}

		/**
		 * NBT friendly ritual name for saving and loading.
		 */
		public ResourceLocation getName() {
			return name;
		}

	}
}
