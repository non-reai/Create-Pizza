package create_pizza;


import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import static create_pizza.CreatePizza.MOD_ID;

public class CreatePizzaParticles {
	// This DefaultParticleType gets called when you want to use your particle in code.
	public static final SimpleParticleType TOMATO_PARTICLE = FabricParticleTypes.simple();

	// Register our custom particle type in the mod initializer.
	public static void load() {
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(MOD_ID, "tomato_splat"), TOMATO_PARTICLE);
	}
}
