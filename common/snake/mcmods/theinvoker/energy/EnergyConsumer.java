package snake.mcmods.theinvoker.energy;

import net.minecraft.tileentity.TileEntity;

public class EnergyConsumer implements IEnergyConsumer
{
	protected int maxEnergyRequest;
	private TileEntity te;
	private int energyID;
	public int energyRequesting;
	
	@Override
	public int getConsumerEnergyID()
	{
		return energyID;
	}

	@Override
	public int getMaxEnergyRequest()
	{
		return maxEnergyRequest;
	}
	
	public void setMaxEnergyRequest(int val)
	{
		maxEnergyRequest = val;
	}
	
	@Override
	public void requestEnergy(int energy)
	{
		energyRequesting=energy;
	}

	@Override
	public void acceptEnergy(int energyFlow)
	{
		energyRequesting = Math.max(energyRequesting-energyFlow,0);
	}

	@Override
    public int getEnergyIsRequesting()
    {
	    return energyRequesting;
    }

	@Override
	public boolean getIsRequestingEnergy()
	{
		return energyRequesting>0;
	}

	@Override
	public TileEntity getTileEntity()
	{
		return te;
	}

}
