package shiroroku.elisesmagic.Item.Tarot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.ItemRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class JusticeTarot extends TarotItem {

	public static void handleOnHurt(LivingHurtEvent event) {
		if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntityLiving() instanceof Player player) {
			if (hasTarot(player, ItemRegistry.justice.get())) {
				attacker.hurt(DamageSource.playerAttack(player), event.getAmount());
				ElisesMagic.LOGGER.debug("TAROT PASSIVE: {} - Returning damage", ItemRegistry.justice.get());
				ElisesMagic.LOGGER.debug("From : {} [{}]", attacker, attacker.getHealth());
				ElisesMagic.LOGGER.debug("To : {}", player);
				ElisesMagic.LOGGER.debug("Amount : {}", event.getAmount());
			}
		}
	}

}
