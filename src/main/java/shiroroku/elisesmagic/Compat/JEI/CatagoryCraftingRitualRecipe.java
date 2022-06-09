package shiroroku.elisesmagic.Compat.JEI;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import shiroroku.elisesmagic.ElisesMagic;

import java.util.List;

@SuppressWarnings("deprecation")
public class CatagoryCraftingRitualRecipe implements IRecipeCategory<CatagoryCraftingRitualRecipe.Wrapper> {

	private final IDrawable bg, icon;
	public static final ResourceLocation screen = new ResourceLocation(ElisesMagic.MODID, "textures/gui/crafting_ritual.png");

	public CatagoryCraftingRitualRecipe(IGuiHelper guihelper) {
		this.bg = guihelper.createDrawable(screen, 0, 0, 162, 92);
		this.icon = guihelper.createDrawableIngredient(new ItemStack(Items.CRAFTING_TABLE));
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, Wrapper recipe, IFocusGroup focuses) {
		int inputs = recipe.getInputs().size();

		int ox = 73;
		int oy = 40;
		int radius = 27;

		float step = 360f / inputs;

		for (int i = 0; i < inputs; i++) {
			double rad = Math.toRadians(step * i - 90);
			int x = ox + (int) (radius * Math.cos(rad));
			int y = oy + (int) (radius * Math.sin(rad));
			builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(recipe.getInputs().get(i));
		}
		builder.addSlot(RecipeIngredientRole.OUTPUT, ox, oy).addItemStack(recipe.getOutput());

	}

	@Override
	public void draw(Wrapper recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
		Font f = Minecraft.getInstance().font;
		MutableComponent ritualName = new TranslatableComponent("jei.elisesmagic.crafting_ritual", recipe.getCircleSize()).withStyle(ChatFormatting.BOLD);
		f.draw(stack, ritualName, 81 - (f.width(ritualName) / 2f), 0, 4210752);
		MutableComponent radius = new TranslatableComponent("jei.elisesmagic.ritual_size", recipe.getCircleSize()).withStyle(ChatFormatting.UNDERLINE);
		f.draw(stack, radius, 81 - (f.width(radius) / 2f), 85, 4210752);
	}

	@Override
	public Class<? extends Wrapper> getRecipeClass() {
		return null;
	}

	@Override
	public ResourceLocation getUid() {
		return null;
	}

	@Override
	public RecipeType<Wrapper> getRecipeType() {
		return JEIModPlugin.CRAFTING_RITUAL;
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent("jei.elisesmagic.crafting_ritual");
	}

	@Override
	public IDrawable getBackground() {
		return bg;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	public static class Wrapper {
		private final List<Ingredient> INPUTS;
		private final ItemStack OUTPUT;
		private final int CIRCLESIZE;

		public Wrapper(List<Ingredient> input, ItemStack output, int circlesize) {
			INPUTS = input;
			OUTPUT = output;
			CIRCLESIZE = circlesize;
		}

		public List<Ingredient> getInputs() {
			return INPUTS;
		}

		public ItemStack getOutput() {
			return OUTPUT;
		}

		public int getCircleSize() {
			return CIRCLESIZE;
		}
	}
}
