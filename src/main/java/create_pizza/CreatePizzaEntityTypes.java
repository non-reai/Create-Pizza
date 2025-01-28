package create_pizza;

import create_pizza.content.ThrowableTomatoEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CreatePizzaEntityTypes {
	public static final EntityType<ThrowableTomatoEntity> THROWABLE_TOMATO =  Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(CreatePizza.MOD_ID, "throwable_tomato"),
			FabricEntityTypeBuilder.<ThrowableTomatoEntity>create(MobCategory.MISC, ThrowableTomatoEntity::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(100).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static void load() { }
}
