package shiroroku.elisesmagic.Network;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import shiroroku.elisesmagic.Block.SigilBlockEntity;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Recipe.CraftingRitualRecipe;
import shiroroku.elisesmagic.Registry.RitualRegistry;
import shiroroku.elisesmagic.Ritual.RitualBase;

import java.util.Optional;
import java.util.function.Supplier;

public class RitualMessageHandler {

	public static void onMessageReceived(final RitualMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
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
			ElisesMagic.LOGGER.warn("Ritual context could not provide a ClientWorld.");
			return;
		}
		ctx.enqueueWork(() -> processMessage((ClientLevel) clientWorld.get(), message));
	}

	private static void processMessage(ClientLevel worldClient, RitualMessage message) {
		ElisesMagic.LOGGER.debug("Recieved Ritual update from server: {} @ {} - running : {}", message.id, message.pos, message.has_started);

		BlockEntity tile = worldClient.getBlockEntity(message.pos);
		RitualBase ritual = RitualRegistry.getRitualFromName(worldClient, message.id);
		if (tile instanceof SigilBlockEntity sigil) {
			if (ritual != null) {
				sigil.setRitual(ritual);
				sigil.setRitualCounter(message.counter);
				if (!message.has_started) {
					ritual.startRitualClient(worldClient, sigil);
				}
			}
		}
	}
}
