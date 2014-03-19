package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulStoneDummy extends BlockMultiBlockBaseDummy
{

	public BlockSoulStoneDummy(BlockMultiBlockBase realBlock)
	{
		super(Material.iron, realBlock);
		this.setCreativeTab(TheInvoker.tab);
		this.setBlockName(TIName.BLOCK_SOUL_STONE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + Utils.getTruelyUnlocalizedName(TIBlocks.soulStone));
	}

	@Override
	protected void setupTileEntity(World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileSoulStone)
		{
			TileSoulStone tss = (TileSoulStone)te;
			tss.setDirection(0);
//			tss.setOwnerName(player.getEntityName());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return null;
	}
}
