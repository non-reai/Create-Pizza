package create_pizza.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
	public final ForgeConfigSpec.ConfigValue<Boolean> exitGameOnTomatoEat;
	public final ForgeConfigSpec.ConfigValue<Double> tomatoSauceTransparencyMultiplier;

	public ClientConfig(ForgeConfigSpec.Builder builder) {
		exitGameOnTomatoEat = builder
				.comment(
						"Choose if you want your game to exit when you eat a tomato."
				).define("exitGameOnTomatoEat", false);
		tomatoSauceTransparencyMultiplier = builder
				.comment(
						"The vision range through tomato sauce will be multiplied by this factor"
				).defineInRange("tomatoSauceTransparencyMultiplier", 1, 0.125F, 256);
	}
}

