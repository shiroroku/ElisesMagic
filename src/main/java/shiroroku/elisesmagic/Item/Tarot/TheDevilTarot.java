package shiroroku.elisesmagic.Item.Tarot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.ItemRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class TheDevilTarot extends TarotItem {

	public static void handleOnHurt(LivingHurtEvent event) {
		if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player player) {
			if (hasTarot(player, ItemRegistry.the_devil.get())) {
				ElisesMagic.LOGGER.debug("TAROT PASSIVE: {} - Inflict weakness", ItemRegistry.the_devil.get());
				ElisesMagic.LOGGER.debug("From : {}", player);
				ElisesMagic.LOGGER.debug("To : {}", event.getEntityLiving());
				event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 2));
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.BLUE));
	}
}
