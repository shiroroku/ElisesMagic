package shiroroku.elisesmagic.Network;

import net.minecraft.network.FriendlyByteBuf;

public class BooleanMessage {
	public boolean bool;
	public boolean initialized = true;

	public BooleanMessage(boolean bool) {
		this.bool = bool;
		initialized = true;
	}

	public BooleanMessage() {
		initialized = false;
	}

	public static BooleanMessage decode(FriendlyByteBuf buf) {
		BooleanMessage retval = new BooleanMessage();
		try {
			retval.bool = buf.readBoolean();
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
		buf.writeBoolean(bool);
	}
}
