package create_pizza.content;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public class PinRollerItem extends Item {

	public PinRollerItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getRecipeRemainder(ItemStack stack) {
		return stack;
	}
}
