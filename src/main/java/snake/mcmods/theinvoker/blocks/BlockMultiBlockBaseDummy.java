package snake.mcmods.theinvoker.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.logic.MultiBlockStructureHelper;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMultiBlockBaseDummy extends BlockMultiBlockBase
{
	public BlockMultiBlockBaseDummy(Material par2Material, BlockMultiBlockBase realBlock)
	{
		super(par2Material);
		this.realBlock = realBlock;
	}

	protected BlockMultiBlockBase realBlock;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + Utils.getTruelyUnlocalizedName(this));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if (player.getHeldItem() != null && getSupportedBlocks().indexOf(player.getHeldItem().getItem()) >= 0)
			return false;
		if (MultiBlockStructureHelper.getIsPlayerHoldingStructureFormerItem(this, player))
		{
			TileMultiBlockBase tmb = MultiBlockStructureHelper.getFormedMultiBlockTileEntity(TileMultiBlockBase.class, world, getSupportedBlocks(), x, y, z, world.getBlockMetadata(x, y, z));
			if (tmb != null)
			{
				if (tmb.getIsFormless())
				{
					if (MultiBlockStructureHelper.getIsFormable(world, x, y, z, this))
					{
						MultiBlockStructureHelper.reformStructure(this, tmb, getSupportedBlocks());
					}
					else
					{
						if (world.isRemote)
						{
//							player.addChatComponentMessage(Lang.getLocalizedStr(LangKeys.TEXT_SOUL_STONE_NOT_FORMABLE));
						}
						onNotAbleToReform(world, x, y, z, player, side);
					}
				}
				else
				{
					MultiBlockStructureHelper.reformStructure(this, tmb, getSupportedBlocks());
				}
			}
			else if (MultiBlockStructureHelper.getIsFormable(world, x, y, z, this))
			{
				world.setBlockToAir(x, y, z);
				world.setBlock(x, y, z, realBlock, world.getBlockMetadata(x, y, z), 2);
				setupTileEntity(world, x, y, z, player);
				tmb = (TileSoulStone)world.getTileEntity(x, y, z);
				int[] startCoords = MultiBlockStructureHelper.getStructureStartPoint(tmb.getWorldObj(), this.getSupportedBlocks(), tmb.xCoord, tmb.yCoord, tmb.zCoord, world.getBlockMetadata(x, y, z));
				tmb.setOriginCoords(startCoords[0], startCoords[1], startCoords[2]);
				onFormed(tmb);
			}
			else
			{
				onNotAbleToForm(world, x, y, z, player, side);
			}
			return true;
		}
		return onActivatedWithoutStructureFormerItem(world, x, y, z, player, side);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileMultiBlockBase tmb = MultiBlockStructureHelper.FindFormedMultiBlockTileEntity(world, getSupportedBlocks(), x, y, z, metadata);
		// TileMultiBlockBase tmb =
		// MultiBlockStructureHelper.getFormedMultiBlockTileEntity(TileMultiBlockBase.class,
		// world, getSupportedBlockIDs(), x, y, z, metadata);
		if (tmb != null && !tmb.getIsTransforming())
		{
			tmb.setIsFormless(true);
		}
		if (hasTileEntity(metadata) && !(this instanceof BlockContainer))
		{
			world.removeTileEntity(x, y, z);
		}
		world.removeTileEntity(x, y, z);
	}

	@Override
	public int[][] getPossibleFormTypes()
	{
		return realBlock.getPossibleFormTypes();
	}

	@Override
	public int[] getMaximumSize()
	{
		return realBlock.getMaximumSize();
	}

	@Override
	public int[] getMinimumSize()
	{
		return realBlock.getMinimumSize();
	}

	@Override
	public boolean getIsFreeSized()
	{
		return realBlock.getIsFreeSized();
	}

	@Override
	public ArrayList<Block> getSupportedBlocks()
	{
		return realBlock.getSupportedBlocks();
	}

	@Override
	public Item getStructureFormerItem()
	{
		return realBlock.getStructureFormerItem();
	}

	@Override
	public void onReformed(TileMultiBlockBase tmb)
	{
		realBlock.onReformed(tmb);
	}

	@Override
	public void onFormed(TileMultiBlockBase tmb)
	{
		realBlock.onFormed(tmb);
	}

	@Override
	public void onNotAbleToReform(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		realBlock.onNotAbleToReform(world, x, y, z, player, side);
	}

	@Override
	public void onNotAbleToForm(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		realBlock.onNotAbleToForm(world, x, y, z, player, side);
	}

	@Override
	public boolean onActivatedWithoutStructureFormerItem(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		return realBlock.onActivatedWithoutStructureFormerItem(world, x, y, z, player, side);
	}

	protected abstract void setupTileEntity(World world, int x, int y, int z, EntityPlayer player);
}
