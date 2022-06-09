package shiroroku.elisesmagic.Network;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import shiroroku.elisesmagic.World.BlackSunHandler;
import shiroroku.elisesmagic.ElisesMagic;

import java.util.Optional;
import java.util.function.Supplier;

public class BlackSunMessageHandler {

	public static void onMessageReceived(final BooleanMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
		ctx.setPacketHandled(true);
		if (sideReceived != LogicalSide.CLIENT) {
			ElisesMagic.LOGGER.warn("Message received on wrong side:" + ctx.getDirection().getReceptionSide());
			return;
		}
		if (!message.initialized) {
			ElisesMagic.LOGGER.warn("Message was invalid: " + message);
			return;
		}

		Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
		if (clientWorld.isEmpty()) {
			ElisesMagic.LOGGER.warn("BlackSun context could not provide a ClientWorld.");
			return;
		}
		ctx.enqueueWork(() -> processMessage((ClientLevel) clientWorld.get(), message));
	}

	private static void processMessage(ClientLevel worldClient, BooleanMessage message) {
		ElisesMagic.LOGGER.debug("Recived Black Sun update from server : {}", message.bool);
		BlackSunHandler.blackSunEnabledClient = message.bool;
	}
}
