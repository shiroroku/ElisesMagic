package shiroroku.elisesmagic.World;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Network.BooleanMessage;
import shiroroku.elisesmagic.SetupCommon;

public class BlackSunHandler {

	/***
	 * Use for client, is updated by packet automatically when server changes setEnabled()
	 */
	public static boolean blackSunEnabledClient = false;

	public static boolean isEnabled(ServerLevel level) {
		return level.getDataStorage().computeIfAbsent(Data::load, Data::create, "elisesmagic").isEnabled();
	}

	public static void setEnabled(ServerLevel level, boolean val) {
		level.getDataStorage().computeIfAbsent(Data::load, Data::create, "elisesmagic").setEnabled(val);
	}

	public static float getSkyDarkenClient(float in) {
		return 0.2f;
	}

	public static Vec3 getDaySkyColor(Vec3 in) {
		return in.multiply(new Vec3(.25, .25, .25));
	}

	public static Vec3 getCloudColor(Vec3 in) {
		return in.multiply(new Vec3(.25, .25, .25));
	}

	public static int getSkyDarken() {
		return 15;
	}

	public static Vec3 getOverworldFogColor(Vec3 color) {
		return color.multiply(new Vec3(.1, .1, .1));
	}

	public static class Data extends SavedData {

		private boolean sun = false;

		public boolean isEnabled() {
			return sun;
		}

		public void sendToClient() {
			SetupCommon.CHANNEL.send(PacketDistributor.ALL.noArg(), new BooleanMessage(sun));
		}

		public void setEnabled(boolean val) {
			ElisesMagic.LOGGER.debug("Setting black sun data from: {} to: {}", sun, val);
			sun = val;
			this.sendToClient();
			this.setDirty();
		}

		public static Data create() {
			return new Data();
		}

		public static Data load(CompoundTag tag) {
			Data data = create();
			data.sun = tag.getBoolean("BlackSunEnabled");
			return data;
		}

		@Override
		public CompoundTag save(CompoundTag nbt) {
			nbt.putBoolean("BlackSunEnabled", sun);
			return nbt;
		}
	}
}
