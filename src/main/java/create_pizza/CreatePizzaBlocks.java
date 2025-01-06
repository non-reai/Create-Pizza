package create_pizza;

import com.tterrag.registrate.util.entry.BlockEntry;

import create_pizza.content.TomatoPlantBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static create_pizza.CreatePizza.REGISTRATE;

public class CreatePizzaBlocks {
	public static final BlockEntry<TomatoPlantBlock> TOMATO_PLANT =
			REGISTRATE.block("tomato_plant", TomatoPlantBlock::new)
					.initialProperties(() -> Blocks.GLASS)
					.properties(BlockBehaviour.Properties::noCollission)
					.properties(p -> p.isViewBlocking((blockState, blockGetter, blockPos) -> false))
					.properties(p -> p.isViewBlocking((a,b,c) -> false))
					.properties(p -> p.sound(SoundType.CROP))
					.properties(BlockBehaviour.Properties::instabreak)
					.addLayer(() -> RenderType::cutout)
					.register(); // Register the block

	public static final BlockEntry<Block> BLOCK_OF_TOMATO =
			REGISTRATE.block("block_of_tomato", Block::new)
					.properties(p -> p.destroyTime(1F))
					.simpleItem()
					.register(); // Register the block


	public static void load() { }
}
