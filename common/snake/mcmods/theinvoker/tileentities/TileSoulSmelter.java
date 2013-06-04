package snake.mcmods.theinvoker.tileentities;

import snake.mcmods.theinvoker.lib.constants.TIName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidTank;

public class TileSoulSmelter extends TileTIBase implements IInventory, ISidedInventory
{
    public static final int MAX_LIQUID = LiquidContainerRegistry.BUCKET_VOLUME * 3;

    public TileSoulSmelter()
    {
        setDirection(ForgeDirection.SOUTH.ordinal());
        lavaTank = new LiquidTank(MAX_LIQUID);
    }

    private int processTimer;

    private LiquidTank lavaTank;
    private ItemStack inv;

    public boolean getIsActive()
    {
        return processTimer > 0;
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inv;
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        inv = itemstack;
    }

    @Override
    public String getInvName()
    {
        return TIName.BLOCK_SOUL_SMELTER;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void openChest()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void closeChest()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return false;
    }
}
