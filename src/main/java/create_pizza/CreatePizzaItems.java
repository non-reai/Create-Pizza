package create_pizza;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import create_pizza.content.PinRollerItem;
import create_pizza.content.ThrowableTomatoItem;
import create_pizza.content.TomatoItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

import static create_pizza.CreatePizza.REGISTRATE;

public class CreatePizzaItems {
	// Transitional
	public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_RAW_PEPPERONI_PIZZA =
			REGISTRATE.item("incomplete_raw_pepperoni_pizza", SequencedAssemblyItem::new)
					.register();
	public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_RAW_SAUSAGE_PIZZA =
			REGISTRATE.item("incomplete_raw_sausage_pizza", SequencedAssemblyItem::new)
					.register();

	static {
		REGISTRATE.defaultCreativeTab(CreatePizza.CREATIVE_TAB_KEY);
	}

	// Tools
	public static final ItemEntry<PinRollerItem> PIN_ROLLER =
			REGISTRATE.item("pin_roller", PinRollerItem::new)
					.properties(p -> p.stacksTo(1))
					.register();
	public static final ItemEntry<Item> PIZZA_CUTTER =
			REGISTRATE.item("pizza_cutter", Item::new)
					.properties(p -> p.stacksTo(1))
					.register();

	// Tomatoes
	public static final ItemEntry<TomatoItem> TOMATO =
			REGISTRATE.item("tomato", TomatoItem::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(1)
							.saturationMod(0.1F)
							.build()))
					.register();
	public static final ItemEntry<ThrowableTomatoItem> THROWABLE_TOMATO =
			REGISTRATE.item("throwable_tomato", ThrowableTomatoItem::new)
					.register();
	public static final ItemEntry<ItemNameBlockItem> TOMATO_SEEDS =
			REGISTRATE.item("tomato_seeds", props -> new ItemNameBlockItem(CreatePizzaBlocks.TOMATO_PLANT.get(), props))
					.register();

	// Ingredients
	public static final ItemEntry<Item> MILK_CURDS =
			REGISTRATE.item("milk_curds", Item::new)
					.register();
	public static final ItemEntry<Item> CHEESE =
			REGISTRATE.item("cheese", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(3)
							.saturationMod(0.5F)
							.build()))
					.register();
	public static final ItemEntry<Item> SHREDDED_CHEESE =
			REGISTRATE.item("shredded_cheese", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(3)
							.saturationMod(0.5F)
							.build()))
					.register();
	public static final ItemEntry<Item> PEPPERONI =
			REGISTRATE.item("pepperoni", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(6)
							.saturationMod(0.5F)
							.build()))
					.register();
	public static final ItemEntry<Item> CUT_PEPPERONI =
			REGISTRATE.item("cut_pepperoni", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(1)
							.saturationMod(0.5F)
							.build()))
					.register();
	public static final ItemEntry<Item> SAUSAGE =
			REGISTRATE.item("sausage", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(6)
							.saturationMod(0.5F)
							.build()))
					.register();
	public static final ItemEntry<Item> CUT_SAUSAGE =
			REGISTRATE.item("cut_sausage", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(1)
							.saturationMod(0.5F)
							.build()))
					.register();

	// Pizza
	public static final ItemEntry<Item> PIZZA_DOUGH =
			REGISTRATE.item("pizza_dough", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(2)
							.saturationMod(0.5F)
							.build()))
					.register();

	public static final ItemEntry<Item> RAW_SAUCED_PIZZA =
			REGISTRATE.item("raw_sauced_pizza", Item::new)
					.register();

	public static final ItemEntry<Item> RAW_CHEESE_PIZZA =
			REGISTRATE.item("raw_cheese_pizza", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(4)
							.saturationMod(0.2F)
							.build()))
					.register();
	public static final ItemEntry<Item> CHEESE_PIZZA =
			REGISTRATE.item("cheese_pizza", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(12)
							.saturationMod(0.6F)
							.build()))
					.register();
	public static final ItemEntry<Item> RAW_SAUSAGE_PIZZA =
			REGISTRATE.item("raw_sausage_pizza", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(4)
							.saturationMod(0.2F)
							.build()))
					.register();
	public static final ItemEntry<Item> SAUSAGE_PIZZA =
			REGISTRATE.item("sausage_pizza", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(14)
							.saturationMod(0.6F)
							.build()))
					.register();
	public static final ItemEntry<Item> RAW_PEPPERONI_PIZZA =
			REGISTRATE.item("raw_pepperoni_pizza", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(4)
							.saturationMod(0.2F)
							.build()))
					.register();
	public static final ItemEntry<Item> PEPPERONI_PIZZA =
			REGISTRATE.item("pepperoni_pizza", Item::new)
					.properties(p -> p.food(new FoodProperties.Builder()
							.nutrition(14)
							.saturationMod(0.6F)
							.build()))
					.register();

	// Disc

	public static final ItemEntry<RecordItem> MUSIC_DISC_TOMATO_JAM =
			REGISTRATE.item("music_disc_tomato_jam", props -> new RecordItem(14, CreatePizzaSoundEvents.TOMATO_JAM.getMainEvent(), props.stacksTo(1).rarity(Rarity.RARE), 112))
					.register();
	public static final ItemEntry<RecordItem> MUSIC_DISC_PIZZA =
			REGISTRATE.item("music_disc_pizza", props -> new RecordItem(14, CreatePizzaSoundEvents.PIZZA.getMainEvent(), props.stacksTo(1).rarity(Rarity.RARE), 83))
					.register();

	public static void load() { }
}
