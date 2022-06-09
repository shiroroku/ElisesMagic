package shiroroku.elisesmagic;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import shiroroku.elisesmagic.Network.BlackSunMessageHandler;
import shiroroku.elisesmagic.Network.BooleanMessage;
import shiroroku.elisesmagic.Network.RitualMessage;
import shiroroku.elisesmagic.Network.RitualMessageHandler;
import shiroroku.elisesmagic.World.FlowerGeneration;
import shiroroku.elisesmagic.World.Loot.LootModifier;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ElisesMagic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupCommon {

	public static SimpleChannel CHANNEL;

	@SubscribeEvent
	public static void commonSetup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(Events.class);

		CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ElisesMagic.MODID, "channel"), () -> "1.0", s -> true, s -> true);

		CHANNEL.registerMessage(0, BooleanMessage.class, BooleanMessage::encode, BooleanMessage::decode, BlackSunMessageHandler::onMessageReceived, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(1, RitualMessage.class, RitualMessage::encode, RitualMessage::decode, RitualMessageHandler::onMessageReceived, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

		event.enqueueWork(FlowerGeneration::registerWildCropGeneration);
	}

	@SubscribeEvent
	public static void registerModifierSerializers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry().register(new LootModifier.Serializer().setRegistryName(new ResourceLocation(ElisesMagic.MODID, "loot_additions")));
	}

}