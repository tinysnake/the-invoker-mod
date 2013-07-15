package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIGuiID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.lib.constants.TIRenderID;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;
import snake.mcmods.theinvoker.utils.Utils;

public class BlockElementPurifier extends BlockContainer
{

	public BlockElementPurifier(int id)
	{
		super(id, Material.iron);
		this.setCreativeTab(TheInvoker.tab);
		this.setUnlocalizedName(TIName.BLOCK_ELEMENT_PURIFIER);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileElementPurifier();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileElementPurifier)
		{
			((TileElementPurifier)te).setDirection(Utils.getPlaceDirection(entity));
			((TileElementPurifier)te).setOwnerName(entity.getEntityName());
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
			player.openGui(TheInvoker.instance, TIGuiID.ELEMENT_PURIFIER, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int metadata)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileElementPurifier)
		{
			TileElementPurifier tep = (TileElementPurifier)te;
			if (tep.getMaterialSlot() != null && tep.getMaterialSlot().stackSize > 0)
			{
				dropBlockAsItem_do(tep.worldObj, x, y, z, tep.getMaterialSlot());
			}
		}
		super.breakBlock(world, x, y, z, id, metadata);
	}

	@Override
	public int getRenderType()
	{
		return TIRenderID.ELEMENT_PURIFIER;
	}
}
