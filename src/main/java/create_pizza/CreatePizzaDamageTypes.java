package create_pizza;

import com.simibubi.create.foundation.damageTypes.DamageTypeBuilder;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class CreatePizzaDamageTypes {
	public static final ResourceKey<DamageType>
			TOMATO_POISONING = key("tomato_poisoning");

	private static ResourceKey<DamageType> key(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, CreatePizza.asResource(name));
	}

	public static void bootstrap(BootstapContext<DamageType> ctx) {
		new DamageTypeBuilder(TOMATO_POISONING).register(ctx);
	}
}
