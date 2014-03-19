package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.tileentities.TileTIBase;
import snake.mcmods.theinvoker.utils.Utils;

public abstract class Block2HeightBase extends BlockContainer
{

    public Block2HeightBase(Material m)
    {
        super(m);
    }

    protected int ghostBlockMetadata;

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return super.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return y >= 255 ? false : World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        TileTIBase te = (TileTIBase)world.getTileEntity(x, y, z);
//      te.setOwnerName(entityLiving.getEntityName());
        te.setDirection(Utils.getPlaceDirection(entityLiving));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata == ghostBlockMetadata)
        {
            // is ghost block, look for the totem blow it.
            if (world.getBlock(x, y - 1, z) != neighborBlock && neighborBlock == this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else
        {
            boolean drop = false;
            // is the actual block, look for the ghost block above it.
            if (shouldBreakWhenGhostBlockDestoryed(world, x, y, z, neighborBlock, metadata) && world.getBlock(x, y + 1, z) != this && neighborBlock == this && !world.isRemote)
            {
                drop = true;
                dropItem(world, x, y, z, neighborBlock, metadata);
            }
            if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z))
            {
                if (world.getBlock(x, y + 1, z) == this)
                {
                    world.setBlockToAir(x, y + 1, z);
                }
            }

            if (drop == true)
            {
                world.setBlockToAir(x, y, z);
            }

        }
    }

    protected boolean shouldBreakWhenGhostBlockDestoryed(World world, int x, int y, int z, Block neighborBlock, int metadata)
    {
        return true;
    }

    protected abstract void dropItem(World world, int x, int y, int z, Block neighborBlock, int metadata);
}
