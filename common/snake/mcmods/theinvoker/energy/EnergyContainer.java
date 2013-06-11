package snake.mcmods.theinvoker.energy;

import net.minecraft.tileentity.TileEntity;

public class EnergyContainer implements IEnergyContainer
{
	public EnergyContainer(TileEntity te, boolean isEnergyProvider,int containerEnergyID)
    {
	    this.te=te;
	    this.isEnergyProvider=isEnergyProvider;
	    this.energyID=containerEnergyID;
	    EnergyLogicCenter.INSTANCE.registerContainer(this);
    }
	protected int effectiveRange;
	protected int maxEnergyRequst;
	protected int capacity;
	protected boolean isEnergyProvider;
	private TileEntity te;
	private int energyID;
	public int energyLevel;
	
	@Override
	public void setEffectiveRange(int range)
	{
		effectiveRange = range;
	}

	@Override
	public int getEffectiveRange()
	{
		return effectiveRange;
	}

	@Override
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

	@Override
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

	@Override
	public int getEnergyCapacity()
	{
		return capacity;
	}

	@Override
	public void setEnergyCapacity(int capacity)
	{
		this.capacity=capacity;
		if(energyLevel>this.capacity)
			energyLevel=capacity;
	}

	@Override
	public int getEnergyLevel()
	{
		return energyLevel;
	}

	@Override
    public void setEnergyLevel(int level)
    {
	    energyLevel=level;
    }

	@Override
	public int getMaxEnergyRequest()
	{
		return maxEnergyRequst;
	}
	@Override
	public void setMaxEnergyRequest(int val)
	{
		maxEnergyRequst=val;
	}

	@Override
	public boolean getIsEnergyProvider()
	{
		return isEnergyProvider;
	}

	@Override
	public TileEntity getTileEntity()
	{
		return te;
	}

	@Override
    public int getContainerEnergyID()
    {
	    return energyID;
    }
}
