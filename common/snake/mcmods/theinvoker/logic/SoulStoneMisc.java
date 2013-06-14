package snake.mcmods.theinvoker.logic;

import net.minecraft.world.World;
import scala.Console;
import snake.mcmods.theinvoker.blocks.TIBlocks;

public class SoulStoneMisc
{
	public static final int MAX_X_SIZE = 7;
	public static final int MAX_Y_SIZE = 3;
	public static final int[][] FORMABLE_SIZE = { { 1, 1, 1 }, { 2, 1, 2 }, { 3, 2, 4 },
	        { MAX_X_SIZE, MAX_Y_SIZE, 5 } };

	public static boolean getIsFormable(World world, int x, int y, int z)
	{
		int mi = 0, mj = 0, mk = 0;
		int tx = x, ty = y, tz = z;
		while (world.getBlockId(tx-1, y, z) == TIBlocks.soulStoneDummy.blockID)
		{
			Console.println("x: " + --tx);
		}
		while (world.getBlockId(x, ty-1, z) == TIBlocks.soulStoneDummy.blockID)
		{
			Console.println("y: " + --ty);
		}
		while (world.getBlockId(x, y, tz-1) == TIBlocks.soulStoneDummy.blockID)
		{
			Console.println("z: " + --tz);
		}

		x = tx;
		y = ty;
		z = tz;

		for (int i = 0; i < MAX_X_SIZE; i++)
		{
			for (int j = 0; j < MAX_Y_SIZE; j++)
			{
				for (int k = 0; k < MAX_X_SIZE; k++)
				{
					tx = x + i;
					ty = y + j;
					tz = z + k;
					int blockID = world.getBlockId(tx, ty, tz);
					if (blockID == TIBlocks.soulStoneDummy.blockID)
					{
						if (mi < i)
							mi = i;
						if (mj < j)
							mj = j;
						if (mk < k)
							mk = k;
					}
				}
			}
		}
		mi++;
		mj++;
		mk++;
		Console.println("got size: " + mi + ", " + mj + ", " + mk);
		return false;
	}

	public static boolean getIsSizeFormable(int x, int y, int z)
	{
		for (int[] arr : FORMABLE_SIZE)
		{
			if ((arr[0] == x && arr[1] == y && arr[2] == z) || (arr[0] == z && arr[1] == y && arr[2] == z))
			{
				return true;
			}
		}
		return false;
	}
}
