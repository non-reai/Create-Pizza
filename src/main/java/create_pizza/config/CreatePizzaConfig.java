package create_pizza.config;

import create_pizza.CreatePizza;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class CreatePizzaConfig {
	private static final CreatePizzaConfig INSTANCE = new CreatePizzaConfig();

	private final ClientConfig client;
	private final CommonConfig common;

	public CreatePizzaConfig() {
		var client = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		this.client = client.getLeft();
		ForgeConfigRegistry.INSTANCE.register(CreatePizza.MOD_ID, ModConfig.Type.CLIENT, client.getRight());

		var common = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		this.common = common.getLeft();
		ForgeConfigRegistry.INSTANCE.register(CreatePizza.MOD_ID, ModConfig.Type.COMMON, common.getRight());
	}

	public static ClientConfig getClient() {
		return INSTANCE.client;
	}

	public static CommonConfig getCommon() {
		return INSTANCE.common;
	}
}
