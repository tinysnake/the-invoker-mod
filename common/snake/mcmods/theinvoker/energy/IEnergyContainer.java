package snake.mcmods.theinvoker.energy;

public interface IEnergyContainer
{
	int getEffectiveRange();
	int gain(int quantity,boolean doGain);
	int take(int quantity, boolean doTake);
	int getEnergyCapacity();
	int setEnergyCapacity();
	int getEnergyAmount();
}
