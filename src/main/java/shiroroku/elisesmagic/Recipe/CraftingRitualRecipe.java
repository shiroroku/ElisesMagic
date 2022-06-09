package shiroroku.elisesmagic.Recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;
import shiroroku.elisesmagic.Ritual.CraftingRitual;
import shiroroku.elisesmagic.Ritual.RitualBase;

public class CraftingRitualRecipe implements Recipe<Container> {

	public static final RecipeType<CraftingRitualRecipe> TYPE = RecipeType.register("crafting_ritual");
	public static final Serializer SERIALIZER = new Serializer();

	private final ResourceLocation ID;
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final int circleRadius;
	private final int craftTime;

	public CraftingRitualRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, int radius, int crafttime) {
		ID = id;
		this.inputs = inputs;
		this.output = output;
		circleRadius = radius;
		craftTime = crafttime;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public ItemStack getOutput() {
		return output;
	}

	public int getCircleRadius() {
		return circleRadius;
	}

	public int getCraftTime() {
		return craftTime;
	}

	public CraftingRitual createRitual() {
		return new CraftingRitual(this.output, new RitualBase.RitualProperties(ID, getCraftTime(), getCircleRadius(), inputs));
	}

	@Override
	public boolean matches(Container container, Level world) {
		return true;
	}

	@Override
	public ItemStack assemble(Container container) {
		return null;
	}

	@Override
	public boolean canCraftInDimensions(int x, int y) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return TYPE;
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CraftingRitualRecipe> {

		@Override
		public CraftingRitualRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			try {
				NonNullList<Ingredient> inputStacks = NonNullList.create();
				for (int i = 0; i < GsonHelper.getAsJsonArray(json, "ingredients").size(); ++i) {
					Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(json, "ingredients").get(i));
					if (!ingredient.isEmpty()) {
						inputStacks.add(ingredient);
					}
				}

				if (inputStacks.isEmpty()) {
					throw new JsonParseException("No ingredients for crafting ritual recipe.");
				} else {
					ItemStack outputStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
					int radius = GsonHelper.getAsInt(json, "circle_radius");
					int craftTime = GsonHelper.getAsInt(json, "craft_time");
					return new CraftingRitualRecipe(recipeId, inputStacks, outputStack, radius, craftTime);
				}
			} catch (JsonSyntaxException e) {
				return null;
			}
		}

		@Nullable
		@Override
		public CraftingRitualRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			int inputSize = buffer.readVarInt();
			NonNullList<Ingredient> inputStacks = NonNullList.withSize(inputSize, Ingredient.EMPTY);
			for (int i = 0; i < inputStacks.size(); ++i) {
				inputStacks.set(i, Ingredient.fromNetwork(buffer));
			}
			int circleRadius = buffer.readInt();
			int craftTime = buffer.readInt();
			ItemStack outputStack = buffer.readItem();
			return new CraftingRitualRecipe(id, inputStacks, outputStack, circleRadius, craftTime);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, CraftingRitualRecipe recipe) {
			buffer.writeVarInt(recipe.inputs.size());
			for (Ingredient ingredient : recipe.inputs) {
				ingredient.toNetwork(buffer);
			}
			buffer.writeInt(recipe.circleRadius);
			buffer.writeInt(recipe.craftTime);
			buffer.writeItem(recipe.output);
		}
	}
}
