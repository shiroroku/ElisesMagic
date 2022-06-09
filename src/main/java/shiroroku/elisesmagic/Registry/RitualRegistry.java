package shiroroku.elisesmagic.Registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import shiroroku.elisesmagic.Block.SigilBlockEntity;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Recipe.CraftingRitualRecipe;
import shiroroku.elisesmagic.Ritual.*;

import java.util.ArrayList;

public class RitualRegistry {
	public enum Rituals {
		HEALING(new RegenerationRitual(new RitualBase.RitualProperties(new ResourceLocation(ElisesMagic.MODID, "regeneration"), 200, 3, new ArrayList<Ingredient>() {{
			add(Ingredient.of(Items.GLISTERING_MELON_SLICE));
			add(Ingredient.of(Items.LAPIS_LAZULI));
			add(Ingredient.of(ItemRegistry.arcania_essence.get()));
		}}))),
		BLACKSUN(new BlackSunRitual(new RitualBase.RitualProperties(new ResourceLocation(ElisesMagic.MODID, "black_sun"), 600, 6, new ArrayList<Ingredient>() {{
			add(Ingredient.of(Items.OBSIDIAN));
			add(Ingredient.of(ItemRegistry.esoteric_gem.get()));
			add(Ingredient.of(ItemRegistry.esoteric_gem.get()));
			add(Ingredient.of(Items.BLACK_STAINED_GLASS));
			add(Ingredient.of(ItemRegistry.esoteric_gem.get()));
			add(Ingredient.of(ItemRegistry.esoteric_gem.get()));
		}}))),
		BLACKSUNREVERSAL(new BlackSunReversalRitual(new RitualBase.RitualProperties(new ResourceLocation(ElisesMagic.MODID, "black_sun_reversal"), 600, 6, new ArrayList<Ingredient>() {{
			add(Ingredient.of(Items.GLOWSTONE));
			add(Ingredient.of(Items.YELLOW_DYE));
			add(Ingredient.of(Items.YELLOW_DYE));
			add(Ingredient.of(Items.GLASS));
			add(Ingredient.of(Items.YELLOW_DYE));
			add(Ingredient.of(Items.YELLOW_DYE));
		}}))),
		ARCANIACONVERSION(new ArcaniaConversionRitual(new RitualBase.RitualProperties(new ResourceLocation(ElisesMagic.MODID, "arcania_conversion"), 100, 4, new ArrayList<Ingredient>() {{
			add(Ingredient.of(ItemRegistry.arcania_essence.get()));
			add(Ingredient.of(Items.LAPIS_BLOCK));
			add(Ingredient.of(Items.LAPIS_BLOCK));
		}})));

		private final RitualBase ritual;

		Rituals(RitualBase ritualin) {
			ritual = ritualin;
		}

		public RitualBase get() {
			return ritual;
		}
	}

	/**
	 * Returns a Ritual object from the registry by its name.
	 */
	public static RitualBase getRitualFromName(Level worldIn, ResourceLocation name) {
		for (Rituals r : Rituals.values()) {
			if (r.get().getRitualProperties().getName().equals(name)) {
				return r.get();
			}
		}
		for (final CraftingRitualRecipe r : worldIn.getRecipeManager().getAllRecipesFor(CraftingRitualRecipe.TYPE)) {
			RitualBase cr = r.createRitual();
			if (cr.getRitualProperties().getName().equals(name)) {
				return cr;
			}
		}
		return null;
	}

	/**
	 * Returns the first Ritual from the registry that the sigil can start.
	 */
	public static RitualBase getRitualFromWorld(ServerLevel worldIn, SigilBlockEntity tile, Player player) {
		//Crafting ritual recipes
		ElisesMagic.LOGGER.debug("Checking crafting rituals");
		for (final CraftingRitualRecipe recipe : worldIn.getRecipeManager().getAllRecipesFor(CraftingRitualRecipe.TYPE)) {
			ElisesMagic.LOGGER.debug("-> {}", recipe.getId().toString());
			CraftingRitual r = recipe.createRitual();
			if (r.canStart(worldIn, tile, player)) {
				return r;
			}
		}
		//Hardcoded rituals
		ElisesMagic.LOGGER.debug("Checking hardcoded rituals");
		for (Rituals r : Rituals.values()) {
			ElisesMagic.LOGGER.debug("-> {}", r.get().getRitualProperties().getName().toString());
			if (r.get().canStart(worldIn, tile, player)) {
				return r.get();
			}
		}

		return null;
	}

}
