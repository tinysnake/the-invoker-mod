package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.lib.constants.LangKeys;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.EvilTouchMisc;
import snake.mcmods.theinvoker.logic.SoulStoneMisc;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulStone extends BlockContainer
{

	public BlockSoulStone(int id)
	{
		super(id, Material.iron);
		this.setCreativeTab(TheInvoker.tab);
		this.setUnlocalizedName(TIName.BLOCK_SOUL_STONE);
	}

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

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if (player.getHeldItem() != null && player.getHeldItem().itemID == TIBlocks.soulStoneDummy.blockID)
			return false;

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null)
			return true;

		TileSoulStone tss = (TileSoulStone)te;
		if (EvilTouchMisc.isPlayHoldingEvilTouch(player))
		{
			if (!tss.getIsFormless())
			{
				reform(world, x, y, z);
			}
			else
			{
				if (SoulStoneMisc.getIsFormable(world, x, y, z))
				{
					reform(world, x, y, z);
				}
				else
				{
					if (world.isRemote)
						player.addChatMessage(Lang.getLocalizedStr(LangKeys.TEXT_SOUL_STONE_NOT_FORMABLE));
				}
			}
		}
		else
		{
			SoulStoneMisc.spawnSoulStoneMonitor(world, x, y, z, side);
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int metadata, int id)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileSoulStone)
		{
			TileSoulStone tss = (TileSoulStone)te;
			int[] dummyCoords = SoulStoneMisc.getAdjacentDummyBlockCoords(world, x, y, z);
			if (dummyCoords != null)
			{
				if (world.setBlock(dummyCoords[0], dummyCoords[1], dummyCoords[2], TIBlocks.soulStone.blockID, world.getBlockMetadata(x, y, z), 4))
				{
					TileEntity teNew = world.getBlockTileEntity(dummyCoords[0], dummyCoords[1], dummyCoords[2]);
					if (teNew != null && teNew instanceof TileSoulStone)
					{
						TileSoulStone tssNew = (TileSoulStone)teNew;
						tssNew.transferFrom(tss);
						tssNew.setIsFormless(true);
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, metadata, id);
	}

	private void reform(World world, int x, int y, int z)
	{
		TileSoulStone tss = (TileSoulStone)world.getBlockTileEntity(x, y, z);
		int metadata = SoulStoneMisc.getMetadataOfFormSize(world, x, y, z);
		if (metadata != world.getBlockMetadata(x, y, z))
		{
			world.setBlockMetadataWithNotify(z, y, z, metadata, 4);
			tss.getEnergyContainer().setEnergyCapacity(SoulStoneMisc.CAPACITY_OF_METADATA[metadata]);
			int[] startCoords = SoulStoneMisc.getStructureStartPoint(world, x, y, z);
			tss.setOriginCoords(startCoords[0], startCoords[1], startCoords[2]);
			// reform effect
		}
		tss.setIsFormless(false);
	}
}
