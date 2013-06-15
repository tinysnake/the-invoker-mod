package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import scala.Console;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.lib.constants.LangKeys;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.EvilTouchMisc;
import snake.mcmods.theinvoker.logic.SoulStoneMisc;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulStoneDummy extends Block
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
		blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + getUnlocalizedName2());
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if (player.getHeldItem() != null && player.getHeldItem().itemID == blockID)
			return false;
		if (EvilTouchMisc.isPlayHoldingEvilTouch(player))
		{
			TileSoulStone tss = SoulStoneMisc.getFormedTileSoulStone(world, x, y, z);
			if (tss != null)
			{
				int metadata = SoulStoneMisc.getMetadataOfFormSize(world, x, y, z);
				world.setBlockMetadataWithNotify(z, y, z, metadata, 4);
				tss.getEnergyContainer().setEnergyCapacity(SoulStoneMisc.CAPACITY_OF_METADATA[metadata]);
			}
			else if (SoulStoneMisc.getIsFormable(world, x, y, z))
			{
				if (world.setBlock(x, y, z, TIBlocks.soulStone.blockID, SoulStoneMisc.getMetadataOfFormSize(world, x, y, z), 4))
				{
					TIBlocks.soulStone.onBlockPlacedBy(world, x, y, z, player, new ItemStack(TIBlocks.soulStone, 0));
					Console.println("formed");
				}
			}
			else
			{
				if (!world.isRemote)
					player.sendChatToPlayer(Lang.getLocalizedStr(LangKeys.TEXT_SOUL_STONE_NOT_FORMABLE));
			}
		}
		return true;
	}
}
