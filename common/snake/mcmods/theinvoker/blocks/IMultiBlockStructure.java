package snake.mcmods.theinvoker.blocks;

import java.util.ArrayList;

import net.minecraft.item.Item;

public interface IMultiBlockStructure
{
	int[][] getPossibleFormTypes();

	int[] getMaximumSize();
	
	int[] getMinimumSize();
	
	boolean getIsFreeSized();

	ArrayList<Integer> getSupportedBlockIDs();
	
	Item getStructureFormerItem();
}
