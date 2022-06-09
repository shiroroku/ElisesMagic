package shiroroku.elisesmagic.Item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.ItemRegistry;

import java.util.UUID;

public class ArcaniaBladeItem extends SwordItem {

	private static final ForgeTier tier = new ForgeTier(2, 300, 7f, 3f, 20, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ItemRegistry.arcania_gem.get()));
	private static final AttributeModifier speedMod = new AttributeModifier(UUID.nameUUIDFromBytes("MoveSpeedBoost".getBytes()).toString(), 0.1f, AttributeModifier.Operation.MULTIPLY_BASE);

	public ArcaniaBladeItem() {
		super(tier, 3, -2.4F, new Properties().tab(ElisesMagic.CREATIVETAB).rarity(Rarity.UNCOMMON));

	}

	public static void handleItemAttribute(ItemAttributeModifierEvent event) {
		if (event.getItemStack().getItem().equals(ItemRegistry.arcania_blade.get()) && event.getSlotType() == EquipmentSlot.MAINHAND) {
			event.addModifier(Attributes.MOVEMENT_SPEED, speedMod);
		}
	}

	public boolean isFoil(ItemStack stack) {
		return true;
	}

}
