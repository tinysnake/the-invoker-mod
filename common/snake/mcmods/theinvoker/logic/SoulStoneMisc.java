package snake.mcmods.theinvoker.logic;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.entities.EntitySoulStoneMonitor;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;

public class SoulStoneMisc
{
	public static final int MAX_X_SIZE = 7;
	public static final int MAX_Y_SIZE = 3;
	public static final int[][] FORMABLE_SIZE = { { 1, 1, 1 }, { 2, 1, 2 }, { 3, 2, 4 }, { MAX_X_SIZE, MAX_Y_SIZE, 5 } };
	public static final int[] CAPACITY_OF_METADATA = { 4000, 16000, 96000, 420000 };
	public static final int EFFECTIVE_RANGE = 16;

	public static boolean getIsFormable(World world, int x, int y, int z)
	{
		int mi = 0, mj = 0, mk = 0;
		int[] tCoords = getStructureStartPoint(world, x, y, z);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, x, y, z);
		tx = tCoords[0];
		ty = tCoords[1];
		tz = tCoords[2];

		mi = tx - x + 1;
		mj = ty - y + 1;
		mk = tz - z + 1;
		if (getIsSizeFormable(mi, mj, mk))
		{
			if (mi == mj && mj == mk && mk == 1)
			{
				return true;
			}
			if (checkIfOuterBoundIsOk(world, x, y, z, tx, ty, tz))
			{
				return checkIsInnerBoundSolid(world, x, y, z, tx, ty, tz);
			}
		}
		return false;
	}

	public static boolean getIsSizeFormable(int x, int y, int z)
	{
		return getMetadataOfFormSize(x, y, z) > -1;
	}

	public static TileSoulStone getFormedTileSoulStone(World world, int x, int y, int z)
	{
		int[] tCoords = getStructureStartPoint(world, x, y, z);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, x, y, z);
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
					if (te != null && te instanceof TileSoulStone)
						return (TileSoulStone)te;
				}
			}
		}
		return null;
	}

	public static int[] getStructureStartPoint(World world, int x, int y, int z)
	{
		int tx = x, ty = y, tz = z;
		while (getIsSoulStoneID(world.getBlockId(tx - 1, y, z)))
		{
			--tx;
		}
		while (getIsSoulStoneID(world.getBlockId(x, ty - 1, z)))
		{
			--ty;
		}
		while (getIsSoulStoneID(world.getBlockId(x, y, tz - 1)))
		{
			--tz;
		}
		return new int[] { tx, ty, tz };
	}

	public static int[] getStructureEndPoint(World world, int x, int y, int z)
	{
		int tx = x, ty = y, tz = z;
		while (getIsSoulStoneID(world.getBlockId(tx + 1, y, z)))
		{
			++tx;
		}
		while (getIsSoulStoneID(world.getBlockId(x, ty + 1, z)))
		{
			++ty;
		}
		while (getIsSoulStoneID(world.getBlockId(x, y, tz + 1)))
		{
			++tz;
		}
		return new int[] { tx, ty, tz };
	}

	public static boolean getIsSoulStoneID(int id)
	{
		return id == TIBlocks.soulStoneDummy.blockID || id == TIBlocks.soulStone.blockID;
	}

	public static int getMetadataOfFormSize(int x, int y, int z)
	{
		for (int i = 0; i < FORMABLE_SIZE.length; i++)
		{
			if ((FORMABLE_SIZE[i][0] == x && FORMABLE_SIZE[i][1] == y && FORMABLE_SIZE[i][2] == z) || (FORMABLE_SIZE[i][0] == z && FORMABLE_SIZE[i][1] == y && FORMABLE_SIZE[i][2] == x))
			{
				return i;
			}
		}
		return -1;
	}

	public static int getMetadataOfFormSize(World world, int x, int y, int z)
	{
		int[] size = getFormSize(world, x, y, z);
		return getMetadataOfFormSize(size[0], size[1], size[2]);
	}

	public static int[] getFormSize(World world, int x, int y, int z)
	{
		int[] result = new int[3];
		int[] tCoords = getStructureStartPoint(world, x, y, z);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, x, y, z);
		tx = tCoords[0];
		ty = tCoords[1];
		tz = tCoords[2];

		result[0] = tx - x + 1;
		result[1] = ty - y + 1;
		result[2] = tz - z + 1;
		return result;
	}

	public static void spawnSoulStoneMonitor(World world, int x, int y, int z, int side)
	{
		if (world.isRemote)
		{
			TileSoulStone tss = SoulStoneMisc.getFormedTileSoulStone(world, x, y, z);
			if (tss != null)
			{
				if (tss.getIsFormless())
					return;
				EntitySoulStoneMonitor essm = new EntitySoulStoneMonitor(world, tss);
				essm.setupLifeSpan();
				int xOff = 0, yOff = 0, zOff = 0;
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				switch (dir)
				{
					case DOWN:
						yOff = -1;
						break;
					case EAST:
						xOff = 1;
						break;
					case NORTH:
						zOff = -1;
						break;
					case SOUTH:
						zOff = 1;
						break;
					case UP:
						yOff = 1;
						break;
					case WEST:
						xOff = -1;
						break;
					default:
						break;

				}
				essm.posX = x + xOff;
				essm.posY = y + yOff;
				essm.posZ = z + zOff;
				essm.setDirection(dir);
				world.spawnEntityInWorld(essm);
			}
		}
	}

	public static int[] getAdjacentDummyBlockCoords(World world, int x, int y, int z)
	{
		if (world.getBlockId(x - 1, y, z) == TIBlocks.soulStoneDummy.blockID)
		{
			return new int[] { x - 1, y, z };
		}
		else if (world.getBlockId(x + 1, y, z) == TIBlocks.soulStoneDummy.blockID)
		{
			return new int[] { x + 1, y, z };
		}
		else if (world.getBlockId(x, y - 1, z) == TIBlocks.soulStoneDummy.blockID)
		{
			return new int[] { x, y - 1, z };
		}
		else if (world.getBlockId(x, y + 1, z) == TIBlocks.soulStoneDummy.blockID)
		{
			return new int[] { x, y + 1, z };
		}
		else if (world.getBlockId(x, y, z - 1) == TIBlocks.soulStoneDummy.blockID)
		{
			return new int[] { x, y, z - 1 };
		}
		else if (world.getBlockId(x, y, z + 1) == TIBlocks.soulStoneDummy.blockID)
		{
			return new int[] { x, y, z + 1 };
		}
		else
			return null;
	}

	private static boolean checkIfOuterBoundIsOk(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		for (int i = minX; i <= maxX; i++)
		{
			for (int k = minZ; k <= maxZ; k++)
			{
				if (getIsSoulStoneID(world.getBlockId(i, minY - 1, k)) || getIsSoulStoneID(world.getBlockId(i, maxY + 1, k)))
				{
					return false;
				}
			}
		}
		for (int j = minY; j <= maxY; j++)
		{
			for (int k = minZ; k <= maxZ; k++)
			{
				if (getIsSoulStoneID(world.getBlockId(minX - 1, j, k)) || getIsSoulStoneID(world.getBlockId(maxX + 1, j, k)))
				{
					return false;
				}
			}
		}
		for (int j = minY; j <= maxY; j++)
		{
			for (int i = minX; i <= maxX; i++)
			{
				if (getIsSoulStoneID(world.getBlockId(i, j, minZ - 1)) || getIsSoulStoneID(world.getBlockId(i, j, maxZ + 1)))
				{
					return false;
				}
			}
		}
		return true;
	}

	private static boolean checkIsInnerBoundSolid(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		for (int i = minX; i <= maxX; i++)
		{
			for (int j = minY; j <= maxY; j++)
			{
				for (int k = minZ; k <= maxZ; k++)
				{
					if (!getIsSoulStoneID(world.getBlockId(i, j, k)))
						return false;
				}
			}
		}
		return true;
	}
}
