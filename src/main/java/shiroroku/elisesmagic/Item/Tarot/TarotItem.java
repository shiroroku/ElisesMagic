package shiroroku.elisesmagic.Item.Tarot;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import shiroroku.elisesmagic.ElisesMagic;

public class TarotItem extends Item {
	public TarotItem() {
		super(new Properties().tab(ElisesMagic.CREATIVETAB).rarity(Rarity.UNCOMMON).stacksTo(1));
	}

	public boolean isFoil(ItemStack stack) {
		return true;
	}

	public static boolean hasTarot(Player player, Item tarot) {
		return (player.getInventory().contains(new ItemStack(tarot)));
	}

	public static void handleAttribute(Player player, Attribute a, AttributeModifier mod, Item tarot) {
		boolean hasCard = player.getInventory().contains(new ItemStack(tarot));
		if (player.getAttribute(a).hasModifier(mod)) {
			if (!hasCard) {
				ElisesMagic.LOGGER.debug("Removing Tarot Modifier : {}", mod);
				player.getAttribute(a).removeModifier(mod);
			}
		} else {
			if (hasCard) {
				ElisesMagic.LOGGER.debug("Adding Tarot Modifier : {}", mod);
				player.getAttribute(a).addTransientModifier(mod);
			}
		}
	}
}
