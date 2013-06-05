package snake.mcmods.theinvoker.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidTank;
import snake.mcmods.theinvoker.lib.constants.TIName;

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
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (inv != null) {
            if (inv.stackSize <= 0) {
                inv = null;
                return null;
            }
            ItemStack newStack = inv;
            if (amount >= newStack.stackSize) {
                inv = null;
            } else {
                newStack = inv.splitStack(amount);
            }

            return newStack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        if (inv == null)
            return null;
        ItemStack toReturn = inv;
        inv = null;
        return toReturn;
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
        return true;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack)
    {
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        int[] arr =
        { 0 };
        return arr;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return inv.stackSize < getInventoryStackLimit();
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return false;
    }
}
