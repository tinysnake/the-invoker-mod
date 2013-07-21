package snake.mcmods.theinvoker.logic;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.blocks.IMultiBlockStructure;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;

public class MultiBlockStructureHelper
{

	public static boolean getIsFormable(World world, int x, int y, int z, IMultiBlockStructure mbd)
	{
		int mi = 0, mj = 0, mk = 0;
		int[] tCoords = getStructureStartPoint(world, mbd.getSupportedBlockIDs(), x, y, z);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, mbd.getSupportedBlockIDs(), x, y, z);
		tx = tCoords[0];
		ty = tCoords[1];
		tz = tCoords[2];

		mi = tx - x + 1;
		mj = ty - y + 1;
		mk = tz - z + 1;
		if (getFormSizeType(world, x, y, z, mbd) > -1)
		{
			if (mi == mj && mj == mk && mk == 1)
			{
				return true;
			}
			if (checkIfOuterBoundIsOk(world, mbd.getSupportedBlockIDs(), x, y, z, tx, ty, tz))
			{
				return checkIsInnerBoundSolid(world, mbd.getSupportedBlockIDs(), x, y, z, tx, ty, tz);
			}
		}
		return false;
	}

	public static <T extends TileMultiBlockBase> T getFormedTileSoulStone(Class<T> claz, World world, ArrayList<Integer> supportedIDs, int x, int y, int z)
	{
		int[] tCoords = getStructureStartPoint(world, supportedIDs, x, y, z);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, supportedIDs, x, y, z);
		tx = tCoords[0];
		ty = tCoords[1];
		tz = tCoords[2];

		for (int i = x; i <= tx; i++)
		{
			for (int j = y; j <= ty; j++)
			{
				for (int k = z; k <= tz; k++)
				{
					TileEntity te = world.getBlockTileEntity(i, j, k);
					if (te != null && claz.isInstance(te))
						return claz.cast(te);
				}
			}
		}
		return null;
	}

	public static int[] getStructureStartPoint(World world, ArrayList<Integer> supportedIDs, int x, int y, int z)
	{
		int tx = x, ty = y, tz = z;

		while (supportedIDs.indexOf(world.getBlockId(tx - 1, y, z)) >= 0)
		{
			--tx;
		}
		while (supportedIDs.indexOf(world.getBlockId(x, ty - 1, z)) >= 0)
		{
			--ty;
		}
		while (supportedIDs.indexOf(world.getBlockId(x, y, tz - 1)) >= 0)
		{
			--tz;
		}
		return new int[] { tx, ty, tz };
	}

	public static int[] getStructureEndPoint(World world, ArrayList<Integer> supportedIDs, int x, int y, int z)
	{
		int tx = x, ty = y, tz = z;
		while (supportedIDs.indexOf(world.getBlockId(tx + 1, y, z)) >= 0)
		{
			++tx;
		}
		while (supportedIDs.indexOf(world.getBlockId(x, ty + 1, z)) >= 0)
		{
			++ty;
		}
		while (supportedIDs.indexOf(world.getBlockId(x, y, tz + 1)) >= 0)
		{
			++tz;
		}
		return new int[] { tx, ty, tz };
	}

	public static int getIndexOfFormSizes(int[][] formableSizes, int x, int y, int z)
	{
		for (int i = 0; i < formableSizes.length; i++)
		{
			if ((formableSizes[i][0] == x && formableSizes[i][1] == y && formableSizes[i][2] == z) || (formableSizes[i][0] == z && formableSizes[i][1] == y && formableSizes[i][2] == x))
			{
				return i;
			}
		}
		return -1;
	}

	public static int getFormSizeType(World world, int x, int y, int z, IMultiBlockStructure mbd)
	{
		int[][] sizes = mbd.getPossibleFormTypes();
		int[] size = getFormSize(world, mbd.getSupportedBlockIDs(), x, y, z);
		return getIndexOfFormSizes(sizes, size[0], size[1], size[2]);
	}

	public static int[] getFormSize(World world, ArrayList<Integer> supportedIDs, int x, int y, int z)
	{
		int[] result = new int[3];
		int[] tCoords = getStructureStartPoint(world, supportedIDs, x, y, z);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, supportedIDs, x, y, z);
		tx = tCoords[0];
		ty = tCoords[1];
		tz = tCoords[2];

		result[0] = tx - x + 1;
		result[1] = ty - y + 1;
		result[2] = tz - z + 1;
		return result;
	}

	public static int[] getAdjacentDummyBlockCoords(World world, ArrayList<Integer> supportedIDs, int x, int y, int z)
	{
		if (supportedIDs.indexOf(world.getBlockId(x - 1, y, z)) >= 0)
		{
			return new int[] { x - 1, y, z };
		}
		else if (supportedIDs.indexOf(world.getBlockId(x + 1, y, z)) >= 0)
		{
			return new int[] { x + 1, y, z };
		}
		else if (supportedIDs.indexOf(world.getBlockId(x, y - 1, z)) >= 0)
		{
			return new int[] { x, y - 1, z };
		}
		else if (supportedIDs.indexOf(world.getBlockId(x, y + 1, z)) >= 0)
		{
			return new int[] { x, y + 1, z };
		}
		else if (supportedIDs.indexOf(world.getBlockId(x, y, z - 1)) >= 0)
		{
			return new int[] { x, y, z - 1 };
		}
		else if (supportedIDs.indexOf(world.getBlockId(x, y, z + 1)) >= 0)
		{
			return new int[] { x, y, z + 1 };
		}
		else
			return null;
	}

	private static boolean checkIfOuterBoundIsOk(World world, ArrayList<Integer> supportedID, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		for (int i = minX; i <= maxX; i++)
		{
			for (int k = minZ; k <= maxZ; k++)
			{
				if (supportedID.indexOf(world.getBlockId(i, minY - 1, k)) >= 0 || supportedID.indexOf(world.getBlockId(i, maxY + 1, k)) >= 0)
				{
					return false;
				}
			}
		}
		for (int j = minY; j <= maxY; j++)
		{
			for (int k = minZ; k <= maxZ; k++)
			{
				if (supportedID.indexOf(world.getBlockId(minX - 1, j, k)) >= 0 || supportedID.indexOf(world.getBlockId(maxX + 1, j, k)) >= 0)
				{
					return false;
				}
			}
		}
		for (int j = minY; j <= maxY; j++)
		{
			for (int i = minX; i <= maxX; i++)
			{
				if (supportedID.indexOf(world.getBlockId(i, j, minZ - 1)) >= 0 || supportedID.indexOf(world.getBlockId(i, j, maxZ + 1)) >= 0)
				{
					return false;
				}
			}
		}
		return true;
	}

	private static boolean checkIsInnerBoundSolid(World world, ArrayList<Integer> supportedID, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		for (int i = minX; i <= maxX; i++)
		{
			for (int j = minY; j <= maxY; j++)
			{
				for (int k = minZ; k <= maxZ; k++)
				{
					if (supportedID.indexOf(world.getBlockId(i, j, k)) < 0)
						return false;
				}
			}
		}
		return true;
	}
}
