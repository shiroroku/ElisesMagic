package shiroroku.elisesmagic.Block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import shiroroku.elisesmagic.ElisesMagic;

public class SigilBlockEntityRenderer implements BlockEntityRenderer<SigilBlockEntity> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ElisesMagic.MODID, "blocks/sigil_center");

	float a = 0f;
	float r = 1f;
	float g = 1f;
	float b = 1f;

	@Override
	public void render(SigilBlockEntity blockEntity, float pticks, PoseStack stack, MultiBufferSource buff, int light, int overlay) {
		TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE);
		VertexConsumer builder = buff.getBuffer(RenderType.translucent());

		a = 1f;
		stack.pushPose();

		if (blockEntity.getRitualCounter() != -1) {
			stack.translate(0.5D, 0D, 0.5D);
			float prog = (float) blockEntity.getRitualCounter() / (float) blockEntity.getRitual().getRitualProperties().getLength();
			stack.mulPose(Vector3f.YN.rotationDegrees(prog * 360));
			stack.translate(-0.5D, 0D, -0.5D);
		}

		Vector3f pos = new Vector3f(0f, 0.06f, 0f);
		add(builder, stack, 0f + pos.x(), pos.y(), 1f - pos.z(), sprite.getU0(), sprite.getV1(), r, g, b, a);
		add(builder, stack, 1f - pos.x(), pos.y(), 1f - pos.z(), sprite.getU1(), sprite.getV1(), r, g, b, a);
		add(builder, stack, 1f - pos.x(), pos.y(), 0f + pos.z(), sprite.getU1(), sprite.getV0(), r, g, b, a);
		add(builder, stack, 0f + pos.x(), pos.y(), 0f + pos.z(), sprite.getU0(), sprite.getV0(), r, g, b, a);
		stack.popPose();
	}

	private void add(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, float r, float g, float b, float a) {
		renderer.vertex(stack.last().pose(), x, y, z).color(r, g, b, a).uv(u, v).uv2(0, 240).normal(1, 0, 0).endVertex();
	}
}
