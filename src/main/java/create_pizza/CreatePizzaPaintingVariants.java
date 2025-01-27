package create_pizza;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class CreatePizzaPaintingVariants {
	public static final PaintingVariant I_HATE_TOMATOES = registerPainting("i_hate_tomatoes", new PaintingVariant(32, 32));

	private static PaintingVariant registerPainting(String name, PaintingVariant paintingVariant) {
		return Registry.register(BuiltInRegistries.PAINTING_VARIANT, new ResourceLocation(CreatePizza.MOD_ID, name), paintingVariant);
	}

	public static void load() { }
}
