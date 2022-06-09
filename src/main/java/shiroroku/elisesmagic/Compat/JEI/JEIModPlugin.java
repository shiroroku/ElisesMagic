package shiroroku.elisesmagic.Compat.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Recipe.CraftingRitualRecipe;
import shiroroku.elisesmagic.Registry.ItemRegistry;
import shiroroku.elisesmagic.Registry.RitualRegistry;
import shiroroku.elisesmagic.Ritual.CraftingRitual;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {

	public static final RecipeType CRAFTING_RITUAL = RecipeType.create(ElisesMagic.MODID, "crafting_ritual", CatagoryCraftingRitualRecipe.Wrapper.class);
	public static final RecipeType RITUAL = RecipeType.create(ElisesMagic.MODID, "ritual", CatagoryRitualRecipe.Wrapper.class);

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(ElisesMagic.MODID, "recipes");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new CatagoryCraftingRitualRecipe(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new CatagoryRitualRecipe(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registry) {
		registry.addRecipes(CRAFTING_RITUAL, convertCraftingRitualRecipes());
		registry.addRecipes(RITUAL, convertRitualRecipes());
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		registry.addRecipeCatalyst(new ItemStack(ItemRegistry.chalk.get()), CRAFTING_RITUAL, RITUAL);
		registry.addRecipeCatalyst(new ItemStack(ItemRegistry.magic_chalk.get()), CRAFTING_RITUAL, RITUAL);
	}

	private List<CatagoryCraftingRitualRecipe.Wrapper> convertCraftingRitualRecipes() {
		List<CatagoryCraftingRitualRecipe.Wrapper> recipesconverted = new ArrayList<>();
		for (final CraftingRitualRecipe recipe : Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(CraftingRitualRecipe.TYPE)) {
			CraftingRitual r = recipe.createRitual();
			recipesconverted.add(new CatagoryCraftingRitualRecipe.Wrapper(r.getRitualProperties().getRequirements(), r.getOutput(), r.getRitualProperties().getCircleSize()));
		}
		return recipesconverted;
	}

	private List<CatagoryRitualRecipe.Wrapper> convertRitualRecipes() {
		List<CatagoryRitualRecipe.Wrapper> recipesconverted = new ArrayList<>();
		for (RitualRegistry.Rituals r : RitualRegistry.Rituals.values()) {
			recipesconverted.add(new CatagoryRitualRecipe.Wrapper(r.get().getRitualProperties().getName(), r.get().getRitualProperties().getRequirements(), r.get().getRitualProperties().getCircleSize()));
		}
		return recipesconverted;
	}
}
