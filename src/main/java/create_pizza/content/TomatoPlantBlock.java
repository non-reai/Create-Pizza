package create_pizza.content;

import create_pizza.CreatePizza;
import create_pizza.CreatePizzaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TomatoPlantBlock extends CropBlock {
	private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
	};

	public TomatoPlantBlock(BlockBehaviour.Properties properties) {
		super(properties);  // Defines material properties for the block
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return CreatePizzaItems.TOMATO_SEEDS;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		int age = (Integer)state.getValue(AGE);
		boolean canBonemeal = age == 7;
		if (!canBonemeal && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
			return InteractionResult.PASS;
		} else if (age > 6) {
			int j = 1 + level.random.nextInt(2);
			popResource(level, pos, new ItemStack(CreatePizzaItems.TOMATO, j + (canBonemeal ? 1 : 0)));
			level.playSound((Player)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			BlockState blockState = (BlockState)state.setValue(AGE, 2);
			level.setBlock(pos, blockState, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.use(state, level, pos, player, hand, hit);
		}
	}

	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AGE_TO_SHAPE[this.getAge(state)];
	}

}
