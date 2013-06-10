package snake.mcmods.theinvoker.energy;

public interface IEnegeryBridge extends IEnergyContainer
{
	IEnergyContainer getTargetContainer();
	void setTargetContainer(IEnergyContainer container);
}
