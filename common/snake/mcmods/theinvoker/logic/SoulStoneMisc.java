package snake.mcmods.theinvoker.logic;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;

public class SoulStoneMisc
{
    public static final int MAX_X_SIZE = 7;
    public static final int MAX_Y_SIZE = 3;
    public static final int[][] FORMABLE_SIZE = { { 1, 1, 1 }, { 2, 1, 2 }, { 3, 2, 4 }, { MAX_X_SIZE, MAX_Y_SIZE, 5 } };

    public static boolean getIsFormable(World world, int x, int y, int z)
    {
        int mi = 0, mj = 0, mk = 0;
        int[] tCoords = getStructureStartPoint(world, x, y, z);
        int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
        x = tx;
        y = ty;
        z = tz;
        tCoords = getStructureEndPoint(world,x,y,z);
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
        for (int[] arr : FORMABLE_SIZE)
        {
            if ((arr[0] == x && arr[1] == y && arr[2] == z) || (arr[0] == z && arr[1] == y && arr[2] == x))
            {
                return true;
            }
        }
        return false;
    }

    public static int[] getStructureSize(World world, int x, int y, int z)
    {
        int[] arr = new int[3];
        return arr;
    }

    public static TileSoulStone getFormedTileSoulStone(World world, int x, int y, int z)
    {
        int[] tCoords = getStructureStartPoint(world, x, y, z);
        int tx = tCoords[0], ty = tCoords[1], tz = tCoords[2];
        x = tx;
        y = ty;
        z = tz;
        tCoords = getStructureEndPoint(world,x,y,z);
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
                        return (TileSoulStone) te;
                }
            }
        }
        return null;
    }

    public static int[] getStructureStartPoint(World world, int x, int y, int z)
    {
        int tx = x, ty = y, tz = z;
        while (world.getBlockId(tx - 1, y, z) == TIBlocks.soulStoneDummy.blockID)
        {
            --tx;
        }
        while (world.getBlockId(x, ty - 1, z) == TIBlocks.soulStoneDummy.blockID)
        {
            --ty;
        }
        while (world.getBlockId(x, y, tz - 1) == TIBlocks.soulStoneDummy.blockID)
        {
            --tz;
        }
        return new int[] { tx, ty, tz };
    }
    
    public static int[] getStructureEndPoint(World world, int x, int y, int z)
    {
        int tx = x, ty = y, tz = z;
        while (world.getBlockId(tx + 1, y, z) == TIBlocks.soulStoneDummy.blockID)
        {
            ++tx;
        }
        while (world.getBlockId(x, ty + 1, z) == TIBlocks.soulStoneDummy.blockID)
        {
            ++ty;
        }
        while (world.getBlockId(x, y, tz + 1) == TIBlocks.soulStoneDummy.blockID)
        {
            ++tz;
        }
        return new int[] { tx, ty, tz };
    }

    private static boolean checkIfOuterBoundIsOk(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        for (int i = minX; i <= maxX; i++)
        {
            for (int k = minZ; k <= maxZ; k++)
            {
                if (world.getBlockId(i, minY - 1, k) == TIBlocks.soulStoneDummy.blockID || world.getBlockId(i, maxY + 1, k) == TIBlocks.soulStoneDummy.blockID)
                {
                    return false;
                }
            }
        }
        for (int j = minY; j <= maxY; j++)
        {
            for (int k = minZ; k <= maxZ; k++)
            {
                if (world.getBlockId(minX - 1, j, k) == TIBlocks.soulStoneDummy.blockID || world.getBlockId(maxX + 1, j, k) == TIBlocks.soulStoneDummy.blockID)
                {
                    return false;
                }
            }
        }
        for (int j = minY; j <= maxY; j++)
        {
            for (int i = minX; i <= maxX; i++)
            {
                if (world.getBlockId(i, j, minZ - 1) == TIBlocks.soulStoneDummy.blockID || world.getBlockId(i, j, maxZ + 1) == TIBlocks.soulStoneDummy.blockID)
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
                    if (world.getBlockId(i, j, k) != TIBlocks.soulStoneDummy.blockID)
                        return false;
                }
            }
        }
        return true;
    }
}
