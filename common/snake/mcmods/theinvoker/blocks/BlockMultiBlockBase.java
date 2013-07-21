package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.logic.MultiBlockStructureHelper;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;

public abstract class BlockMultiBlockBase extends BlockMultiBlockBaseDummy implements IMultiBlockStructure, ITileEntityProvider
{

	protected BlockMultiBlockBase(int par1, Material par2Material)
	{
		super(par1, par2Material);
		this.isBlockContainer = true;
	}

	public boolean onBlockEventReceived(World world, int x, int y, int z, int par5, int par6)
	{
		super.onBlockEventReceived(world, x, y, z, par5, par6);
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
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

		if (getIsPlayerHoldingStructureFormerItem(player))
		{
			if (!tmb.getIsFormless())
			{
				reform(tmb);
			}
			else
			{
				if (MultiBlockStructureHelper.getIsFormable(world, x, y, z, this))
				{
					reform(tmb);
				}
				else
				{
					onNotAbleToReform(world, x, y, z, player, side);
				}
			}
		}
		return onActivatedWithoutStructureFormerItem(world, x, y, z, player, side);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int metadata, int id)
	{
		super.breakBlock(world, x, y, z, metadata, id);
		world.removeBlockTileEntity(x, y, z);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileMultiBlockBase)
		{
			TileMultiBlockBase tmb = (TileMultiBlockBase)te;
			int[] dummyCoords = MultiBlockStructureHelper.getAdjacentDummyBlockCoords(world, getSupportedBlockIDs(), x, y, z);
			if (dummyCoords != null)
			{
				if (world.setBlock(dummyCoords[0], dummyCoords[1], dummyCoords[2], TIBlocks.soulStone.blockID, world.getBlockMetadata(x, y, z), 4))
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
		super.breakBlock(world, x, y, z, metadata, id);
	}
}
