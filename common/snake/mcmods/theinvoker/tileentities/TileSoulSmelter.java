package snake.mcmods.theinvoker.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.energy.IEnergyContainerWrapper;
import snake.mcmods.theinvoker.energy.TIEnergy;
import snake.mcmods.theinvoker.inventory.ContainerSoulSmelter;
import snake.mcmods.theinvoker.lib.SoulSmelterGUINetwork;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.soulsmelter.SoulSmelterMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketSoulSmelterUpdate;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileSoulSmelter extends TileTIBase implements IInventory, IFluidHandler, IEnergyContainerWrapper
{
	public static final int MAX_LIQUID = FluidContainerRegistry.BUCKET_VOLUME * 3;

	public static final int MAX_ENERGY_CAPACITY = 200;

	public static final int ENERGY_RANGE = 8;

	public static final int MAX_ENERGY_REQUEST = 5;

	public TileSoulSmelter()
	{
		setDirection(ForgeDirection.SOUTH.ordinal());
		lavaTank = new FluidTank(FluidRegistry.LAVA, 0, MAX_LIQUID);
	}

	private boolean isProccessing;
	private int lastBoilTicks;
	private int boilTicksLeft;
	private int idolTicks;
	private int processingItemID;
	private int processingItemDamage;
	private FluidTank lavaTank;
	private ItemStack inputSlot;
	private EnergyContainer energyContainer;

	public boolean hasWork;

	public int getBoilTicksLeft()
	{
		return boilTicksLeft;
	}

	public float getBoilProgress()
	{
		return (lastBoilTicks - boilTicksLeft) / (float)lastBoilTicks;
	}

	private void setBoilTicks(int ticks)
	{
		lastBoilTicks = boilTicksLeft = ticks;
	}

	@Override
	public EnergyContainer getEnergyContainer()
	{
		return energyContainer;
	}

	public FluidTank getLavaTank()
	{
		return lavaTank;
	}

	public ItemStack getInputSlot()
	{
		return inputSlot;
	}

	public boolean getIsProcessing()
	{
		return isProccessing;
	}

	public boolean getIsAbleToWork()
	{
		return (inputSlot != null && lavaTank.getFluid() != null && SoulSmelterMisc.getIsValidRecipe(inputSlot.itemID, inputSlot.getItemDamage()) && lavaTank.getFluid().amount >= SoulSmelterMisc.getTotalBoilTicks(inputSlot.itemID, inputSlot.getItemDamage()) && energyContainer.getEnergyCapacity() > energyContainer.getEnergyLevel());
	}

	public boolean getHasWork()
	{
		return hasWork;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		setupEnergyContainer();
		if (this.worldObj.isRemote)
			return;
		if (boilTicksLeft > 0)
		{
			boilTicksLeft--;
			idolTicks = 0;
		}
		else if (idolTicks == 0)
		{
			if (isProccessing)
			{
				isProccessing = false;
				if (processingItemID > 0)
				{
					this.drain(null, SoulSmelterMisc.getLavaBurnAmount(processingItemID, processingItemDamage), true);
					energyContainer.gain(SoulSmelterMisc.ENERGY_PER_ITEM, true);
				}
				processingItemID = 0;
				hasWork = getIsAbleToWork();
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				sendUpdatePacket();
			}
			else if (getIsAbleToWork())
			{
				isProccessing = true;
				processingItemID = inputSlot.itemID;
				processingItemDamage = inputSlot.getItemDamage();
				setBoilTicks(SoulSmelterMisc.getTotalBoilTicks(processingItemID, processingItemDamage));
				decrStackSize(0, 1);
				hasWork = true;
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				sendUpdatePacket();
			}
			idolTicks = 3;
		}
		else
		{
			idolTicks--;
		}
	}

	private void setupEnergyContainer()
	{
		if (energyContainer == null)
		{
			energyContainer = new EnergyContainer(this, true, TIEnergy.soul.getID());
			energyContainer.setEffectiveRange(ENERGY_RANGE);
			energyContainer.setEnergyCapacity(MAX_ENERGY_CAPACITY);
			energyContainer.setMaxEnergyRequest(MAX_ENERGY_REQUEST);
		}
		if (!energyContainer.getIsRegistered() && !worldObj.isRemote)
		{
			energyContainer.register();
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		if (itemstack == null)
			return false;

		return SoulSmelterMisc.getIsValidRecipe(itemstack.itemID);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtCompound)
	{
		super.readFromNBT(nbtCompound);
		if (nbtCompound.hasKey(TAG_LAVA_TANK))
			lavaTank.setFluid(FluidStack.loadFluidStackFromNBT(nbtCompound.getCompoundTag(TAG_LAVA_TANK)));
		if (nbtCompound.hasKey(TAG_ENERGY_CONTAINER))
			energyContainer = EnergyContainer.readFromNBT(nbtCompound.getCompoundTag(TAG_ENERGY_CONTAINER), this);
		if (nbtCompound.hasKey(TAG_INPUT_SLOT))
			inputSlot = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag(TAG_INPUT_SLOT));
		if (nbtCompound.hasKey(TAG_BOIL_TICKS_LEFT))
			boilTicksLeft = nbtCompound.getInteger(TAG_BOIL_TICKS_LEFT);
		if (nbtCompound.hasKey(TAG_LAST_BOIL_TICKS))
			lastBoilTicks = nbtCompound.getInteger(TAG_LAST_BOIL_TICKS);
		if (nbtCompound.hasKey(TAG_BOIL_TICKS_LEFT))
			processingItemID = nbtCompound.getInteger(TAG_PROCESSING_ITEM_ID);
		if (nbtCompound.hasKey(TAG_IS_PROCESSING))
			isProccessing = nbtCompound.getBoolean(TAG_IS_PROCESSING);
		hasWork = getIsProcessing();
		setupEnergyContainer();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtCompound)
	{
		super.writeToNBT(nbtCompound);
		setupEnergyContainer();
		if (inputSlot != null)
		{
			nbtCompound.setTag(TAG_INPUT_SLOT, inputSlot.writeToNBT(new NBTTagCompound()));
		}
		nbtCompound.setTag(TAG_LAVA_TANK, lavaTank.writeToNBT(new NBTTagCompound()));
		if (energyContainer != null)
		{
			nbtCompound.setTag(TAG_ENERGY_CONTAINER, energyContainer.writeToNBT(new NBTTagCompound()));
		}
		nbtCompound.setInteger(TAG_BOIL_TICKS_LEFT, boilTicksLeft);
		nbtCompound.setBoolean(TAG_IS_PROCESSING, isProccessing);
		nbtCompound.setInteger(TAG_LAST_BOIL_TICKS, lastBoilTicks);
		nbtCompound.setInteger(TAG_PROCESSING_ITEM_ID, processingItemID);
	}

	public void sendNetworkGUIData(ContainerSoulSmelter container, ICrafting c)
	{
		c.sendProgressBarUpdate(container, SoulSmelterGUINetwork.LAST_BOIL_TICK.ordinal(), lastBoilTicks);
		c.sendProgressBarUpdate(container, SoulSmelterGUINetwork.BOIL_PROGRESS.ordinal(), boilTicksLeft);
		c.sendProgressBarUpdate(container, SoulSmelterGUINetwork.LAVA_CAPACITY.ordinal(), lavaTank.getFluid() != null ? lavaTank.getFluid().amount : 0);
	}

	public void receiveNetworkGUIData(int signiture, int value)
	{
		SoulSmelterGUINetwork s = SoulSmelterGUINetwork.fromInt(signiture);
		switch (s)
		{
			case LAST_BOIL_TICK:
				this.lastBoilTicks = value;
			case BOIL_PROGRESS:
				this.boilTicksLeft = value;
				break;
			case LAVA_CAPACITY:
				if (this.lavaTank.getFluid() == null)
					this.lavaTank.setFluid(new FluidStack(FluidRegistry.LAVA, value));
				else
					this.lavaTank.getFluid().amount = value;
				break;
			default:
				break;
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		if (energyContainer != null)
			energyContainer.destroy();
	}

	@Override
	public Packet getDescriptionPacket()
	{
		setupEnergyContainer();
		return super.getDescriptionPacket();
	}

	public void sendUpdatePacket()
	{
		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128, worldObj.provider.dimensionId, PacketTypeHandler.serialize(new PacketSoulSmelterUpdate(xCoord, yCoord, zCoord, getDirection().ordinal(), getOwnerName(), this.hasWork, lavaTank.getFluid() != null ? lavaTank.getFluid().amount : 0)));
	}

	private static final String TAG_LAVA_TANK = "lavaTank";
	private static final String TAG_INPUT_SLOT = "inputSlot";
	private static final String TAG_ENERGY_CONTAINER = "energyContainer";
	private static final String TAG_LAST_BOIL_TICKS = "lastBoilTicks";
	private static final String TAG_BOIL_TICKS_LEFT = "boilTicksLeft";
	private static final String TAG_PROCESSING_ITEM_ID = "processingItemID";
	private static final String TAG_IS_PROCESSING = "isProcessing";

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		if (resource != null && resource.fluidID == FluidRegistry.LAVA.getID())
		{
			int result = lavaTank.fill(resource, doFill);
			if (doFill)
				sendUpdatePacket();
			return result;
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		FluidStack result = lavaTank.drain(maxDrain, doDrain);
		if (doDrain)
			sendUpdatePacket();
		return result;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return drain(from, resource != null ? resource.amount : 0, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		if (lavaTank.getFluid() != null && fluid.getID() == lavaTank.getFluid().fluidID)
			return true;
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		if (lavaTank.getFluid() != null && fluid.getID() == lavaTank.getFluid().fluidID)
			return true;
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[] { new FluidTankInfo(lavaTank.getFluid(), lavaTank.getCapacity()) };
	}
}
