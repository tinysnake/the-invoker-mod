package snake.mcmods.theinvoker.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;

public interface IMultiBlockStructure
{
	int[][] getPossibleFormTypes();

	int[] getMaximumSize();
	
	int[] getMinimumSize();
	
	boolean getIsFreeSized();

	ArrayList<Block> getSupportedBlocks();
	
	Item getStructureFormerItem();
	
	void onReformed(TileMultiBlockBase tmb);
	
	void onFormed(TileMultiBlockBase tmb);
	
	void onNotAbleToReform(World world, int x, int y, int z, EntityPlayer player, int side);
	
	void onNotAbleToForm(World world, int x, int y, int z, EntityPlayer player, int side);
	
	boolean onActivatedWithoutStructureFormerItem(World world, int x, int y, int z, EntityPlayer player, int side);
}
