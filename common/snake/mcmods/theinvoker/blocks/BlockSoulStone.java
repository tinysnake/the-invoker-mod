package snake.mcmods.theinvoker.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
	    blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID+":"+getUnlocalizedName2());
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack itemstack)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te!=null&& te instanceof TileSoulStone)
		{
			TileSoulStone tss = (TileSoulStone)te;
			tss.setDirection(0);
			tss.setOwnerName(player.getEntityName());
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileSoulStone();
	}

}
