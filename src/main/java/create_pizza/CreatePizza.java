package create_pizza;

import com.simibubi.create.Create;

import com.simibubi.create.foundation.data.CreateRegistrate;

import create_pizza.config.CreatePizzaConfig;
import create_pizza.foundation.events.ClientEvents;
import create_pizza.foundation.lootTableModifiers.LootTableModifiers;
import create_pizza.foundation.soundEvents.SoundEvents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.CreativeModeTab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreatePizza implements ModInitializer {
	public static final String MOD_ID = "create_pizza";
	public static final String NAME = "Create Pizza";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

	public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
			new ResourceLocation(MOD_ID, "tab"));


	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);

		registerCreativeTab();

		CreatePizzaItems.load();
		CreatePizzaBlocks.load();
		CreatePizzaFluids.load();

		CreatePizzaConfig.getClient();

		SoundEvents.prepare();

		REGISTRATE.register();

		SoundEvents.register();

		LootTableModifiers.modifyLootTables();

		ClientEvents.register();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	private void registerCreativeTab() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
				new ResourceLocation(MOD_ID, "tab"),
				FabricItemGroup.builder()
						.icon(CreatePizzaItems.PIN_ROLLER::asStack)
						.title(Component.translatable("tab." + MOD_ID + ".tab"))
						.build());

		REGISTRATE.defaultCreativeTab(CREATIVE_TAB_KEY);
	}
}
