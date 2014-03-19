package snake.mcmods.theinvoker.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.ElemPillarType;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.tileentities.TileElemPillar;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElemPillarDummy extends BlockMultiBlockBaseDummy
{
	public static final String[] NAMES = {
			TIName.BLOCK_NAME_ICE_ELEM_PILLAR,
			TIName.BLOCK_NAME_FIRE_ELEM_PILLAR,
			TIName.BLOCK_NAME_WIND_ELEM_PILLAR,
			TIName.BLOCK_NAME_DARKNESS_ELEM_PILLAR
	};

	private IIcon[] icons;

	public BlockElemPillarDummy(BlockMultiBlockBase realBlock)
	{
		super(Material.iron, realBlock);
		this.setCreativeTab(TheInvoker.tab);
		this.setBlockName(TIName.BLOCK_ELEMENT_PILLAR);
		icons = new IIcon[ElemPillarType.values().length];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return icons[metadata];
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < ElemPillarType.values().length; i++)
		{
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		for (int i = 0; i < ElemPillarType.values().length; i++)
		{
			icons[i] = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + Utils.getTruelyUnlocalizedName(this) + "." + NAMES[i]);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return null;
	}

	@Override
	protected void setupTileEntity(World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileElemPillar)
		{
			((TileElemPillar)te).setDirection(0);
//			((TileElemPillar)te).setOwnerName(player.getEntityName());
		}
	}

}
