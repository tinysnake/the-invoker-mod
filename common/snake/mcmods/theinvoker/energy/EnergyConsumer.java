package snake.mcmods.theinvoker.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class EnergyConsumer extends EnergyUnit
{
	public EnergyConsumer(TileEntity te, int energyConsumerID)
	{
		super(te, energyConsumerID);
	}

	protected int energyRequesting;

	public void requestEnergy(int energy)
	{
		energyRequesting = energy;
		if (energy > 0)
		{
			EnergyUtils.fireEvent(new EnergyConsumerEvent.EnergyRequestedEvent(getTileEntity().worldObj, this, getTileEntity().xCoord, getTileEntity().yCoord, getTileEntity().zCoord, energy));
		}
	}

	public void acceptEnergy(int energyFlow)
	{
		if (energyRequesting > 0)
		{
			int accepted = energyRequesting - energyFlow < 0 ? energyRequesting : energyRequesting - energyFlow;
			energyRequesting -= accepted;
			EnergyUtils.fireEvent(new EnergyConsumerEvent.EnergyAcceptedEvent(getTileEntity().worldObj, this, getTileEntity().xCoord, getTileEntity().yCoord, getTileEntity().zCoord, accepted));
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

	@Override
	public void register()
	{
		if (!isRegistered && this.getTileEntity().worldObj != null && !this.getTileEntity().worldObj.isRemote)
			isRegistered = EnergyCenter.INSTANCE.registerConsumer(this);
	}

	@Override
	public void destroy()
	{
		EnergyCenter.INSTANCE.removeConsumer(this);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		nbt.setInteger(TAG_ENERGY_REQUESTING, getEnergyIsRequesting());
		return nbt;
	}

	public static EnergyConsumer readFromNBT(NBTTagCompound nbt, TileEntity te)
	{
		EnergyConsumer ec = new EnergyConsumer(te, nbt.getInteger("energyId"));
		if (nbt.hasKey(TAG_RANGE))
			ec.setEffectiveRange(nbt.getInteger(TAG_RANGE));
		if (nbt.hasKey(TAG_MAX_REQUEST))
			ec.setMaxEnergyRequest(nbt.getInteger(TAG_MAX_REQUEST));
		if (nbt.hasKey(TAG_ENERGY_REQUESTING))
			ec.requestEnergy(nbt.getInteger(TAG_ENERGY_REQUESTING));
		if (nbt.hasKey(TAG_IS_AVAILABLE))
			ec.setIsAvailable(nbt.getBoolean(TAG_IS_AVAILABLE));
		return ec;
	}

	private static final String TAG_ENERGY_REQUESTING = "energyRequesting";
}
