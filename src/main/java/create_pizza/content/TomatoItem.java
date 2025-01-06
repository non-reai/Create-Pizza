package create_pizza.content;

import create_pizza.CreatePizzaItems;
import create_pizza.config.ClientConfig;
import create_pizza.config.CreatePizzaConfig;
import create_pizza.foundation.damageTypes.CreatePizzaDamageSources;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class TomatoItem extends Item {

	public TomatoItem(Properties properties) {
		super(properties);
	}

	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
		Player playerentity = entity instanceof Player ? (Player) entity : null;

		playerentity.awardStat(Stats.ITEM_USED.get(CreatePizzaItems.TOMATO.get()));

		if (playerentity instanceof ServerPlayer)
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerentity, stack);

		if (!world.isClientSide)
			entity.hurt(CreatePizzaDamageSources.tomatoPoisoning(world), 10F);

		if (world.isClientSide) {
			if (CreatePizzaConfig.getClient().exitGameOnTomatoEat.get()) {
				System.exit(0);
			}
		}

		return stack;
	}
}
