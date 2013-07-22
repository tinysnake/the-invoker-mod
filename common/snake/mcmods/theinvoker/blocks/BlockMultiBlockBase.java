package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.logic.MultiBlockStructureHelper;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;

public abstract class BlockMultiBlockBase extends BlockContainer implements IMultiBlockStructure, ITileEntityProvider
{

	protected BlockMultiBlockBase(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if (player.getHeldItem() != null && getSupportedBlockIDs().indexOf(blockID) >= 0)
			return false;

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null)
			return false;

		TileMultiBlockBase tmb = (TileMultiBlockBase)te;

		if (MultiBlockStructureHelper.getIsPlayerHoldingStructureFormerItem(this, player))
		{
			if (!tmb.getIsFormless())
			{
				MultiBlockStructureHelper.reformStructure(this, tmb, getSupportedBlockIDs());
			}
			else
			{
				if (MultiBlockStructureHelper.getIsFormable(world, x, y, z, this))
				{
					MultiBlockStructureHelper.reformStructure(this, tmb, getSupportedBlockIDs());
				}
				else
				{
					onNotAbleToReform(world, x, y, z, player, side);
				}
			}
		}
		else
		{
			return onActivatedWithoutStructureFormerItem(world, x, y, z, player, side);
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int metadata, int id)
	{
		TileMultiBlockBase tmb = MultiBlockStructureHelper.getFormedMultiBlockTileEntity(TileMultiBlockBase.class, world, getSupportedBlockIDs(), x, y, z, metadata);
		if(tmb!=null)
		{
			if(tmb.xCoord!=x||tmb.yCoord!=y||tmb.zCoord!=z)
			{
				tmb.setIsFormless(true);
			}
			else
			{
				int[] dummyCoords = MultiBlockStructureHelper.getAdjacentDummyBlockCoords(world, getSupportedBlockIDs(), x, y, z, metadata);
				if (dummyCoords != null)
				{
					if (world.setBlock(dummyCoords[0], dummyCoords[1], dummyCoords[2], blockID, world.getBlockMetadata(x, y, z), 2))
					{
						TileEntity teNew = world.getBlockTileEntity(dummyCoords[0], dummyCoords[1], dummyCoords[2]);
						if (teNew != null && teNew instanceof TileMultiBlockBase)
						{
							TileMultiBlockBase tmbNew = (TileMultiBlockBase)teNew;
							tmbNew.transferFrom(tmb);
							tmbNew.setIsFormless(true);
						}
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, metadata, id);
	}
}
