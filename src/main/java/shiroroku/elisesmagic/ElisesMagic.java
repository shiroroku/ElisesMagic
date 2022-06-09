package shiroroku.elisesmagic;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shiroroku.elisesmagic.Registry.BlockEntityRegistry;
import shiroroku.elisesmagic.Registry.BlockRegistry;
import shiroroku.elisesmagic.Registry.ItemRegistry;
import shiroroku.elisesmagic.Registry.RecipeRegistry;

@Mod(ElisesMagic.MODID)
public class ElisesMagic {

	public static final String MODID = "elisesmagic";
	public static final Logger LOGGER = LogManager.getLogger();

	public ElisesMagic() {
		ItemRegistry.register();
		BlockRegistry.register();
		BlockEntityRegistry.register();
		RecipeRegistry.register();

	}

	public static final CreativeModeTab CREATIVETAB = new CreativeModeTab(MODID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemRegistry.magic_chalk.get());
		}
	};

}
