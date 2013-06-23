package snake.mcmods.theinvoker.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import snake.mcmods.theinvoker.energy.EnergyConsumer;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.energy.IEnergyConsumerWrapper;
import snake.mcmods.theinvoker.energy.IEnergyContainerWrapper;
import snake.mcmods.theinvoker.energy.TIEnergy;
import snake.mcmods.theinvoker.inventory.ContainerSoulSmelter;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class TileElementPurifier extends TileTIBase implements IEnergyContainerWrapper, IEnergyConsumerWrapper,
        IInventory
{

	public static final int INNER_TANK_CAPACITY = 500;
	public static final int EFFECTIVE_RANGE = 12;
	public static final int MAX_REQUEST = 5;

	public TileElementPurifier()
	{
		setDirection(0);
	}

	private EnergyConsumer energyConsumer;
	private EnergyContainer soulContainer;
	private EnergyContainer fireContainer;
	private EnergyContainer iceContainer;
	private EnergyContainer windContainer;
	private EnergyContainer darknessContainer;

	private ItemStack materialSlot;

	private int ticksThisRound;
	private int ticksLeft;
	private int itemIDThisRound;
	private int energyIDThisRound;
	private int idolTicks;
	private boolean isProcessing;
	public boolean hasWork;

	public boolean getIsProcessing()
	{
		return isProcessing;
	}

	public float getProcessProgress()
	{
		return (float)(ticksThisRound - ticksLeft) / ticksThisRound;
	}

	@Override
	public EnergyConsumer getEnergyConsumer()
	{
		return energyConsumer;
	}

	@Override
	public EnergyContainer getEnergyContainer()
	{
		return soulContainer;
	}

	public EnergyContainer getEnergyContainer(int energyID)
	{
		if (energyID == soulContainer.getEnergyID())
			return soulContainer;
		else if (energyID == fireContainer.getEnergyID())
			return fireContainer;
		else if (energyID == iceContainer.getEnergyID())
			return iceContainer;
		else if (energyID == windContainer.getEnergyID())
			return windContainer;
		else if (energyID == darknessContainer.getEnergyID())
			return darknessContainer;
		return getEnergyContainer();
	}

	private void setupEnergyUnit()
	{
		if (energyConsumer == null)
		{
			energyConsumer = new EnergyConsumer(this, TIEnergy.soul.getID());
			energyConsumer.setMaxEnergyRequest(1);
			energyConsumer.setEffectiveRange(EFFECTIVE_RANGE);
		}
		if (soulContainer == null)
		{
			soulContainer = new EnergyContainer(this, true, TIEnergy.soul.getID());
			soulContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			soulContainer.setEffectiveRange(EFFECTIVE_RANGE);
			soulContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if (iceContainer == null)
		{
			iceContainer = new EnergyContainer(this, true, TIEnergy.ice.getID());
			iceContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			iceContainer.setEffectiveRange(EFFECTIVE_RANGE);
			iceContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if (fireContainer == null)
		{
			fireContainer = new EnergyContainer(this, true, TIEnergy.fire.getID());
			fireContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			fireContainer.setEffectiveRange(EFFECTIVE_RANGE);
			fireContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if (windContainer == null)
		{

			windContainer = new EnergyContainer(this, true, TIEnergy.wind.getID());
			windContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			windContainer.setEffectiveRange(EFFECTIVE_RANGE);
			windContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if (darknessContainer == null)
		{

			darknessContainer = new EnergyContainer(this, true, TIEnergy.darkness.getID());
			darknessContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			darknessContainer.setEffectiveRange(EFFECTIVE_RANGE);
			darknessContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if (!energyConsumer.getIsRegistered())
			energyConsumer.register();
		if (!soulContainer.getIsRegistered())
			soulContainer.register();
		if (!iceContainer.getIsRegistered())
			iceContainer.register();
		if (!fireContainer.getIsRegistered())
			fireContainer.register();
		if (!windContainer.getIsRegistered())
			windContainer.register();
		if (!darknessContainer.getIsRegistered())
			darknessContainer.register();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		setupEnergyUnit();
		if (ticksLeft > 0)
		{
			ticksLeft--;
			idolTicks = 0;
		}
		else if (idolTicks <= 0)
		{
			if (isProcessing)
			{
				if (ticksLeft <= 0 && itemIDThisRound > 0 && energyIDThisRound > 0)
				{

				}
				isProcessing = false;
			}
			else
			{

			}
			idolTicks = 3;
		}
		else
		{
			idolTicks--;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey(TAG_SOUL_CONTAINER))
			soulContainer = EnergyContainer.readFromNBT(nbt.getCompoundTag(TAG_SOUL_CONTAINER), this);
		if (nbt.hasKey(TAG_ICE_CONTAINER))
			iceContainer = EnergyContainer.readFromNBT(nbt.getCompoundTag(TAG_ICE_CONTAINER), this);
		if (nbt.hasKey(TAG_FIRE_CONTAINER))
			fireContainer = EnergyContainer.readFromNBT(nbt.getCompoundTag(TAG_FIRE_CONTAINER), this);
		if (nbt.hasKey(TAG_WIND_CONTAINER))
			windContainer = EnergyContainer.readFromNBT(nbt.getCompoundTag(TAG_WIND_CONTAINER), this);
		if (nbt.hasKey(TAG_DARKNESS_CONTAINER))
			darknessContainer = EnergyContainer.readFromNBT(nbt.getCompoundTag(TAG_DARKNESS_CONTAINER), this);
		ticksThisRound = nbt.getInteger(TAG_TICKS_THIS_ROUND);
		ticksLeft = nbt.getInteger(TAG_TICKS_LEFT);
		itemIDThisRound = nbt.getInteger(TAG_ITEM_THIS_ROUND);
		energyIDThisRound = nbt.getInteger(TAG_ENERGY_THIS_ROUND);
		isProcessing = nbt.getBoolean(TAG_IS_PROCESSING);
		setupEnergyUnit();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		setupEnergyUnit();
		nbt.setTag(TAG_SOUL_CONTAINER, soulContainer.writeToNBT(new NBTTagCompound()));
		nbt.setTag(TAG_ICE_CONTAINER, iceContainer.writeToNBT(new NBTTagCompound()));
		nbt.setTag(TAG_FIRE_CONTAINER, fireContainer.writeToNBT(new NBTTagCompound()));
		nbt.setTag(TAG_WIND_CONTAINER, windContainer.writeToNBT(new NBTTagCompound()));
		nbt.setTag(TAG_DARKNESS_CONTAINER, darknessContainer.writeToNBT(new NBTTagCompound()));
		nbt.setInteger(TAG_TICKS_THIS_ROUND, ticksThisRound);
		nbt.setInteger(TAG_TICKS_LEFT, ticksLeft);
		nbt.setInteger(TAG_ITEM_THIS_ROUND, itemIDThisRound);
		nbt.setInteger(TAG_ENERGY_THIS_ROUND, energyIDThisRound);
		if (materialSlot != null)
		{
			nbt.setTag(TAG_MATERIAL_SLOT, materialSlot.writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		setupEnergyUnit();
		return super.getDescriptionPacket();
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return materialSlot;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (materialSlot != null)
		{
			if (materialSlot.stackSize <= 0)
			{
				materialSlot = null;
				return null;
			}
			ItemStack newStack = materialSlot;
			if (amount >= newStack.stackSize)
			{
				materialSlot = null;
			}
			else
			{
				newStack = materialSlot.splitStack(amount);
			}

			return newStack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if (materialSlot == null)
			return null;
		ItemStack toReturn = materialSlot;
		materialSlot = null;
		return toReturn;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		materialSlot = itemstack;
	}

	@Override
	public String getInvName()
	{
		return TIName.CONTAINER_ELEMENT_PURIFIER;
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

		return itemstack.itemID == TIItems.soulRune.itemID;
	}

	public void sendNetworkGUIData(ContainerSoulSmelter container, ICrafting c)
	{
		
	}

	public void receiveNetworkGUIData(int signiture, int value)
	{
		
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		if (energyConsumer != null)
			energyConsumer.destroy();
		if (soulContainer != null)
			soulContainer.destroy();
		if (iceContainer != null)
			iceContainer.destroy();
		if (fireContainer != null)
			fireContainer.destroy();
		if (windContainer != null)
			windContainer.destroy();
		if (darknessContainer != null)
			darknessContainer.destroy();
	}

	private static final String TAG_SOUL_CONTAINER = "soulContainer";
	private static final String TAG_ICE_CONTAINER = "iceContainer";
	private static final String TAG_FIRE_CONTAINER = "fireContainer";
	private static final String TAG_WIND_CONTAINER = "windContainer";
	private static final String TAG_DARKNESS_CONTAINER = "darknessContainer";
	private static final String TAG_TICKS_THIS_ROUND = "ticksThisRound";
	private static final String TAG_TICKS_LEFT = "ticksLeft";
	private static final String TAG_ITEM_THIS_ROUND = "itemID";
	private static final String TAG_ENERGY_THIS_ROUND = "energyID";
	private static final String TAG_IS_PROCESSING = "isProcessing";
	private static final String TAG_MATERIAL_SLOT = "materialSlot";
}