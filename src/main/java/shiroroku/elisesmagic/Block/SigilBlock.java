package shiroroku.elisesmagic.Block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;
import shiroroku.elisesmagic.ElisesMagic;
import shiroroku.elisesmagic.Network.RitualMessage;
import shiroroku.elisesmagic.Registry.BlockEntityRegistry;
import shiroroku.elisesmagic.Registry.RitualRegistry;
import shiroroku.elisesmagic.Ritual.RitualBase;
import shiroroku.elisesmagic.SetupCommon;

import javax.annotation.Nullable;

public class SigilBlock extends BaseEntityBlock {

	public static final BooleanProperty IS_CENTER = BooleanProperty.create("is_center");
	protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	public SigilBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(IS_CENTER, Boolean.FALSE));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return state.getValue(IS_CENTER) ? new SigilBlockEntity(pos, state) : null;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> blockEntity) {
		return blockEntity == BlockEntityRegistry.sigil.get() ? SigilBlockEntity::tick : null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockpos = pos.below();
		return level.getBlockState(blockpos).isFaceSturdy(level, blockpos, Direction.UP) || state.is(Blocks.HOPPER);
	}

	@Override
	@SuppressWarnings("deprecation")
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
		if (state.getValue(IS_CENTER) && world instanceof ServerLevel server && hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).isEmpty()) {
			boolean fromPlayer = player != null;

			SigilBlockEntity tile = (SigilBlockEntity) world.getBlockEntity(pos);
			if (tile == null) {
				return InteractionResult.PASS;
			}
			ElisesMagic.LOGGER.debug("Getting ritual from world");
			RitualBase r = RitualRegistry.getRitualFromWorld(server, tile, player);
			if (r != null && tile.getRitual() == null) {
				ElisesMagic.LOGGER.debug("Setting ritual to [{}]", r.getRitualProperties().getName());
				ElisesMagic.LOGGER.debug("Sending to client");
				SetupCommon.CHANNEL.send(PacketDistributor.ALL.noArg(), new RitualMessage(r.getRitualProperties().getName(), pos, tile.getRitualCounter(), false));
				tile.setRitual(r);
				tile.setCreator(player);
				ElisesMagic.LOGGER.debug("Starting ritual");
				r.startRitual(server, tile, player);
			} else {
				ElisesMagic.LOGGER.debug("Failed to find valid ritual");
			}

		}
		return InteractionResult.PASS;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(IS_CENTER, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(IS_CENTER);
	}

	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext ctx) {
		return AABB;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
}
