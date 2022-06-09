package shiroroku.elisesmagic.Registry;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shiroroku.elisesmagic.Block.SigilBlock;
import shiroroku.elisesmagic.ElisesMagic;

import java.util.function.Supplier;

public class BlockRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ElisesMagic.MODID);

	public static final RegistryObject<Block> sigil = BLOCKS.register("sigil", () -> new SigilBlock(Block.Properties.of(Material.CLOTH_DECORATION).noCollission().instabreak().sound(SoundType.SAND)));
	public static final RegistryObject<Block> arcania_flower = registerBlockAndItem("arcania_flower", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 9, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));

	public static void register() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	/**
	 * Creates and returns a Block while also creating an Item for that block.
	 *
	 * @param id Block id
	 * @param supplier Block factory
	 * @return Registry object of supplied Block
	 */
	private static <I extends Block> RegistryObject<I> registerBlockAndItem(final String id, final Supplier<? extends I> supplier) {
		RegistryObject<I> createdBlock = BLOCKS.register(id, supplier);
		ItemRegistry.ITEMS.register(id, () -> new BlockItem(createdBlock.get(), new Item.Properties().tab(ElisesMagic.CREATIVETAB)));
		return createdBlock;
	}

}
