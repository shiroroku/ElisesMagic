package shiroroku.elisesmagic.Item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.ItemRegistry;

import java.util.UUID;

public class PhytorageBladeItem extends SwordItem {

	private static final ForgeTier tier = new ForgeTier(3, 1200, 9f, 4f, 20, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.phytorage.get()));

	public PhytorageBladeItem() {
		super(tier, 3, -2.4F, new Properties().tab(ElisesMagic.CREATIVETAB).rarity(Rarity.UNCOMMON));

	}

	public boolean isFoil(ItemStack stack) {
		return true;
	}

}
