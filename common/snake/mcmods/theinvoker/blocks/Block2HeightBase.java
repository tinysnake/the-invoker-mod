package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.tileentities.TileTIBase;

public abstract class Block2HeightBase extends BlockContainer
{

	public Block2HeightBase(int id, Material m)
	{
		super(id, m);
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
		return y >= 255 ? false : world.doesBlockHaveSolidTopSurface(x, y - 1, z)
		        && super.canPlaceBlockAt(world, x, y, z)
		        && super.canPlaceBlockAt(world, x, y + 1, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving,
	        ItemStack itemStack)
	{
		TileTIBase te = (TileTIBase) world.getBlockTileEntity(x, y, z);
		te.setOwnerName(entityLiving.getEntityName());
		int face = MathHelper
		        .floor_double((double) (entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		switch (face)
		{
			case 0:
				te.setDirection(2);
				break;
			case 1:
				te.setDirection(5);
				break;
			case 2:
				te.setDirection(3);
				break;
			case 3:
				te.setDirection(4);
				break;
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID)
	{
		int metadata = world.getBlockMetadata(x, y, z);

		if (metadata == ghostBlockMetadata)
		{
			// is ghost block, look for the totem blow it.
			if (world.getBlockId(x, y - 1, z) != this.blockID && neighborBlockID == this.blockID)
			{
				world.setBlockToAir(x, y, z);
			}
		}
		else
		{
			boolean drop = false;
			// is the actual block, look for the ghost block above it.
			if (shouldBreakWhenGhostBlockDestoryed(world, x, y, z, neighborBlockID, metadata)
			        && world.getBlockId(x, y + 1, z) != this.blockID
			        && neighborBlockID == this.blockID && !world.isRemote)
			{
				drop = true;
				dropItem(world, x, y, z, neighborBlockID, metadata);
			}
			if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z))
			{
				if (world.getBlockId(x, y + 1, z) == this.blockID)
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

	protected boolean shouldBreakWhenGhostBlockDestoryed(World world, int x, int y, int z,
	        int neighborBlockID, int metadata)
	{
		return true;
	}

	protected abstract void dropItem(World world, int x, int y, int z, int neighborBlockID,
	        int metadata);
}
