package snake.mcmods.theinvoker.energy;

import net.minecraft.tileentity.TileEntity;

public interface IEnergyContainer
{
	int getContainerEnergyID();
	int getEffectiveRange();
	void setEffectiveRange(int range);
	int gain(int energyFlow,boolean doGain);
	int take(int energyFlow, boolean doTake);
	int getEnergyCapacity();
	void setEnergyCapacity(int capacity);
	int getEnergyLevel();
	void setEnergyLevel(int level);
	int getMaxEnergyRequest();
	void setMaxEnergyRequest(int max);
	boolean getIsEnergyProvider();
	TileEntity getTileEntity();
	
}
