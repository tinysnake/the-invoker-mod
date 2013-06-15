package snake.mcmods.theinvoker.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.energy.IEnergyContainerWrapper;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.logic.SoulStoneMisc;

public class TileSoulStone extends TileTIBase implements IEnergyContainerWrapper
{

	public TileSoulStone()
	{
		setDirection(0);
	}

	private EnergyContainer energyContainer;

	@Override
	public EnergyContainer getEnergyContainer()
	{
		return energyContainer;
	}
	
	@Override
	public void updateEntity()
	{
	    super.updateEntity();
	    setupEnergyContainer();
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

	@Override
	public void readFromNBT(NBTTagCompound nbtCompound)
	{
		super.readFromNBT(nbtCompound);
		if(nbtCompound.hasKey(TAG_ENERGY_CONTAINER))
			energyContainer = EnergyContainer.readFromNBT(nbtCompound.getCompoundTag(TAG_ENERGY_CONTAINER), this);
		setupEnergyContainer();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtCompound)
	{
		super.writeToNBT(nbtCompound);
		if (energyContainer != null)
		{
			nbtCompound.setTag(TAG_ENERGY_CONTAINER, energyContainer.writeToNBT(new NBTTagCompound()));
		}
	}
	
	public void transferFrom(TileSoulStone tss)
	{
		this.energyContainer = tss.getEnergyContainer();
	}

	private void setupEnergyContainer()
	{
		if (energyContainer == null)
		{
			energyContainer = new EnergyContainer(this, false, TIItems.soulShard.itemID);
			energyContainer.setEffectiveRange(SoulStoneMisc.EFFECTIVE_RANGE);
			energyContainer.setEnergyCapacity(SoulStoneMisc.CAPACITY_OF_METADATA[getBlockMetadata()]);
		}
		if (!energyContainer.getIsRegistered())
		{
			energyContainer.register();
		}
	}

	private static final String TAG_ENERGY_CONTAINER = "energyContainer";
}
