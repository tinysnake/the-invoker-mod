package snake.mcmods.theinvoker.blocks;

import java.util.ArrayList;

import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public interface IMultiBlockStructure
{
	int[][] getPossibleFormTypes();

	int[] getMaximumSize();
	
	int[] getMinimumSize();
	
	boolean getIsFreeSized();

	ArrayList<Integer> getSupportedBlockIDs();
	
	Item getStructureFormerItem();
	
	void onReformed(TileMultiBlockBase tmb);
	
	void onFormed(TileMultiBlockBase tmb);
	
	void onNotAbleToReform(World world, int x, int y, int z, EntityPlayer player, int side);
	
	void onNotAbleToForm(World world, int x, int y, int z, EntityPlayer player, int side);
	
	boolean onActivatedWithoutStructureFormerItem(World world, int x, int y, int z, EntityPlayer player, int side);
}
