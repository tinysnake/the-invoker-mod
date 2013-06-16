package snake.mcmods.theinvoker.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class EnergyContainer extends EnergyUnit
{
	public EnergyContainer(TileEntity te, boolean isEnergyProvider, int containerEnergyID)
	{
		super(te, containerEnergyID);
		this.isEnergyProvider = isEnergyProvider;
	}

	protected int capacity;
	protected boolean isEnergyProvider;
	protected int energyLevel;

	public int gain(int energyFlow, boolean doGain)
	{
		int tobeGain = energyFlow;
		if (energyLevel + tobeGain > capacity)
		{
			tobeGain = capacity - energyLevel;
		}
		if (doGain)
		{
			energyLevel += tobeGain;
			EnergyUtils.fireEvent(new EnergyContainerEvent.EnergyGainedEvent(getTileEntity().worldObj, this, getTileEntity().xCoord, getTileEntity().yCoord, getTileEntity().zCoord, tobeGain));
		}
		return tobeGain;
	}

	public int take(int energyFlow, boolean doTake)
	{
		int tobeTake = energyFlow;
		if (energyLevel - energyFlow < 0)
		{
			tobeTake = energyLevel;
		}
		if (doTake)
		{
			energyLevel -= tobeTake;
			EnergyUtils.fireEvent(new EnergyContainerEvent.EnergyTookEvent(getTileEntity().worldObj, this, getTileEntity().xCoord, getTileEntity().yCoord, getTileEntity().zCoord, tobeTake));
		}
		return tobeTake;
	}

	public int getEnergyCapacity()
	{
		return capacity;
	}

	public void setEnergyCapacity(int capacity)
	{
		this.capacity = capacity;
		if (energyLevel > this.capacity)
			energyLevel = capacity;
	}

	public int getEnergyLevel()
	{
		return energyLevel;
	}

	public void setEnergyLevel(int level)
	{
		energyLevel = level;
	}

	public boolean getIsEnergyProvider()
	{
		return isEnergyProvider;
	}

	@Override
	public void register()
	{
		if (!isRegistered && this.getTileEntity().worldObj != null && !this.getTileEntity().worldObj.isRemote)
			isRegistered = EnergyCenter.INSTANCE.registerContainer(this);
	}

	@Override
	public void destroy()
	{
		EnergyCenter.INSTANCE.removeContainer(this);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		nbt.setInteger(TAG_ENERGY_LEVEL, getEnergyLevel());
		nbt.setInteger(TAG_CAPACITY, getEnergyCapacity());
		nbt.setBoolean(TAG_IS_PROVIDER, getIsEnergyProvider());
		return nbt;
	}

	public static EnergyContainer readFromNBT(NBTTagCompound nbt, TileEntity te)
	{
		EnergyContainer c = new EnergyContainer(te, nbt.getBoolean(TAG_IS_PROVIDER), nbt.getInteger(TAG_ENERGY_ID));
		if (nbt.hasKey(TAG_RANGE))
			c.setEffectiveRange(nbt.getInteger(TAG_RANGE));
		if (nbt.hasKey(TAG_MAX_REQUEST))
			c.setMaxEnergyRequest(nbt.getInteger(TAG_MAX_REQUEST));
		if (nbt.hasKey(TAG_CAPACITY))
			c.setEnergyCapacity(nbt.getInteger(TAG_CAPACITY));
		if (nbt.hasKey(TAG_ENERGY_LEVEL))
			c.setEnergyLevel(nbt.getInteger(TAG_ENERGY_LEVEL));
		if (nbt.hasKey(TAG_IS_AVAILABLE))
			c.setIsAvailable(nbt.getBoolean(TAG_IS_AVAILABLE));
		return c;
	}

	private static final String TAG_CAPACITY = "capacity";
	private static final String TAG_ENERGY_LEVEL = "energyLevel";
	private static final String TAG_IS_PROVIDER = "isProvider";
}
