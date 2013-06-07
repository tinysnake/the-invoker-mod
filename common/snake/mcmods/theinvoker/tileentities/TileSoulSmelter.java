package snake.mcmods.theinvoker.tileentities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import snake.mcmods.theinvoker.inventory.ContainerSoulSmelter;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.SoulSmelterGUINetwork;
import snake.mcmods.theinvoker.lib.SoulSmelterMisc;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class TileSoulSmelter extends TileTIBase implements IInventory, ITankContainer
{
    public static final int MAX_LIQUID = LiquidContainerRegistry.BUCKET_VOLUME * 3;

    public TileSoulSmelter()
    {
        setDirection(ForgeDirection.SOUTH.ordinal());
        lavaTank = new LiquidTank(MAX_LIQUID);
    }

    private boolean isProccessing;
    private int lastBoilTicks;
    private int boilTicksLeft;
    private LiquidTank lavaTank;
    private ItemStack inputSlot;

    public int getBoilTicksLeft()
    {
        return boilTicksLeft;
    }

    public float getBoilProgress()
    {
        return (lastBoilTicks - boilTicksLeft) / (float) lastBoilTicks;
    }

    private void setBoilTicks(int ticks)
    {
        lastBoilTicks = boilTicksLeft = ticks;
    }

    public LiquidTank getLavaTank()
    {
        return lavaTank;
    }

    public ItemStack getInputSlot()
    {
        return inputSlot;
    }

    public boolean getIsActive()
    {
        return boilTicksLeft > 0;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (getIsActive())
        {
            boilTicksLeft--;
        }
        else
        {
            if (isProccessing)
            {
                isProccessing = false;
            }
            else
            {
                if (inputSlot != null && SoulSmelterMisc.getIsValidRecipe(inputSlot.itemID) && lavaTank.drain(SoulSmelterMisc.getLavaBurnAmount(inputSlot.itemID), false).amount > 0)
                {
                    isProccessing = true;
                    setBoilTicks(SoulSmelterMisc.getTotalBoilTicks(inputSlot.itemID));
                    lavaTank.drain(SoulSmelterMisc.getLavaBurnAmount(inputSlot.itemID), true);
                    decrStackSize(0, 1);
                }
            }
        }
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inputSlot;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (inputSlot != null)
        {
            if (inputSlot.stackSize <= 0)
            {
                inputSlot = null;
                return null;
            }
            ItemStack newStack = inputSlot;
            if (amount >= newStack.stackSize)
            {
                inputSlot = null;
            }
            else
            {
                newStack = inputSlot.splitStack(amount);
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
        if (itemstack == null)
            return false;

        return SoulSmelterMisc.getIsValidRecipe(itemstack.itemID);
    }

    @Override
    public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
    {
        return fill(0, resource, doFill);
    }

    @Override
    public int fill(int tankIndex, LiquidStack resource, boolean doFill)
    {
        if (resource.itemID == Block.lavaStill.blockID)
        {
            return lavaTank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return drain(0, maxDrain, doDrain);
    }

    @Override
    public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
    {
        return lavaTank.drain(maxDrain, doDrain);
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
        super.readFromNBT(nbtCompound);
        lavaTank.setLiquid(LiquidStack.loadLiquidStackFromNBT(nbtCompound.getCompoundTag("lavaTank")));
        if (nbtCompound.hasKey("inputSlot"))
        {
            inputSlot = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("inputSlot"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);
        if (inputSlot != null)
        {
            nbtCompound.setTag("inputSlot", inputSlot.writeToNBT(new NBTTagCompound()));
        }
        nbtCompound.setTag("lavaTank", lavaTank.writeToNBT(new NBTTagCompound()));
    }

    public void sendNetworkGUIData(ContainerSoulSmelter container, ICrafting c)
    {
        c.sendProgressBarUpdate(container, SoulSmelterGUINetwork.BOIL_PROGRESS.ordinal(), boilTicksLeft);
        c.sendProgressBarUpdate(container, SoulSmelterGUINetwork.LAVA_CAPACITY.ordinal(), lavaTank.getLiquid() != null ? lavaTank.getLiquid().amount : 0);
    }

    public void receiveNetworkGUIData(int signiture, int value)
    {
        SoulSmelterGUINetwork s = SoulSmelterGUINetwork.fromInt(signiture);
        switch (s)
        {
            case BOIL_PROGRESS:
                this.boilTicksLeft = value;
                break;
            case LAVA_CAPACITY:
                if (this.lavaTank.getLiquid() == null)
                    this.lavaTank.setLiquid(new LiquidStack(Block.lavaStill.blockID, value));
                else
                    this.lavaTank.getLiquid().amount = value;
                break;
            default:
                break;

        }
    }
}
