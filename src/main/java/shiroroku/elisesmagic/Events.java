package shiroroku.elisesmagic;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shiroroku.elisesmagic.Command.ToggleBlackSunCommand;
import shiroroku.elisesmagic.Item.ArcaniaBladeItem;
import shiroroku.elisesmagic.Item.ChronobrandItem;
import shiroroku.elisesmagic.Item.Tarot.*;
import shiroroku.elisesmagic.World.BlackSunHandler;
import shiroroku.elisesmagic.World.FlowerGeneration;

public class Events {
	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		if (event.getClimate().temperature > 0f && event.getClimate().temperature < 1f) {
			event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowerGeneration.ARCANIA_FLOWER_PATCH);
		}
	}

	@SubscribeEvent
	public static void onRegisterCommand(RegisterCommandsEvent event) {
		ToggleBlackSunCommand.register(event.getDispatcher());
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		JusticeTarot.handleOnHurt(event);
		TheDevilTarot.handleOnHurt(event);
		DeathTarot.handleOnHurt(event);
		TheTowerTarot.handleOnHurt(event);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		TheChariotTarot.handleOnPlayerTick(event);
		TheSunTarot.handleOnPlayerTick(event);
		WheelOfFortuneTarot.handleOnPlayerTick(event);
		TheMoonTarot.handleOnPlayerTick(event);
		StrengthTarot.handleOnPlayerTick(event);
		TheEmperorTarot.handleOnPlayerTick(event);
		ChronobrandItem.handleOnPlayerTick(event);
		TheHermitTarot.handleOnPlayerTick(event);
		TheLoversTarot.handleOnPlayerTick(event);
	}

	@SubscribeEvent
	public static void onPlayerPickupXpEvent(PlayerXpEvent.PickupXp event) {
		HierophantTarot.handleOnPlayerPickupXp(event);
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getPlayer().getLevel() instanceof ServerLevel serverLevel) {
			ElisesMagic.LOGGER.debug("Syncing Black Sun data to client");
			serverLevel.getDataStorage().computeIfAbsent(BlackSunHandler.Data::load, BlackSunHandler.Data::create, "elisesmagic").sendToClient();
		}
	}

	@SubscribeEvent
	public static void onItemAttributeEvent(ItemAttributeModifierEvent event){
		ArcaniaBladeItem.handleItemAttribute(event);
		ChronobrandItem.handleItemAttribute(event);
	}
}
