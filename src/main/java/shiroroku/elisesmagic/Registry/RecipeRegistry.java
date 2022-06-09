package shiroroku.elisesmagic.Registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Recipe.CraftingRitualRecipe;

public class RecipeRegistry {

	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ElisesMagic.MODID);

	public static final RegistryObject<RecipeSerializer<CraftingRitualRecipe>> FORGE_FURNACE_SERIALIZER = SERIALIZERS.register("crafting_ritual", () -> CraftingRitualRecipe.SERIALIZER);

	public static void register() {
		SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
