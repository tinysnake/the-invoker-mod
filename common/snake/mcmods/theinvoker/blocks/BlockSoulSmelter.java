package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import scala.Console;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIGuiID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;
import snake.mcmods.theinvoker.tileentities.TileTIBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulSmelter extends BlockContainer
{

	public BlockSoulSmelter(int id)
	{
		super(id, Material.iron);
		this.setUnlocalizedName(TIName.BLOCK_SOUL_SMELTER);
		this.setCreativeTab(TheInvoker.tab);
	}

	private Icon iconFrontOff;

	private Icon iconFrontOn;

	private Icon iconTopOff;

	private Icon iconTopOn;

	private Icon iconBottom;

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileSoulSmelter();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving,
	        ItemStack itemStack)
	{
		TileTIBase te = (TileTIBase) world.getBlockTileEntity(x, y, z);
		te.setOwnerName(entityLiving.getEntityName());
		int face = MathHelper
		        .floor_double((double) (entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		switch (face)
		{
			case 0:
				te.setDirection(2);
				break;
			case 1:
				te.setDirection(5);
				break;
			case 2:
				te.setDirection(3);
				break;
			case 3:
				te.setDirection(4);
				break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		TileSoulSmelter tss = (TileSoulSmelter) iBlockAccess.getBlockTileEntity(x, y, z);
		if (tss != null)
		{
			if (tss.getDirection().ordinal() == side)
			{
				return tss.getIsActive() ? iconFrontOn : iconFrontOff;
			}
			else
			{
				switch (side)
				{
					case 0:
						return iconBottom;
					case 1:
						return tss.getIsActive() ? iconTopOn : iconTopOff;
					default:
						return blockIcon;
				}
			}
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata)
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
	public void registerIcons(IconRegister iconRegister)
	{
		iconBottom = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_BOTTOM);
		blockIcon = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_SIDE);
		iconFrontOff = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_FRONT_OFF);
		iconFrontOn = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_FRONT_ON);
		iconTopOff = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_TOP_OFF);
		iconTopOn = iconRegister.registerIcon(Textures.BLOCK_SOUL_SMELTER_TOP_ON);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
	        EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return false;
		if(player.getHeldItem()==null)
		{
		    if (!world.isRemote)
                player.openGui(TheInvoker.instance, TIGuiID.SOUL_SMELTER, world, x, y, z);
            return true;
		}
		else if (player.getHeldItem().itemID == TIItems.evilTouch.itemID)
		{
			
		}
		else if(player.getHeldItem().itemID==Item.bucketLava.itemID)
		{
		    TileEntity te = world.getBlockTileEntity(x, y, z);
		    if(te instanceof TileSoulSmelter)
		    {
		        TileSoulSmelter soulSmelter = (TileSoulSmelter) te;
		        int amount = soulSmelter.fill(0, new LiquidStack(Block.lavaStill, LiquidContainerRegistry.BUCKET_VOLUME), false);
		        if(amount>0)
		        {
		            soulSmelter.fill(0, new LiquidStack(Block.lavaStill, LiquidContainerRegistry.BUCKET_VOLUME), true);
	                player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Item.bucketEmpty);
		        }
		        Console.println("fill amount: "+amount);
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
	public void breakBlock(World world, int x, int y, int z, int id, int metadata)
	{
	    dropItems(world,x,y,z);
	    super.breakBlock(world, x, y, z, id, metadata);
	}

    private void dropItems(World world, int x, int y, int z)
    {
        TileSoulSmelter tss = (TileSoulSmelter)world.getBlockTileEntity(x, y, z);
        if(tss!=null)
        {
            this.dropBlockAsItem_do(world, x, y, z, tss.getInputSlot().copy());
        }
    }
}
