package snake.mcmods.theinvoker.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import snake.mcmods.theinvoker.energy.EnergyConsumer;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.energy.IEnergyConsumerWrapper;
import snake.mcmods.theinvoker.energy.IEnergyContainerWrapper;
import snake.mcmods.theinvoker.energy.TIEnergy;

public class TileElementPurifier extends TileEntity implements IEnergyContainerWrapper, IEnergyConsumerWrapper
{

	public static final int INNER_TANK_CAPACITY = 500;
	public static final int EFFECTIVE_RANGE = 12;
	public static final int MAX_REQUEST = 5;

	public TileElementPurifier()
	{

	}

	private EnergyConsumer energyConsumer;
	private EnergyContainer soulContainer;
	private EnergyContainer fireContainer;
	private EnergyContainer iceContainer;
	private EnergyContainer windContainer;
	private EnergyContainer darknessContainer;

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
		if(soulContainer==null)
		{
			soulContainer = new EnergyContainer(this, true, TIEnergy.soul.getID());
			soulContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			soulContainer.setEffectiveRange(EFFECTIVE_RANGE);
			soulContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if(iceContainer==null)
		{
			iceContainer = new EnergyContainer(this, true, TIEnergy.ice.getID());
			iceContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			iceContainer.setEffectiveRange(EFFECTIVE_RANGE);
			iceContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if(fireContainer==null)
		{
			fireContainer = new EnergyContainer(this, true, TIEnergy.fire.getID());
			fireContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			fireContainer.setEffectiveRange(EFFECTIVE_RANGE);
			fireContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if(windContainer==null)
		{

			windContainer = new EnergyContainer(this, true, TIEnergy.wind.getID());
			windContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			windContainer.setEffectiveRange(EFFECTIVE_RANGE);
			windContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		if(darknessContainer==null)
		{

			darknessContainer = new EnergyContainer(this, true, TIEnergy.darkness.getID());
			darknessContainer.setEnergyCapacity(INNER_TANK_CAPACITY);
			darknessContainer.setEffectiveRange(EFFECTIVE_RANGE);
			darknessContainer.setMaxEnergyRequest(MAX_REQUEST);
		}
		
	}
	
	@Override
	public void updateEntity()
	{
	    super.updateEntity();
	    setupEnergyUnit();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound)
	{
	    super.readFromNBT(par1nbtTagCompound);
	    
	    setupEnergyUnit();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound)
	{
	    super.writeToNBT(par1nbtTagCompound);
	    
	}

	@Override
	public Packet getDescriptionPacket()
	{
		setupEnergyUnit();
		return super.getDescriptionPacket();
	}
}
