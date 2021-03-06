package snake.mcmods.theinvoker.logic;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.blocks.IMultiBlockStructure;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;

public class MultiBlockStructureHelper
{

	public static boolean getIsFormable(World world, int x, int y, int z, IMultiBlockStructure mbd)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		int mi = 0, mj = 0, mk = 0;
		int[] tCoords = getStructureStartPoint(world, mbd.getSupportedBlockIDs(), x, y, z, metadata);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, mbd.getSupportedBlockIDs(), x, y, z, metadata);
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
			if (checkIfOuterBoundIsOk(world, mbd.getSupportedBlockIDs(), x, y, z, tx, ty, tz, metadata))
			{
				return checkIsInnerBoundSolid(world, mbd.getSupportedBlockIDs(), x, y, z, tx, ty, tz, metadata);
			}
		}
		return false;
	}

	public static <T extends TileMultiBlockBase> T getFormedMultiBlockTileEntity(Class<T> claz, World world, ArrayList<Integer> supportedIDs, int x, int y, int z, int metadata)
	{
		int[] tCoords = getStructureStartPoint(world, supportedIDs, x, y, z, metadata);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, supportedIDs, x, y, z, metadata);
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

	public static int[] getStructureStartPoint(World world, ArrayList<Integer> supportedIDs, int x, int y, int z, int metadata)
	{
		int tx = x, ty = y, tz = z;

		while (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(tx - 1, y, z), world.getBlockMetadata(tx - 1, y, z)))
		{
			--tx;
		}
		while (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, ty - 1, z), world.getBlockMetadata(x, ty - 1, z)))
		{
			--ty;
		}
		while (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, y, tz - 1), world.getBlockMetadata(x, y, tz - 1)))
		{
			--tz;
		}
		return new int[] { tx, ty, tz };
	}

	public static int[] getStructureEndPoint(World world, ArrayList<Integer> supportedIDs, int x, int y, int z, int metadata)
	{
		int tx = x, ty = y, tz = z;
		while (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(tx + 1, y, z), world.getBlockMetadata(tx + 1, y, z)))
		{
			++tx;
		}
		while (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, ty + 1, z), world.getBlockMetadata(x, ty + 1, z)))
		{
			++ty;
		}
		while (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, y, tz + 1), world.getBlockMetadata(x, y, tz + 1)))
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
		int[] size = getFormSize(world, mbd.getSupportedBlockIDs(), x, y, z, world.getBlockMetadata(x, y, z));
		return getIndexOfFormSizes(sizes, size[0], size[1], size[2]);
	}

	public static int[] getFormSize(World world, ArrayList<Integer> supportedIDs, int x, int y, int z, int metadata)
	{
		int[] result = new int[3];
		int[] tCoords = getStructureStartPoint(world, supportedIDs, x, y, z, metadata);
		int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
		x = tx;
		y = ty;
		z = tz;
		tCoords = getStructureEndPoint(world, supportedIDs, x, y, z, metadata);
		tx = tCoords[0];
		ty = tCoords[1];
		tz = tCoords[2];

		result[0] = tx - x + 1;
		result[1] = ty - y + 1;
		result[2] = tz - z + 1;
		return result;
	}

	public static int[] getAdjacentDummyBlockCoords(World world, ArrayList<Integer> supportedIDs, int x, int y, int z, int metadata)
	{
		if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x - 1, y, z), world.getBlockMetadata(x - 1, y, z)))
		{
			return new int[] { x - 1, y, z };
		}
		else if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x + 1, y, z), world.getBlockMetadata(x + 1, y, z)))
		{
			return new int[] { x + 1, y, z };
		}
		else if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, y - 1, z), world.getBlockMetadata(x, y - 1, z)))
		{
			return new int[] { x, y - 1, z };
		}
		else if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, y + 1, z), world.getBlockMetadata(x, y + 1, z)))
		{
			return new int[] { x, y + 1, z };
		}
		else if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, y, z - 1), world.getBlockMetadata(x, y, z - 1)))
		{
			return new int[] { x, y, z - 1 };
		}
		else if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(x, y, z + 1), world.getBlockMetadata(x, y, z + 1)))
		{
			return new int[] { x, y, z + 1 };
		}
		else
			return null;
	}

	public static void reformStructure(IMultiBlockStructure imbs, TileMultiBlockBase tmb, ArrayList<Integer> supportedIDs)
	{
		int[] startCoords = MultiBlockStructureHelper.getStructureStartPoint(tmb.worldObj, supportedIDs, tmb.xCoord, tmb.yCoord, tmb.zCoord, tmb.worldObj.getBlockMetadata(tmb.xCoord, tmb.yCoord, tmb.zCoord));
		tmb.setOriginCoords(startCoords[0], startCoords[1], startCoords[2]);
		tmb.setIsFormless(false);
		imbs.onReformed(tmb);
	}

	public static boolean getIsPlayerHoldingStructureFormerItem(IMultiBlockStructure imbs, EntityPlayer player)
	{
		if ((imbs.getStructureFormerItem() == null && player.getHeldItem() == null) ||
		        (player.getHeldItem() != null && player.getHeldItem().itemID == imbs.getStructureFormerItem().itemID))
			return true;
		return false;
	}

	public static TileMultiBlockBase FindFormedMultiBlockTileEntity(World world, ArrayList<Integer> supportedBlockIDs, int x, int y, int z, int metadata)
	{
		int tx = x;
		int ty = y;
		int tz = z;
		int minX = x;
		int minY = y;
		int minZ = z;
		int maxX = x;
		int maxY = y;
		int maxZ = z;
		do
		{
			maxX = ++tx;
		}
		while (checkIsBlockSupported(supportedBlockIDs, metadata, world.getBlockId(tx, y, z), world.getBlockMetadata(tx, y, z)));
		do
		{
			maxY = ++ty;
		}
		while (checkIsBlockSupported(supportedBlockIDs, metadata, world.getBlockId(x, ty, z), world.getBlockMetadata(x, ty, z)));
		do
		{
			maxZ = ++tz;
		}
		while (checkIsBlockSupported(supportedBlockIDs, metadata, world.getBlockId(x, y, tz), world.getBlockMetadata(x, y, tz)));
		tx = x;
		ty = y;
		tz = z;
		do
		{
			minX = --tx;
		}
		while (checkIsBlockSupported(supportedBlockIDs, metadata, world.getBlockId(tx, y, z), world.getBlockMetadata(tx, y, z)));
		do
		{
			minY = --ty;
		}
		while (checkIsBlockSupported(supportedBlockIDs, metadata, world.getBlockId(x, ty, z), world.getBlockMetadata(x, ty, z)));
		do
		{
			minZ = --tz;
		}
		while (checkIsBlockSupported(supportedBlockIDs, metadata, world.getBlockId(x, y, tz), world.getBlockMetadata(x, y, tz)));
		for (tx = minX + 1; tx < maxX; tx++)
		{
			for (ty = minY + 1; ty < maxY; ty++)
			{
				for (tz = minZ + 1; tz < maxZ; tz++)
				{
					TileEntity te = world.getBlockTileEntity(tx, ty, tz);
					if(te!=null && te instanceof TileMultiBlockBase)
						return (TileMultiBlockBase)te;
				}
			}
		}
		return null;
	}

	private static boolean checkIfOuterBoundIsOk(World world, ArrayList<Integer> supportedIDs, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int metadata)
	{
		for (int i = minX; i <= maxX; i++)
		{
			for (int k = minZ; k <= maxZ; k++)
			{
				if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(i, minY - 1, k), world.getBlockMetadata(i, minY - 1, k)) ||
				        checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(i, maxY + 1, k), world.getBlockMetadata(i, maxY + 1, k)))
				{
					return false;
				}
			}
		}
		for (int j = minY; j <= maxY; j++)
		{
			for (int k = minZ; k <= maxZ; k++)
			{
				if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(minX - 1, j, k), world.getBlockMetadata(minX - 1, j, k)) ||
				        checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(maxX + 1, j, k), world.getBlockMetadata(minX + 1, j, k)))
				{
					return false;
				}
			}
		}
		for (int j = minY; j <= maxY; j++)
		{
			for (int i = minX; i <= maxX; i++)
			{
				if (checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(i, j, minZ - 1), world.getBlockMetadata(i, j, minZ - 1)) ||
				        checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(i, j, maxZ + 1), world.getBlockMetadata(i, j, maxZ + 1)))
				{
					return false;
				}
			}
		}
		return true;
	}

	private static boolean checkIsInnerBoundSolid(World world, ArrayList<Integer> supportedIDs, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int metadata)
	{
		for (int i = minX; i <= maxX; i++)
		{
			for (int j = minY; j <= maxY; j++)
			{
				for (int k = minZ; k <= maxZ; k++)
				{
					if (!checkIsBlockSupported(supportedIDs, metadata, world.getBlockId(i, j, k), world.getBlockMetadata(i, j, k)))
						return false;
				}
			}
		}
		return true;
	}

	private static boolean checkIsBlockSupported(ArrayList<Integer> supportedIDs, int metadata, int blockID, int blockMeta)
	{
		return supportedIDs.indexOf(blockID) >= 0 && blockMeta == metadata;
	}
}
