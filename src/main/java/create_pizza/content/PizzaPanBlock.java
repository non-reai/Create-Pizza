package create_pizza.content;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.foundation.sound.SoundScapes;
import com.tterrag.registrate.util.entry.ItemEntry;

import create_pizza.CreatePizza;
import create_pizza.CreatePizzaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PizzaPanBlock extends Block {
	public static final IntegerProperty PIZZA = IntegerProperty.create("pizza", 0,10);
	public static final IntegerProperty CUT = IntegerProperty.create("cut", 0,4);
	public static final HashMap<ResourceLocation, Integer> PIZZAS = new HashMap<ResourceLocation, Integer>();

	public VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0, 1, 0.125, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.9375, 1, 0.125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0.0625, 0.0625, 1, 0.125, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.0625, 0.0625, 0.125, 0.9375), BooleanOp.OR);

		return shape;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(PIZZA);
		builder.add(CUT);
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
		player.awardStat(Stats.BLOCK_MINED.get(this));
		player.causeFoodExhaustion(0.005F);
		dropResources(state, level, pos, blockEntity, player, tool);

		if (state.getValue(PIZZA) != 0) {
			popResource(level, pos, new ItemStack(BuiltInRegistries.ITEM.get(getKeyByValue(PIZZAS, state.getValue(PIZZA))), 1));
		}
	}

	public PizzaPanBlock(Properties properties) {
		super(properties);  // Defines material properties for the block
		PIZZAS.put(CreatePizzaItems.RAW_CHEESE_PIZZA.getId(), 1);
		PIZZAS.put(CreatePizzaItems.CHEESE_PIZZA.getId(), 2);
		PIZZAS.put(CreatePizzaItems.RAW_SAUSAGE_PIZZA.getId(), 3);
		PIZZAS.put(CreatePizzaItems.SAUSAGE_PIZZA.getId(), 4);
		PIZZAS.put(CreatePizzaItems.RAW_PEPPERONI_PIZZA.getId(), 5);
		PIZZAS.put(CreatePizzaItems.PEPPERONI_PIZZA.getId(), 6);
	}

	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Map.Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {


		if (state.getValue(CUT) > 0 && state.getValue(PIZZA) > 0) {
			int pizza_nutrition = BuiltInRegistries.ITEM.get(
					getKeyByValue(
							PIZZAS, state.getValue(PIZZA)
					)
			).getFoodProperties().getNutrition();
			float pizza_saturation_multiplier = BuiltInRegistries.ITEM.get(
					getKeyByValue(
							PIZZAS, state.getValue(PIZZA)
					)
			).getFoodProperties().getSaturationModifier();
			player.getFoodData().setFoodLevel(
					(int)((float)player.getFoodData().getFoodLevel() + ((float)pizza_nutrition / 4F))
			);
			player.getFoodData().setSaturation(
					player.getFoodData().getSaturationLevel() + (((float) pizza_nutrition / 4) * pizza_saturation_multiplier)
			);

			level.playSound((Player)null, pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.2F);

			BlockState blockState = (BlockState)state.setValue(CUT, (state.getValue(CUT) + 1) % 5);
			if (blockState.getValue(CUT) == 0) {
				blockState = blockState.setValue(PIZZA, 0);
			}
			level.setBlock(pos, blockState, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));

			player.swing(hand);
			return InteractionResult.CONSUME_PARTIAL;
		}

		if (state.getValue(CUT) == 0 && state.getValue(PIZZA) > 0 && player.getItemInHand(hand).getItem().equals(CreatePizzaItems.PIZZA_CUTTER.get())) {
			BlockState blockState = (BlockState)state.setValue(CUT, 1);
			level.setBlock(pos, blockState, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));

			level.playSound((Player)null, pos, AllSoundEvents.SAW_ACTIVATE_WOOD.getMainEvent(), SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

			player.swing(hand);
			return InteractionResult.SUCCESS;
		}

		if (state.getValue(PIZZA) != 0) {
			if (player.getItemInHand(hand).getItem().toString().equals("air")) {
				popResource(level, pos, new ItemStack(BuiltInRegistries.ITEM.get(getKeyByValue(PIZZAS, state.getValue(PIZZA))), 1));

				BlockState blockState = (BlockState)state.setValue(PIZZA, 0);
				level.setBlock(pos, blockState, 2);
				level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));

				level.playSound((Player)null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

				player.swing(hand);
			}
			return InteractionResult.PASS;
		}

		if (PIZZAS.get(BuiltInRegistries.ITEM.getKey(player.getItemInHand(hand).getItem())) != null) {
			BlockState blockState = (BlockState)state.setValue(PIZZA, PIZZAS.get(BuiltInRegistries.ITEM.getKey(player.getItemInHand(hand).getItem())));
			level.setBlock(pos, blockState, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));

			level.playSound((Player)null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

			player.swing(hand);
			player.getItemInHand(hand).shrink(1);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}

	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return makeShape();
	}


}
