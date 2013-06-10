package snake.mcmods.theinvoker.energy;

import net.minecraft.tileentity.TileEntity;

public interface IEnergyContainer
{
	int getContainerEnergyID();
	int getEffectiveRange();
	int gain(int energyFlow,boolean doGain);
	int take(int energyFlow, boolean doTake);
	int getEnergyCapacity();
	void setEnergyCapacity(int capacity);
	int getEnergyLevel();
	int getMaxEnergyRequest();
	boolean getIsEnergyProvider();
	TileEntity getTileEntity();
	
}
