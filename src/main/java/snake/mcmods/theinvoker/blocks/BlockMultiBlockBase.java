package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.Block;
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

	protected BlockMultiBlockBase(Material par2Material)
	{
		super(par2Material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if (player.getHeldItem() != null && getSupportedBlocks().indexOf(null) >= 0)
			return false;

		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null)
			return false;

		TileMultiBlockBase tmb = (TileMultiBlockBase)te;

		if (MultiBlockStructureHelper.getIsPlayerHoldingStructureFormerItem(this, player))
		{
			if (!tmb.getIsFormless())
			{
				MultiBlockStructureHelper.reformStructure(this, tmb, getSupportedBlocks());
			}
			else
			{
				if (MultiBlockStructureHelper.getIsFormable(world, x, y, z, this))
				{
					MultiBlockStructureHelper.reformStructure(this, tmb, getSupportedBlocks());
				}
				else
				{
					onNotAbleToReform(world, x, y, z, player, side);
				}
			}
			return true;
		}
		return onActivatedWithoutStructureFormerItem(world, x, y, z, player, side);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileMultiBlockBase)
		{
			TileMultiBlockBase tmb = (TileMultiBlockBase)te;
			tmb.setIsFormless(true);
			int[] dummyCoords = MultiBlockStructureHelper.getAdjacentDummyBlockCoords(world, getSupportedBlocks(), x, y, z, metadata);
			if (dummyCoords != null)
			{
				tmb.setBeginTransfer();
				if (world.setBlockToAir(dummyCoords[0], dummyCoords[1], dummyCoords[2])&&
						world.setBlock(dummyCoords[0], dummyCoords[1], dummyCoords[2], this, metadata, 2))
				{
					TileEntity teNew = world.getTileEntity(dummyCoords[0], dummyCoords[1], dummyCoords[2]);
					if (teNew != null && teNew instanceof TileMultiBlockBase)
					{
						TileMultiBlockBase tmbNew = (TileMultiBlockBase)teNew;
						tmbNew.transferFrom(tmb);
						tmbNew.setIsFormless(true);
					}
				}
				tmb.setEndTransfer();
			}
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}
}
