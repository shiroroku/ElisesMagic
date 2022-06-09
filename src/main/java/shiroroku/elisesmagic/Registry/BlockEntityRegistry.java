package shiroroku.elisesmagic.Registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shiroroku.elisesmagic.Block.SigilBlockEntity;
import shiroroku.elisesmagic.ElisesMagic;

public class BlockEntityRegistry {

	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ElisesMagic.MODID);

	public static final RegistryObject<BlockEntityType<SigilBlockEntity>> sigil = TILE_ENTITIES.register("sigil", () -> BlockEntityType.Builder.of(SigilBlockEntity::new, BlockRegistry.sigil.get()).build(null));

	public static void register() {
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
