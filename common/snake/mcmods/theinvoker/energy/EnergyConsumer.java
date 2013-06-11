package snake.mcmods.theinvoker.energy;

import net.minecraft.tileentity.TileEntity;

public class EnergyConsumer
{
	public EnergyConsumer(TileEntity te, int energyConsumerID)
    {
		if(te==null)
			throw new IllegalArgumentException("you dare to create a EnergyConsumer without a valid TileEntity?");
	    this.te=te;
	    if(EnergyCenter.INSTANCE.getEnergyForce(energyConsumerID)!=null)
		    this.energyID=energyConsumerID;
	    else
	    	throw new IllegalArgumentException("you have created a EnergyConsumer without a valid Energy ID");
	    EnergyCenter.INSTANCE.registerConsumer(this);
    }
	protected int maxEnergyRequest;
	protected int energyRequesting;
	private TileEntity te;
	private int energyID;

	public int getConsumerEnergyID()
	{
		return energyID;
	}

	public int getMaxEnergyRequest()
	{
		return maxEnergyRequest;
	}

	public void setMaxEnergyRequest(int val)
	{
		maxEnergyRequest = val;
	}

	public void requestEnergy(int energy)
	{
		energyRequesting = energy;
		if (energy > 0)
		{
			EnergyUtils.fireEvent(new EnergyConsumerEvent.EnergyRequestedEvent(te.worldObj, this, te.xCoord, te.yCoord, te.zCoord, energy));
		}
	}

	public void acceptEnergy(int energyFlow)
	{
		if (energyRequesting > 0)
		{
			int accepted = energyRequesting - energyFlow < 0 ? energyRequesting : energyRequesting - energyFlow;
			energyRequesting -= accepted;
			EnergyUtils.fireEvent(new EnergyConsumerEvent.EnergyAcceptedEvent(te.worldObj, this, te.xCoord, te.yCoord, te.zCoord, accepted));
		}
	}

	public int getEnergyIsRequesting()
	{
		return energyRequesting;
	}

	public boolean getIsRequestingEnergy()
	{
		return energyRequesting > 0;
	}

	public TileEntity getTileEntity()
	{
		return te;
	}

	public void destroy()
	{
		EnergyCenter.INSTANCE.removeConsumer(this);
	}

}
