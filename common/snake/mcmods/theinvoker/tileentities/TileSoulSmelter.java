package snake.mcmods.theinvoker.tileentities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import net.minecraftforge.liquids.LiquidDictionary.LiquidRegisterEvent;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class TileSoulSmelter extends TileTIBase implements IInventory, ISidedInventory,
        ITankContainer
{
    public static final int MAX_LIQUID = LiquidContainerRegistry.BUCKET_VOLUME * 3;

    public TileSoulSmelter()
    {
        setDirection(ForgeDirection.SOUTH.ordinal());
        lavaTank = new LiquidTank(MAX_LIQUID);
    }

    private int processTimer;

    private LiquidTank lavaTank;
    private ItemStack inputSlot;
    private ItemStack outputSlot;

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
        if (i == 0)
            return inputSlot;
        else
            return outputSlot;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack itemSlot = slot == 0 ? inputSlot : outputSlot;
        if (itemSlot != null)
        {
            if (itemSlot.stackSize <= 0)
            {
                itemSlot = null;
                return null;
            }
            ItemStack newStack = itemSlot;
            if (amount >= newStack.stackSize)
            {
                itemSlot = null;
            }
            else
            {
                newStack = itemSlot.splitStack(amount);
            }

            return newStack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        if (inputSlot == null)
            return null;
        ItemStack toReturn = inputSlot;
        inputSlot = null;
        return toReturn;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        inputSlot = itemstack;
    }

    @Override
    public String getInvName()
    {
        return TIName.CONTAINER_SOUL_SMELTER;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return true;
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
        if (itemstack == null || itemstack.getItem() == null)
            return false;

        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        int[] arr = { 0 };
        return arr;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return i == 0 || itemstack == null || itemstack.getItem() == null
                || itemstack.getItem().itemID == TIItems.soulShard.itemID;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side)
    {
        return slot == 1;
    }

    @Override
    public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
    {
        return fill(0,resource,doFill);
    }

    @Override
    public int fill(int tankIndex, LiquidStack resource, boolean doFill)
    {
        if(resource.itemID==Block.lavaStill.blockID)
        {
            return lavaTank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return null;
    }

    @Override
    public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
    {
        return null;
    }

    @Override
    public ILiquidTank[] getTanks(ForgeDirection direction)
    {
        return new ILiquidTank[] { lavaTank };
    }

    @Override
    public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
    {
        return type.itemID == Block.lavaStill.blockID ? lavaTank : null;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        // TODO Auto-generated method stub
        super.readFromNBT(nbtCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        // TODO Auto-generated method stub
        super.writeToNBT(nbtCompound);
    }
}
