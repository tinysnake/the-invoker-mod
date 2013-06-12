package snake.mcmods.theinvoker.energy;

import net.minecraft.nbt.NBTTagCompound;
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
    }
	protected int maxEnergyRequest;
	protected int energyRequesting;
	private TileEntity te;
	private int energyID;
	private boolean isRegistered;
	
	public boolean getIsRegister()
	{
		return isRegistered;
	}

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
	
	public void register()
	{
	    isRegistered = EnergyCenter.INSTANCE.registerConsumer(this);
	}

	public void destroy()
	{
		EnergyCenter.INSTANCE.removeConsumer(this);
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("energyId",getConsumerEnergyID());
		nbt.setInteger("maxRequest", getMaxEnergyRequest());
		nbt.setInteger("energyRequesting", getEnergyIsRequesting());
	}
	
	public static EnergyConsumer readFromNBT(NBTTagCompound nbt,TileEntity te)
	{
		EnergyConsumer ec= new EnergyConsumer(te, nbt.getInteger("energyId"));
		ec.setMaxEnergyRequest(nbt.getInteger("maxRequest"));
		ec.requestEnergy(nbt.getInteger("energyRequesting"));
		return ec;
	}
}
