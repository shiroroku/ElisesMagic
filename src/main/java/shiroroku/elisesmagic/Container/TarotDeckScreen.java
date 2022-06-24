package shiroroku.elisesmagic.Container;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import shiroroku.elisesmagic.ElisesMagic;

public class TarotDeckScreen extends AbstractContainerScreen<TarotDeckContainer> {

	private final ResourceLocation GUI = new ResourceLocation(ElisesMagic.MODID, "textures/gui/tarot_deck.png");

	public TarotDeckScreen(TarotDeckContainer container, Inventory inv, Component name) {
		super(container, inv, name);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		TranslatableComponent text = new TranslatableComponent("item.elisesmagic.tarot_deck");
		drawString(matrixStack, Minecraft.getInstance().font, text, this.imageWidth / 2 - Minecraft.getInstance().font.width(text) / 2, 8, 0xfff699);
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, GUI);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

	}
}

