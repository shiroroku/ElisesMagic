package shiroroku.elisesmagic.Item.Tarot;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.LogicalSide;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.ItemRegistry;

public class TheTowerTarot extends TarotItem {

	public static void handleOnHurt(LivingHurtEvent event) {
		if (event.getSource() == DamageSource.FALL && event.getEntityLiving() instanceof Player player) {
			if (hasTarot(player, ItemRegistry.the_tower.get())) {
				ElisesMagic.LOGGER.debug("TAROT PASSIVE: {} - Fall immune", ItemRegistry.the_tower.get());
				ElisesMagic.LOGGER.debug("To : {}", player);
				event.setCanceled(true);
			}
		}
	}

}
