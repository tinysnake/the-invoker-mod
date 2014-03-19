package snake.mcmods.theinvoker.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.entities.EntityElemPillarMonitor;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.LangKeys;
import snake.mcmods.theinvoker.logic.MultiBlockStructureHelper;
import snake.mcmods.theinvoker.tileentities.TileElemPillar;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;

public class BlockElemPillar extends BlockMultiBlockBase
{
	public static final int[][] FORMABLE_SIZES = new int[][] { new int[] { 1, 1, 1 }, new int[] { 1, 2, 1 }, new int[] { 2, 3, 2 }, new int[] { 2, 8, 2 } };

	public static final int[] CAPACITY_OF_SIZES = new int[] { 50, 100, 600, 1600 };

	protected BlockElemPillar()
	{
		super(Material.iron);
	}

	private ArrayList<Block> supportedBlocks;

	@Override
	public int[][] getPossibleFormTypes()
	{
		return FORMABLE_SIZES;
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
	public ArrayList<Block> getSupportedBlocks()
	{
		if (supportedBlocks == null)
		{
			supportedBlocks = new ArrayList<Block>();
			supportedBlocks.add(this);
			supportedBlocks.add(TIBlocks.elemPillarDummy);
		}
		return supportedBlocks;
	}

	@Override
	public Item getStructureFormerItem()
	{
		return TIItems.evilTouch;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new TileElemPillar();
	}

	@Override
	public void onReformed(TileMultiBlockBase tmb)
	{
		onFormed(tmb);
	}

	@Override
	public void onFormed(TileMultiBlockBase tmb)
	{
		int index = MultiBlockStructureHelper.getIndexOfFormSizes(BlockSoulStone.FORMABLE_SIZES, tmb.xCoord, tmb.yCoord, tmb.zCoord);
		if (index > -1)
		{
			((TileElemPillar)tmb).getEnergyContainer().setEnergyCapacity(BlockSoulStone.CAPACITY_OF_SIZES[index]);
		}
		tmb.getWorldObj().spawnParticle("bigexplosion", tmb.xCoord, tmb.yCoord, tmb.zCoord, 0, 0, 0);
	}

	@Override
	public void onNotAbleToReform(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		if (world.isRemote)
		{
//			player.addChatMessage(Lang.getLocalizedStr(LangKeys.TEXT_SOUL_STONE_NOT_FORMABLE));
		}
	}

	@Override
	public void onNotAbleToForm(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		if (world.isRemote)
		{
//			player.addChatMessage(Lang.getLocalizedStr(LangKeys.TEXT_SOUL_STONE_NOT_FORMABLE));
		}
	}

	@Override
	public boolean onActivatedWithoutStructureFormerItem(World world, int x, int y, int z, EntityPlayer player, int side)
	{
		spawnSoulStoneMonitor(world, x, y, z, side);
		return true;
	}

	public void spawnSoulStoneMonitor(World world, int x, int y, int z, int side)
	{
		if (world.isRemote)
		{
			TileElemPillar tep = MultiBlockStructureHelper.getFormedMultiBlockTileEntity(TileElemPillar.class, world, getSupportedBlocks(), x, y, z, world.getBlockMetadata(x, y, z));
			if (tep != null)
			{
				if (tep.getIsFormless())
					return;
				EntityElemPillarMonitor eepm = new EntityElemPillarMonitor(world, tep);
				eepm.setupLifeSpan();
				int xOff = 0, yOff = 0, zOff = 0;
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				switch (dir)
				{
					case DOWN:
						yOff = -1;
						break;
					case EAST:
						xOff = 1;
						break;
					case NORTH:
						zOff = -1;
						break;
					case SOUTH:
						zOff = 1;
						break;
					case UP:
						yOff = 1;
						break;
					case WEST:
						xOff = -1;
						break;
					default:
						break;

				}
				eepm.posX = x + xOff;
				eepm.posY = y + yOff;
				eepm.posZ = z + zOff;
				eepm.setDirection(dir);
				world.spawnEntityInWorld(eepm);
			}
		}
	}
}
