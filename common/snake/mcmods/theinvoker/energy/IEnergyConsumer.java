package snake.mcmods.theinvoker.energy;

import net.minecraft.tileentity.TileEntity;

public interface IEnergyConsumer
{
	int getConsumerEnergyID();
	int getMaxEnergyRequest();
	void requestEnergy(int energy);
	void acceptEnergy(int energyFlow);
	int getEnergyIsRequesting();
	boolean getIsRequestingEnergy();
	TileEntity getTileEntity();
}
