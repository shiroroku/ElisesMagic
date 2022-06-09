package shiroroku.elisesmagic.Item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import shiroroku.elisesmagic.ElisesMagic;

public class MirrorOfTalentItem extends Item {
	public MirrorOfTalentItem() {
		super(new Properties().tab(ElisesMagic.CREATIVETAB).rarity(Rarity.RARE).stacksTo(1));
	}
}
