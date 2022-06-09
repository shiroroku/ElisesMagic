package shiroroku.elisesmagic.Block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import shiroroku.elisesmagic.Registry.BlockEntityRegistry;
import shiroroku.elisesmagic.Registry.RitualRegistry;
import shiroroku.elisesmagic.Ritual.RitualBase;

public class SigilBlockEntity extends BlockEntity implements Clearable {

	private int ritualCounter = -1;
	private RitualBase currentRitual = null;
	private Player creator = null;

	public SigilBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.sigil.get(), pos, state);
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, T blockEntity) {
		SigilBlockEntity sigil = (SigilBlockEntity) blockEntity;
		if (sigil.getRitualCounter() >= 0) {
			if (sigil.getRitual() != null) {
				if (sigil.getRitualCounter() == 0) {
					sigil.getRitual().stopRitual(level, sigil, sigil.getCreator());
					sigil.setRitual(null);
					sigil.setCreator(null);
				} else {
					sigil.getRitual().doRitual(level, sigil, sigil.getCreator());
				}
			} else {
				sigil.setRitualCounter(-1);
			}
			sigil.setRitualCounter(sigil.getRitualCounter() - 1);
		}
	}

	/**
	 * Returns percentage of completion for the running ritual.
	 */
	public float getRitualPercentage() {
		if (this.getRitualCounter() > 0) {
			return 1 - ((float) this.getRitualCounter() / this.getRitual().getRitualProperties().getLength());
		}
		return 0;
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		if (nbt.contains("RitualCounter", Tag.TAG_INT)) {
			this.ritualCounter = nbt.getInt("RitualCounter");
		}
		if (nbt.contains("Ritual", Tag.TAG_STRING)) {
			String name = nbt.getString("Ritual");
			if (!name.isEmpty()) {
				this.currentRitual = RitualRegistry.getRitualFromName(this.getLevel(), ResourceLocation.tryParse(name));
			}
		}
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		nbt.putInt("RitualCounter", this.getRitualCounter());
		if (this.getRitual() != null) {
			ResourceLocation name = this.getRitual().getRitualProperties().getName();
			nbt.putString("Ritual", name.toString());
		}else{
			nbt.putString("Ritual", "");
		}
	}

	public Player getCreator() {
		return creator;
	}

	public void setCreator(Player creator) {
		this.creator = creator;
		this.setChanged();
	}

	public void setRitualCounter(int tick) {
		this.ritualCounter = tick;
		this.setChanged();
	}

	public int getRitualCounter() {
		return this.ritualCounter;
	}

	public void setRitual(RitualBase ritual) {
		this.currentRitual = ritual;
		this.setChanged();
	}

	public RitualBase getRitual() {
		return this.currentRitual;
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = super.getUpdateTag();
		this.saveAdditional(nbt);
		return nbt;
	}

	@Override
	public void clearContent() {
		this.setRitualCounter(-1);
		this.setRitual(null);
	}

}
