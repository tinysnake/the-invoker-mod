package snake.mcmods.theinvoker.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class EnergyContainer
{
	public EnergyContainer(TileEntity te, boolean isEnergyProvider, int containerEnergyID)
	{
		if (te == null)
			throw new IllegalArgumentException("you dare to create a EnergyContainer without a valid TileEntity?");
		this.te = te;
		this.isEnergyProvider = isEnergyProvider;
		if (EnergyCenter.INSTANCE.getEnergyForce(containerEnergyID) != null)
			this.energyID = containerEnergyID;
		else
			throw new IllegalArgumentException("you have created a EnergyContainer without a valid Energy ID");
	}

	protected int effectiveRange;
	protected int maxEnergyRequst;
	protected int capacity;
	protected boolean isEnergyProvider;
	protected int energyLevel;
	private TileEntity te;
	private int energyID;
	private boolean isRegistered;
	
	public boolean getIsRegistered()
	{
		return isRegistered;
	}

	public void setEffectiveRange(int range)
	{
		effectiveRange = range;
	}

	public int getEffectiveRange()
	{
		return effectiveRange;
	}

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
			EnergyUtils.fireEvent(new EnergyContainerEvent.EnergyGainedEvent(te.worldObj, this, te.xCoord, te.yCoord, te.zCoord, tobeGain));
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
			EnergyUtils.fireEvent(new EnergyContainerEvent.EnergyTookEvent(te.worldObj, this, te.xCoord, te.yCoord, te.zCoord, tobeTake));
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

	public int getMaxEnergyRequest()
	{
		return maxEnergyRequst;
	}

	public void setMaxEnergyRequest(int val)
	{
		maxEnergyRequst = val;
	}

	public boolean getIsEnergyProvider()
	{
		return isEnergyProvider;
	}

	public TileEntity getTileEntity()
	{
		return te;
	}

	public int getContainerEnergyID()
	{
		return energyID;
	}
	
	public void register()
	{
		if(!isRegistered)
			isRegistered = EnergyCenter.INSTANCE.registerContainer(this);
	}

	public void destroy()
	{
		EnergyCenter.INSTANCE.removeContainer(this);
	}

	public NBTTagCompound WriteToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("range", getEffectiveRange());
		nbt.setInteger("maxRequest", getMaxEnergyRequest());
		nbt.setInteger("capacity", getEnergyCapacity());
		nbt.setInteger("energyLevel", getEnergyLevel());
		nbt.setInteger("energyId", getContainerEnergyID());
		nbt.setBoolean("isProvider", getIsEnergyProvider());
		return nbt;
	}

	public static EnergyContainer ReadFromNBT(NBTTagCompound nbt, TileEntity te)
	{
		EnergyContainer c = new EnergyContainer(te, nbt.getBoolean("isProvider"), nbt.getInteger("energyId"));
		c.setEffectiveRange(nbt.getInteger("range"));
		c.setMaxEnergyRequest(nbt.getInteger("maxReuest"));
		c.setEnergyCapacity(nbt.getInteger("capacity"));
		c.setEnergyLevel(nbt.getInteger("energyLevel"));
		return c;
	}
}
