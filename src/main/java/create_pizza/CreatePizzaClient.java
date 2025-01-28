package create_pizza;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;

public class CreatePizzaClient implements ClientModInitializer {
	public static float TOMATO_SPLAT_OVERLAY_Y = 1;
	public static float TOMATO_SPLAT_OVERLAY_COOLDOWN = 70;
	@Override
	public void onInitializeClient() {
		CreatePizzaParticles.load();
		CreatePizzaClientEvents.register();

		HudRenderCallback.EVENT.register((ctx, dt)->{
			if (TOMATO_SPLAT_OVERLAY_COOLDOWN > 0) {
				TOMATO_SPLAT_OVERLAY_COOLDOWN -= dt;
			} else {
				TOMATO_SPLAT_OVERLAY_Y = Math.min(TOMATO_SPLAT_OVERLAY_Y + (dt/100), 1);
			}
			ctx.blit(new ResourceLocation("create_pizza", "textures/particle/tomato_splat.png"), 10, (int) (10+(ctx.guiHeight()*TOMATO_SPLAT_OVERLAY_Y)), 0, 0, ctx.guiWidth()-20, ctx.guiHeight()-20, ctx.guiWidth()-20, ctx.guiHeight()-20);
		});

		ParticleFactoryRegistry.getInstance().register(CreatePizzaParticles.TOMATO_PARTICLE, SplashParticle.Provider::new);

		EntityRendererRegistry.register(CreatePizzaEntityTypes.THROWABLE_TOMATO, (context) ->
				new ThrownItemRenderer(context));
	}
}
