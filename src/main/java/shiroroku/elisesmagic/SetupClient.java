package shiroroku.elisesmagic;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import shiroroku.elisesmagic.Block.SigilBlockEntityRenderer;
import shiroroku.elisesmagic.Container.TarotDeckScreen;
import shiroroku.elisesmagic.Registry.BlockEntityRegistry;
import shiroroku.elisesmagic.Registry.BlockRegistry;
import shiroroku.elisesmagic.Registry.ContainerRegistry;

@Mod.EventBusSubscriber(modid = ElisesMagic.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupClient {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ItemBlockRenderTypes.setRenderLayer(BlockRegistry.sigil.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockRegistry.arcania_flower.get(), RenderType.cutout());
			MenuScreens.register(ContainerRegistry.tarot_deck.get(), TarotDeckScreen::new);
		});
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			return;
		}
		event.addSprite(SigilBlockEntityRenderer.TEXTURE);
	}

	@SubscribeEvent
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(BlockEntityRegistry.sigil.get(), ctx -> new SigilBlockEntityRenderer());
	}

}

