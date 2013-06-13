package snake.mcmods.theinvoker.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

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
	    blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID+":"+getUnlocalizedName2());
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack item)
	{
		ForgeDirection.
	    world.setBlockMetadataWithNotify(x, y, z, 0, 2);
	}
}
