package create_pizza.foundation.lootTableModifiers;

import create_pizza.CreatePizzaItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootTableModifiers {
	private static final ResourceLocation VILLAGE_PLAINS_HOUSE_ID = new ResourceLocation("minecraft","chests/village/village_plains_house");
	private static final ResourceLocation TOMATO_FACTORY_HOUSE_ID = new ResourceLocation("minecraft","chests/tomato_factory");
	public static void modifyLootTables() {
		LootTableEvents.MODIFY.register((resourceManager, lootDataManager, resourceLocation, builder, lootTableSource) -> {
			if (VILLAGE_PLAINS_HOUSE_ID.equals(resourceLocation)) {
				LootPool.Builder poolBuilder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.conditionally(LootItemRandomChanceCondition.randomChance(1F).build())
						.with(LootItem.lootTableItem(CreatePizzaItems.TOMATO).build())
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f)).build());

				builder.pool(poolBuilder.build());
			}

			if (VILLAGE_PLAINS_HOUSE_ID.equals(resourceLocation)) {
				LootPool.Builder poolBuilder = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.conditionally(LootItemRandomChanceCondition.randomChance(1F).build())
						.with(LootItem.lootTableItem(CreatePizzaItems.TOMATO_SEEDS).build())
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 32.0f)).build());

				builder.pool(poolBuilder.build());
			}
		});
	}
}
