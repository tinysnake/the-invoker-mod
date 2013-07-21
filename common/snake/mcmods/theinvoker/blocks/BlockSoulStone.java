package snake.mcmods.theinvoker.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.entities.EntitySoulStoneMonitor;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.LangKeys;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.MultiBlockStructureHelper;
import snake.mcmods.theinvoker.tileentities.TileMultiBlockBase;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulStone extends BlockMultiBlockBase implements IMultiBlockStructure
{

	public static final int MAX_X_SIZE = 7;
	public static final int MAX_Y_SIZE = 3;
	public static final int[] CAPACITY_OF_METADATA = { 4000, 16000, 96000, 420000 };
	public static final int EFFECTIVE_RANGE = 16;
	public static final int[][] FORMABLE_SIZES = { { 1, 1, 1 }, { 2, 1, 2 }, { 3, 2, 4 }, { MAX_X_SIZE, MAX_Y_SIZE, 5 } };

	public BlockSoulStone(int id)
	{
		super(id, Material.iron);
		this.setCreativeTab(TheInvoker.tab);
		this.setUnlocalizedName(TIName.BLOCK_SOUL_STONE);
	}
	
	private ArrayList<Integer> supportedIDs;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + Utils.getTruelyUnlocalizedName(this));
	}

	public void setupTileEntity(World world, int x, int y, int z, String playerName)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileSoulStone)
		{
			TileSoulStone tss = (TileSoulStone)te;
			tss.setDirection(0);
			tss.setOwnerName(playerName);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileSoulStone();
	}

	public void spawnSoulStoneMonitor(World world, int x, int y, int z, int side)
	{
		if (world.isRemote)
		{
			TileSoulStone tss = MultiBlockStructureHelper.getFormedTileSoulStone(TileSoulStone.class, world, getSupportedBlockIDs(), x, y, z);
			if (tss != null)
			{
				if (tss.getIsFormless())
					return;
				EntitySoulStoneMonitor essm = new EntitySoulStoneMonitor(world, tss);
				essm.setupLifeSpan();
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
				essm.posX = x + xOff;
				essm.posY = y + yOff;
				essm.posZ = z + zOff;
				essm.setDirection(dir);
				world.spawnEntityInWorld(essm);
			}
		}
	}

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
	public ArrayList<Integer> getSupportedBlockIDs()
	{
		if(supportedIDs==null)
		{
			supportedIDs = new ArrayList<Integer>();
			supportedIDs.add(TIBlocks.soulStone.blockID);
			supportedIDs.add(blockID);
		}
		return supportedIDs;
	}

	@Override
	public Item getStructureFormerItem()
	{
		return TIItems.evilTouch;
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
