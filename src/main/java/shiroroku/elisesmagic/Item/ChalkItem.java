package shiroroku.elisesmagic.Item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import shiroroku.elisesmagic.Block.SigilBlock;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Registry.BlockRegistry;
import shiroroku.elisesmagic.Util;
import shiroroku.elisesmagic.World.BlackSunHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ChalkItem extends Item {

	private boolean magic = false;

	public ChalkItem(Item.Properties properties) {
		this(properties, false);
	}

	public ChalkItem(Properties properties, boolean magic) {
		super(properties.tab(ElisesMagic.CREATIVETAB));
		this.magic = magic;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level world = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();

		//Magic chalk abilitiy
		if (magic && world.getBlockState(pos).getBlock() == BlockRegistry.sigil.get() && world.getBlockState(pos).getValue(SigilBlock.IS_CENTER)) {
			//Removing sigil ring on shift
			if (ctx.getPlayer().isShiftKeyDown()) {

				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				if (world.isClientSide()) {
					for (int i = 0; i < 5; i++) {
						ctx.getPlayer().playSound(SoundEvents.WOOL_HIT, 1f, 0.25f + (0.5f * world.random.nextFloat()));
						world.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.75f - (world.random.nextFloat() / 2), pos.getY() + 0.5f, pos.getZ() + 0.75f - (world.random.nextFloat() / 2), 0.0D, 0D, 0.0D);
					}
				}

				for (BlockPos p : Util.midpointCircle(pos, Math.max(ctx.getItemInHand().getOrCreateTag().getInt("ChalkSize"), 2))) {
					if (world.getBlockState(p).getBlock() == BlockRegistry.sigil.get()) {
						world.setBlock(p, Blocks.AIR.defaultBlockState(), 3);
						if (world.isClientSide()) {
							for (int i = 0; i < 5; i++) {
								world.addParticle(ParticleTypes.CLOUD, p.getX() + 0.75f - (world.random.nextFloat() / 2), p.getY() + 0.5f, p.getZ() + 0.75f - (world.random.nextFloat() / 2), 0.0D, 0D, 0.0D);
							}
						}
					}
				}
			} else
			//Placing sigil ring
			{
				if (world.isClientSide()) {
					ctx.getPlayer().playSound(SoundEvents.FLINTANDSTEEL_USE, 1f, 0.25f + (0.5f * world.random.nextFloat()));
					ctx.getPlayer().playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 0.75f, 0.5f + (0.2f * world.random.nextFloat()));
				}

				for (BlockPos p : Util.midpointCircle(pos, Math.max(ctx.getItemInHand().getOrCreateTag().getInt("ChalkSize"), 2))) {
					if (world.getBlockState(p).canBeReplaced(new BlockPlaceContext(ctx)) && world.getBlockState(p.below()).isFaceSturdy(world, p.below(), Direction.UP) && world.setBlock(p, BlockRegistry.sigil.get().defaultBlockState(), 3)) {
						ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), (entity) -> {
							entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
						});

						if (world.isClientSide()) {
							for (int i = 0; i < 5; i++) {
								world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, p.getX() + 0.75f - (world.random.nextFloat() / 2), p.getY() + 0.5f, p.getZ() + 0.75f - (world.random.nextFloat() / 2), 0.0D, -0.1D, 0.0D);
							}
						}
					}
				}

			}
			return InteractionResult.SUCCESS;
		}
		return tryPlaceSigil(ctx, ctx.getClickedPos());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		//Magic chalk size changing interaction
		if (this.magic && !world.isClientSide()) {
			ItemStack itemstack = player.getItemInHand(hand);
			int size = Math.max(itemstack.getOrCreateTag().getInt("ChalkSize"), 2);
			if (player.isCrouching()) {
				if (size > 2) {
					size--;
				}
			} else {
				size++;
			}
			itemstack.getOrCreateTag().putInt("ChalkSize", size);
			player.displayClientMessage(new TranslatableComponent(this.getDescriptionId() + ".desc", size), true);
			return InteractionResultHolder.success(itemstack);
		}
		return super.use(world, player, hand);
	}

	@Override
	public String getDescriptionId() {
		return this.magic ? "item.elisesmagic.magic_chalk" : super.getDescriptionId();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		if (this.magic) {
			tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".desc", Math.max(stack.getOrCreateTag().getInt("ChalkSize"), 2)).withStyle(ChatFormatting.GRAY));
		}
	}

	private InteractionResult tryPlaceSigil(UseOnContext ctx, BlockPos pos) {
		Level world = ctx.getLevel();
		if (world.getBlockState(pos).isFaceSturdy(world, pos, Direction.UP) && world.getBlockState(pos.above()).canBeReplaced(new BlockPlaceContext(ctx))) {
			if (ctx.getPlayer().isShiftKeyDown()) {
				world.setBlock(pos.above(), BlockRegistry.sigil.get().defaultBlockState().setValue(SigilBlock.IS_CENTER, true), 3);
			} else {
				world.setBlock(ctx.getClickedPos().above(), BlockRegistry.sigil.get().defaultBlockState(), 3);
			}

			if (world.isClientSide()) {
				ctx.getPlayer().playSound(SoundEvents.FLINTANDSTEEL_USE, 1f, 0.25f + (0.5f * world.random.nextFloat()));
				ctx.getPlayer().playSound(SoundEvents.VILLAGER_WORK_CARTOGRAPHER, 0.75f, 0.5f + (0.2f * world.random.nextFloat()));
			}

			ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), (entity) -> {
				entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}

