package snake.mcmods.theinvoker.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.lib.constants.LangKeys;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.MultiBlockStructureHelper;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulStoneDummy extends BlockMultiBlockBaseDummy
{
	
	public BlockSoulStoneDummy(int id)
	{
		super(id, Material.iron);
		this.setCreativeTab(TheInvoker.tab);
		this.setUnlocalizedName(TIName.BLOCK_SOUL_STONE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + Utils.getTruelyUnlocalizedName(TIBlocks.soulStone));
	}

	@Override
	public int[][] getPossibleFormTypes()
	{
		return TIBlocks.soulStone.getPossibleFormTypes();
	}

	@Override
	public int[] getMaximumSize()
	{
		return null;
	}

	@Override
	public int[] getMinimumSize()
	{
		return null;
	}

	@Override
	public boolean getIsFreeSized()
	{
		return false;
	}

	@Override
	public ArrayList<Integer> getSupportedBlockIDs()
	{
		return TIBlocks.soulStone.getSupportedBlockIDs();
	}

	@Override
	public Item getStructureFormerItem()
	{
		return TIBlocks.soulStone.getStructureFormerItem();
	}

	@Override
	protected void onReformed(TileMultiBlockBase tmb)
	{
		onFormed(tmb);
	}

	@Override
	protected void onFormed(TileMultiBlockBase tmb)
	{
		int index = MultiBlockStructureHelper.getIndexOfFormSizes(BlockSoulStone.FORMABLE_SIZES, tmb.xCoord, tmb.yCoord, tmb.zCoord);
		if(index>-1)
		{
			((TileSoulStone)tmb).getEnergyContainer().setEnergyCapacity(BlockSoulStone.CAPACITY_OF_METADATA[index]);
		}
		tmb.worldObj.spawnParticle("bigexplosion", tmb.xCoord, tmb.yCoord, tmb.zCoord, 0, 0, 0);
	}

	@Override
	protected void onNotAbleToReform(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		onNotAbleToForm(world, x, y, z, player, side);
	}

	@Override
	protected void onNotAbleToForm(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		if (world.isRemote)
			player.addChatMessage(Lang.getLocalizedStr(LangKeys.TEXT_SOUL_STONE_NOT_FORMABLE));
	}

	@Override
	protected boolean onActivatedWithoutStructureFormerItem(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		TIBlocks.soulStone.spawnSoulStoneMonitor(world, x, y, z, side);
		return true;
	}
}
