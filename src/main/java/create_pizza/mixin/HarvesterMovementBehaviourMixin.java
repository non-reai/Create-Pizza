package create_pizza.mixin;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;

import com.simibubi.create.infrastructure.config.AllConfigs;

import create_pizza.CreatePizzaBlocks;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HarvesterMovementBehaviour.class)
public class HarvesterMovementBehaviourMixin {
	@Redirect(method = "visitNewPosition", at = @At(value = "INVOKE",
			target = "Lcom/simibubi/create/content/contraptions/actors/harvester/HarvesterMovementBehaviour;cutCrop(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"))
	private BlockState cutCrop(HarvesterMovementBehaviour instance, Level world, BlockPos pos, BlockState state) {
		if (!AllConfigs.server().kinetics.harvesterReplants.get()) {
			if (state.getFluidState()
					.isEmpty())
				return Blocks.AIR.defaultBlockState();
			return state.getFluidState()
					.createLegacyBlock();
		}

		Block block = state.getBlock();
		if (block == CreatePizzaBlocks.TOMATO_PLANT.get()) {
			return state.setValue(BlockStateProperties.AGE_7, Integer.valueOf(2));
		}
		if (block instanceof CropBlock) {
			CropBlock crop = (CropBlock) block;
			return crop.getStateForAge(0);
		}
		if (block == Blocks.SWEET_BERRY_BUSH) {
			return state.setValue(BlockStateProperties.AGE_3, Integer.valueOf(1));
		}
		if (block == Blocks.SUGAR_CANE || block instanceof GrowingPlantBlock) {
			if (state.getFluidState()
					.isEmpty())
				return Blocks.AIR.defaultBlockState();
			return state.getFluidState()
					.createLegacyBlock();
		}
		if (state.getCollisionShape(world, pos)
				.isEmpty() || block instanceof CocoaBlock) {
			for (Property<?> property : state.getProperties()) {
				if (!(property instanceof IntegerProperty))
					continue;
				if (!property.getName()
						.equals(BlockStateProperties.AGE_1.getName()))
					continue;
				return state.setValue((IntegerProperty) property, Integer.valueOf(0));
			}
		}

		if (state.getFluidState()
				.isEmpty())
			return Blocks.AIR.defaultBlockState();
		return state.getFluidState()
				.createLegacyBlock();
	}
}
