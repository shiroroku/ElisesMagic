package shiroroku.elisesmagic.Network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class RitualMessage {
	public ResourceLocation id;
	public BlockPos pos;
	public int counter;
	public boolean has_started;
	public boolean initialized = true;

	public RitualMessage(ResourceLocation id, BlockPos pos, int counter, boolean has_started) {
		this.id = id;
		this.pos = pos;
		this.counter = counter;
		this.has_started = has_started;
		initialized = true;
	}

	public RitualMessage() {
		initialized = false;
	}

	public static RitualMessage decode(FriendlyByteBuf buf) {
		RitualMessage retval = new RitualMessage();
		try {
			retval.id = buf.readResourceLocation();
			retval.pos = buf.readBlockPos();
			retval.counter = buf.readInt();
			retval.has_started = buf.readBoolean();
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			return retval;
		}
		retval.initialized = true;
		return retval;
	}

	public void encode(FriendlyByteBuf buf) {
		if (!initialized) {
			return;
		}
		buf.writeResourceLocation(id);
		buf.writeBlockPos(pos);
		buf.writeInt(counter);
		buf.writeBoolean(has_started);
	}
}
