package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.lib.constants.LangKeys;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.logic.MultiBlockStructureHelper;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMultiBlockBaseDummy extends Block implements IMultiBlockStructure
{

	public BlockMultiBlockBaseDummy(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + Utils.getTruelyUnlocalizedName(this));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if (player.getHeldItem() != null && getSupportedBlockIDs().indexOf(blockID) >= 0)
			return false;
		if (getIsPlayerHoldingStructureFormerItem(player))
		{
			TileMultiBlockBase tmb = MultiBlockStructureHelper.getFormedTileSoulStone(TileMultiBlockBase.class, world, getSupportedBlockIDs(), x, y, z);
			if (tmb != null)
			{
				if (tmb.getIsFormless())
				{
					if (MultiBlockStructureHelper.getIsFormable(world, x, y, z, this))
					{
						reform(tmb);
					}
					else
					{
						if (world.isRemote)
							player.addChatMessage(Lang.getLocalizedStr(LangKeys.TEXT_SOUL_STONE_NOT_FORMABLE));
						onNotAbleToReform(world, x, y, z, player, side);
					}
				}
				else
				{
					reform(tmb);
				}
			}
			else if (MultiBlockStructureHelper.getIsFormable(world, x, y, z, this))
			{
				TIBlocks.soulStone.setupTileEntity(world, x, y, z, player.getEntityName());
				tmb = (TileSoulStone)world.getBlockTileEntity(x, y, z);
				int[] startCoords = MultiBlockStructureHelper.getStructureStartPoint(tmb.worldObj, this.getSupportedBlockIDs(), tmb.xCoord, tmb.yCoord, tmb.zCoord);
				tmb.setOriginCoords(startCoords[0], startCoords[1], startCoords[2]);
				onFormed(tmb);
			}
			else
			{
				onNotAbleToForm(world, x, y, z, player, side);
			}
		}
		return onActivatedWithoutStructureFormerItem(world, x, y, z, player, side);
	}

	protected boolean getIsPlayerHoldingStructureFormerItem(EntityPlayer player)
	{
		if ((getStructureFormerItem() == null && player.getHeldItem() == null) ||
				(player.getHeldItem() != null && player.getHeldItem().itemID == getStructureFormerItem().itemID))
			return true;
		return false;
	}

	protected void reform(TileMultiBlockBase tmb)
	{
		int[] startCoords = MultiBlockStructureHelper.getStructureStartPoint(tmb.worldObj, getSupportedBlockIDs(), tmb.xCoord, tmb.yCoord, tmb.zCoord);
		tmb.setOriginCoords(startCoords[0], startCoords[1], startCoords[2]);
		tmb.setIsFormless(false);
		onReformed(tmb);
	}

	protected abstract void onReformed(TileMultiBlockBase tmb);

	protected abstract void onFormed(TileMultiBlockBase tmb);

	protected abstract void onNotAbleToReform(World world, int x, int y, int z, EntityPlayer player, int side);

	protected abstract void onNotAbleToForm(World world, int x, int y, int z, EntityPlayer player, int side);

	protected abstract boolean onActivatedWithoutStructureFormerItem(World world, int x, int y, int z, EntityPlayer player, int side);
}
