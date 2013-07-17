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
import snake.mcmods.theinvoker.inventory.ContainerElementPurifier;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.ElementPurifierGUINetwork;
import snake.mcmods.theinvoker.lib.constants.TIEnergyID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.elempurifier.ElementPurifierMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketElementPurifierUpdate;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileElementPurifier extends TileTIBase implements IEnergyContainerWrapper, IEnergyConsumerWrapper, IInventory
{

	public static final int INNER_TANK_CAPACITY = 50;
	public static final int SOUL_TANK_CAPACITY = 500;
	public static final int EFFECTIVE_RANGE = 12;
	public static final int MAX_REQUEST = 1;

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

	private int energyConsumingThisRound;
	private int energyConsumingLeft;
	private ItemStack processItem;
	private int energyIDThisRound;
	private int idolTicks;
	private boolean isProcessing;
	public boolean hasWork;
	public float spinValue;
	public float floatValue;

	public ItemStack getProcessItem()
	{
		return processItem;
	}

	public boolean getIsProcessing()
	{
		return energyConsumingLeft > 0;
	}

	public float getProcessProgress()
	{
		return (float)(energyConsumingThisRound - energyConsumingLeft) / energyConsumingThisRound;
	}
	
	public ItemStack getMaterialSlot()
	{
		return materialSlot;
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
			soulContainer.setEnergyCapacity(SOUL_TANK_CAPACITY);
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
		if (energyConsumingLeft > 0)
		{
			spinValue += .02;
			floatValue += .1;
			idolTicks = 0;
			energyConsumingLeft = energyConsumer.getEnergyIsRequesting();
		}
		else if (idolTicks <= 0)
		{
			if (isProcessing)
			{
				if (energyConsumingLeft <= 0 && processItem != null && energyIDThisRound > 0)
				{
					isProcessing = false;
					EnergyContainer container = getContainerByEnergyID(energyIDThisRound);
					int value = container == soulContainer ? ElementPurifierMisc.SOUL_ENERGY_PER_RUNE : ElementPurifierMisc.ELEMENT_PER_RUNE;
					int gainValue = container.gain(value, false);
					if (gainValue > 0)
						container.gain(gainValue, true);
					energyIDThisRound = 0;
					processItem = null;
					energyConsumer.requestEnergy(0);
					energyConsumingThisRound = 0;
					hasWork = getIsAbleToWork();
					this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					sendUpdatePacket();
				}
				isProcessing = false;
			}
			else if (getIsAbleToWork())
			{
				isProcessing = true;
				processItem = materialSlot.copy();
				processItem.stackSize = 1;
				energyIDThisRound = ElementPurifierMisc.getEnergyID(materialSlot.itemID, materialSlot.getItemDamage());
				int boilTicks = ElementPurifierMisc.getTotalBoilTicks(materialSlot.itemID, materialSlot.getItemDamage());
				energyConsumingThisRound = boilTicks * MAX_REQUEST;
				energyConsumingLeft = boilTicks * MAX_REQUEST;
				energyConsumer.requestEnergy(energyConsumingThisRound);
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

	protected EnergyContainer getContainerByEnergyID(int id)
	{
		if (id == TIEnergyID.SOUL)
			return soulContainer;
		else if (id == TIEnergyID.ICE)
			return iceContainer;
		else if (id == TIEnergyID.FIRE)
			return fireContainer;
		else if (id == TIEnergyID.WIND)
			return windContainer;
		else if (id == TIEnergyID.DARKNESS)
			return darknessContainer;
		else
			return null;
	}

	public boolean getIsAbleToWork()
	{
		return materialSlot != null && materialSlot.stackSize > 0 && ElementPurifierMisc.getIsValidRecipe(materialSlot.itemID, materialSlot.getItemDamage()) && !getContainerIsFull();
	}

	public boolean getContainerIsFull()
	{
		if (materialSlot == null || materialSlot.stackSize <= 0)
			return soulContainer.getIsFull() && getTotalEnergyLevelOfElementContainers() >= INNER_TANK_CAPACITY;
		int energyID = ElementPurifierMisc.getEnergyID(materialSlot.itemID, materialSlot.getItemDamage());
		if (energyID == TIEnergyID.SOUL)
		{
			return soulContainer.getIsFull();
		}
		else
		{
			return getTotalEnergyLevelOfElementContainers() >= INNER_TANK_CAPACITY;
		}
	}

	protected int getTotalEnergyLevelOfElementContainers()
	{
		return iceContainer.getEnergyLevel() + fireContainer.getEnergyLevel() + windContainer.getEnergyLevel() + darknessContainer.getEnergyLevel();
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
		energyConsumingThisRound = nbt.getInteger(TAG_TICKS_THIS_ROUND);
		energyConsumingLeft = nbt.getInteger(TAG_TICKS_LEFT);
		if (nbt.hasKey(TAG_ITEM_ID_THIS_ROUND))
		{
			int itemIDThisRound = nbt.getInteger(TAG_ITEM_ID_THIS_ROUND);
			int itemDmgThisRound = nbt.getInteger(TAG_ITEM_DMG_THIS_ROUND);
			processItem = new ItemStack(itemIDThisRound, 1, itemDmgThisRound);
		}
		energyIDThisRound = nbt.getInteger(TAG_ENERGY_THIS_ROUND);
		isProcessing = nbt.getBoolean(TAG_IS_PROCESSING);
		if(nbt.hasKey(TAG_MATERIAL_SLOT))
		{
			materialSlot = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(TAG_MATERIAL_SLOT));
		}
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
		nbt.setInteger(TAG_TICKS_THIS_ROUND, energyConsumingThisRound);
		nbt.setInteger(TAG_TICKS_LEFT, energyConsumingLeft);
		if (processItem != null)
		{
			nbt.setInteger(TAG_ITEM_ID_THIS_ROUND, processItem.itemID);
			nbt.setInteger(TAG_ITEM_DMG_THIS_ROUND, processItem.getItemDamage());
		}
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

	public void sendUpdatePacket()
	{
		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128, worldObj.provider.dimensionId, PacketTypeHandler.serialize(new PacketElementPurifierUpdate(xCoord, yCoord, zCoord, getDirection().ordinal(), getOwnerName(), this.hasWork)));
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		if (itemstack == null)
			return false;

		return itemstack.itemID == TIItems.soulRune.itemID;
	}

	public void sendNetworkGUIData(ContainerElementPurifier container, ICrafting c)
	{
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.PROCESS_TICKS.ordinal(), energyConsumingLeft);
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.TOTAL_TICKS.ordinal(), energyConsumingThisRound);
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.ENERGY_ID.ordinal(), energyIDThisRound);
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.SOUL_REQUESTING.ordinal(), energyConsumer.getEnergyIsRequesting());
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.SOUL_LEVEL.ordinal(), soulContainer.getEnergyLevel());
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.ICE_LEVEL.ordinal(), iceContainer.getEnergyLevel());
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.FIRE_LEVEL.ordinal(), fireContainer.getEnergyLevel());
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.WIND_LEVEL.ordinal(), windContainer.getEnergyLevel());
		c.sendProgressBarUpdate(container, ElementPurifierGUINetwork.DARKNESS_LEVEL.ordinal(), soulContainer.getEnergyLevel());
	}

	public void receiveNetworkGUIData(int signiture, int value)
	{
		ElementPurifierGUINetwork s = ElementPurifierGUINetwork.fromInt(signiture);
		switch (s)
		{
			case ENERGY_ID:
				energyIDThisRound = value;
				break;
			case PROCESS_TICKS:
				energyConsumingLeft = value;
				break;
			case TOTAL_TICKS:
				energyConsumingThisRound = value;
				break;
			case SOUL_REQUESTING:
				energyConsumer.requestEnergy(value);
				break;
			case SOUL_LEVEL:
				soulContainer.setEnergyLevel(value);
				break;
			case ICE_LEVEL:
				iceContainer.setEnergyLevel(value);
				break;
			case FIRE_LEVEL:
				fireContainer.setEnergyLevel(value);
				break;
			case WIND_LEVEL:
				windContainer.setEnergyLevel(value);
				break;
			case DARKNESS_LEVEL:
				darknessContainer.setEnergyLevel(value);
				break;
			default:
				break;

		}
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
	private static final String TAG_ITEM_ID_THIS_ROUND = "itemID";
	private static final String TAG_ITEM_DMG_THIS_ROUND = "itemID";
	private static final String TAG_ENERGY_THIS_ROUND = "energyID";
	private static final String TAG_IS_PROCESSING = "isProcessing";
	private static final String TAG_MATERIAL_SLOT = "materialSlot";
}
