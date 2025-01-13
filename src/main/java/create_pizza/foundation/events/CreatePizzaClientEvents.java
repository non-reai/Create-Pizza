package create_pizza.foundation.events;

import com.mojang.blaze3d.shaders.FogShape;

import create_pizza.CreatePizzaFluids;
import create_pizza.config.CreatePizzaConfig;
import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;

public class CreatePizzaClientEvents {
	public static boolean getFogDensity(FogRenderer.FogMode mode, FogType type, Camera camera, float partialTick, float renderDistance, float nearDistance, float farDistance, FogShape shape, FogEvents.FogData fogData) {
		Level level = Minecraft.getInstance().level;
		BlockPos blockPos = camera.getBlockPosition();
		FluidState fluidState = level.getFluidState(blockPos);
		if (camera.getPosition().y >= blockPos.getY() + fluidState.getHeight(level, blockPos))
			return false;
		Fluid fluid = fluidState.getType();

		if (CreatePizzaFluids.TOMATO_SAUCE.get()
				.isSame(fluid)) {
			fogData.scaleFarPlaneDistance((float) (1f / 32f * CreatePizzaConfig.getClient().tomatoSauceTransparencyMultiplier.get()));
			return true;
		}

		return false;
	}

	public static void getFogColor(FogEvents.ColorData event, float partialTicks) {
		Camera info = event.getCamera();
		Level level = Minecraft.getInstance().level;
		BlockPos blockPos = info.getBlockPosition();
		FluidState fluidState = level.getFluidState(blockPos);
		if (info.getPosition().y > blockPos.getY() + fluidState.getHeight(level, blockPos))
			return;

		Fluid fluid = fluidState.getType();

		if (CreatePizzaFluids.TOMATO_SAUCE.get()
				.isSame(fluid)) {
			event.setRed(219 / 255f);
			event.setGreen(0 / 255f);
			event.setBlue(33 / 255f);
			return;
		}
	}

	public static void register() {
		com.simibubi.create.foundation.events.ClientEvents.ModBusEvents.registerClientReloadListeners();

		FogEvents.RENDER_FOG.register(CreatePizzaClientEvents::getFogDensity);
		FogEvents.SET_COLOR.register(CreatePizzaClientEvents::getFogColor);
	}
}
