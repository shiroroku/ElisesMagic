package shiroroku.elisesmagic.Item.Tarot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import shiroroku.elisesmagic.Registry.ItemRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class WheelOfFortuneTarot extends TarotItem {

	private static final float boost = 3f;
	private static final AttributeModifier luckBoost = new AttributeModifier(UUID.nameUUIDFromBytes("TarotWheelOfFortune".getBytes()).toString(), boost, AttributeModifier.Operation.ADDITION);

	public static void handleOnPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.player.tickCount % 20 == 0 && event.side == LogicalSide.SERVER) {
			handleAttribute(event.player, Attributes.ARMOR, luckBoost, ItemRegistry.wheel_of_fortune.get());
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".desc", boost).withStyle(ChatFormatting.BLUE));
	}
}
