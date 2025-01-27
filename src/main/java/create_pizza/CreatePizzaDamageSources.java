package create_pizza;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class CreatePizzaDamageSources {
	public static DamageSource tomatoPoisoning(Level level) {
		return source(CreatePizzaDamageTypes.TOMATO_POISONING, level);
	}
	public static DamageSource tomatoHit(Level level) {
		return source(CreatePizzaDamageTypes.TOMATO_HIT, level);
	}

	private static DamageSource source(ResourceKey<DamageType> key, LevelReader level) {
		Registry<DamageType> registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
		return new DamageSource(registry.getHolderOrThrow(key));
	}
}
