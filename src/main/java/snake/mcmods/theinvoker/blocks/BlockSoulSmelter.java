package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIGuiID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;
import snake.mcmods.theinvoker.tileentities.TileTIBase;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulSmelter extends BlockContainer
{

	public BlockSoulSmelter()
	{
		super(Material.iron);
		this.setBlockName(TIName.BLOCK_SOUL_SMELTER);
		this.setCreativeTab(TheInvoker.tab);
	}

	private IIcon iconFrontOff;

	private IIcon iconFrontOn;

	private IIcon iconTopOff;

	private IIcon iconTopOn;

	private IIcon iconBottom;

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new TileSoulSmelter();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
	{
		TileTIBase te = (TileTIBase)world.getTileEntity(x, y, z);
//		te.setOwnerName(entityLiving.getEntityName());
		te.setDirection(Utils.getPlaceDirection(entityLiving));
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
//	{
//		if (iBlockAccess == null)
//			return getIcon(side, 0);
//		TileSoulSmelter tss = (TileSoulSmelter)iBlockAccess.getTileEntity(x, y, z);
//		if (tss != null)
//		{
//			if (tss.getDirection().ordinal() == side)
//			{
//				return tss.getHasWork() ? iconFrontOn : iconFrontOff;
//			}
//			else
//			{
//				switch (side)
//				{
//					case 0:
//						return iconBottom;
//					case 1:
//						return tss.getHasWork() ? iconTopOn : iconTopOff;
//					default:
//						return blockIcon;
//				}
//			}
//		}
//		return blockIcon;
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		switch (side)
		{
			case 0:
				return iconBottom;
			case 1:
				return iconTopOff;
			case 4:
				return iconFrontOff;
			default:
				return blockIcon;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		iconBottom = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_BOTTOM);
		blockIcon = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_SIDE);
		iconFrontOff = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_FRONT_OFF);
		iconFrontOn = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_FRONT_ON);
		iconTopOff = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_TOP_OFF);
		iconTopOn = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_TOP_ON);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if (player.getHeldItem() == null)
		{
			if (!world.isRemote)
				player.openGui(TheInvoker.instance, TIGuiID.SOUL_SMELTER, world, x, y, z);
			return true;
		}
		else if (player.getHeldItem().getItem() == TIItems.evilTouch)
		{

		}
		else if (player.getHeldItem().getItem() == (Item)Item.itemRegistry.getObject("lava_bucket"))
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileSoulSmelter)
			{
				TileSoulSmelter soulSmelter = (TileSoulSmelter)te;
				int amount = soulSmelter.fill(null, new FluidStack(FluidRegistry.LAVA.getID(), FluidContainerRegistry.BUCKET_VOLUME), false);
				if (amount > 0)
				{
					soulSmelter.fill(null, new FluidStack(FluidRegistry.LAVA.getID(), FluidContainerRegistry.BUCKET_VOLUME), true);
					if (!player.capabilities.isCreativeMode)
						player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack((Item)Item.itemRegistry.getObject("bucket"));
				}
				return true;
			}
		}
		else
		{
			if (!world.isRemote)
				player.openGui(TheInvoker.instance, TIGuiID.SOUL_SMELTER, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int metadata)
	{
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, b, metadata);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileSoulSmelter)
		{
			FluidStack ls = ((TileSoulSmelter)te).getLavaTank().getFluid();
			if (ls != null && ls.amount > 0)
			{
				return (int)Math.min(15, ls.amount * 15F / FluidContainerRegistry.BUCKET_VOLUME);
			}
		}
		return super.getLightValue(world, x, y, z);
	}

	private void dropItems(World world, int x, int y, int z)
	{
		TileSoulSmelter tss = (TileSoulSmelter)world.getTileEntity(x, y, z);
		if (tss != null && tss.getInputSlot() != null)
		{
			this.dropBlockAsItem(world, x, y, z, tss.getInputSlot().copy());
		}
	}
}
