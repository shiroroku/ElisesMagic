package shiroroku.elisesmagic.Item;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.LogicalSide;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.ItemRegistry;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChronobrandItem extends SwordItem {

	private static final ForgeTier tier = new ForgeTier(2, 1, 12.0F, 0.0F, 22, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemStack.EMPTY));
	private static final UUID damageMod = UUID.nameUUIDFromBytes("ChronoscaleDamage".getBytes());
	private static final UUID speedMod = UUID.nameUUIDFromBytes("ChronoscaleSpeed".getBytes());

	private static final int maxPowerAt = 43200;
	private static final int attackCost = 15;
	private static final float maxAdditiveDamage = 8f;
	private static final float maxAdditiveSpeed = 1.6f;

	public ChronobrandItem() {
		super(tier, 3, -2.4F, new Properties().tab(ElisesMagic.CREATIVETAB).rarity(Rarity.UNCOMMON).durability(-1));
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !oldStack.getItem().equals(newStack.getItem());
	}

	public static void handleItemAttribute(ItemAttributeModifierEvent event) {
		if (event.getItemStack().getItem().equals(ItemRegistry.chronobrand.get()) && event.getSlotType() == EquipmentSlot.MAINHAND) {
			double powerPerc = Math.max(event.getItemStack().getOrCreateTag().getLong("TimeKept"), 0) / (float) maxPowerAt;
			event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(damageMod, "Chronoscale", Math.min(maxAdditiveDamage * powerPerc, maxAdditiveDamage), AttributeModifier.Operation.ADDITION));
			event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(speedMod, "Chronoscale", Math.min(maxAdditiveSpeed * powerPerc, maxAdditiveSpeed), AttributeModifier.Operation.ADDITION));
		}
	}

	private static long getTimeKept(ItemStack chronoblade) {
		return Math.max(Math.min(chronoblade.getOrCreateTag().getLong("TimeKept"), 362439), 0);
	}

	private static void setTimeKept(ItemStack chronoblade, long seconds) {
		chronoblade.getOrCreateTag().putLong("TimeKept", Math.max(Math.min(seconds, 362439), 0));
	}

	public static void handleOnPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.player.tickCount % 20 == 0 && event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
			if (event.player.getInventory().contains(new ItemStack(ItemRegistry.chronobrand.get()))) {
				ItemStack weapon = null;
				final List<NonNullList<ItemStack>> compartments = ImmutableList.of(event.player.getInventory().items, event.player.getInventory().armor, event.player.getInventory().offhand);
				foundItem:
				for (List<ItemStack> list : compartments) {
					for (ItemStack itemstack : list) {
						if (itemstack.getItem() == ItemRegistry.chronobrand.get()) {
							weapon = itemstack;
							break foundItem;
						}
					}
				}
				if (weapon != null) {
					long seconds = getTimeKept(weapon);
					seconds++;
					setTimeKept(weapon, seconds);
				}
			}
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity entity1) {
		long seconds = getTimeKept(stack);
		seconds -= attackCost;
		setTimeKept(stack, seconds);
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".desc", hmsFromSeconds(getTimeKept(stack))).withStyle(ChatFormatting.GRAY));
	}

	private static String hmsFromSeconds(long seconds) {
		return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds = seconds % 60);
	}

}
