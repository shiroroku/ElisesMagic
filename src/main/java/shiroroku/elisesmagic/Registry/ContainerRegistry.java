package shiroroku.elisesmagic.Registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shiroroku.elisesmagic.Container.TarotDeckContainer;
import shiroroku.elisesmagic.ElisesMagic;

public class ContainerRegistry {
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ElisesMagic.MODID);

	public static final RegistryObject<MenuType<TarotDeckContainer>> tarot_deck = CONTAINERS.register("tarot_deck", () -> IForgeMenuType.create((windowId, inv, data) -> new TarotDeckContainer(windowId, inv, inv.player)));

	public static void register() {
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
