package snake.mcmods.theinvoker.energy;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class EnergyLogicCenter
{
	public static final EnergyLogicCenter INSTANCE = new EnergyLogicCenter();

	public EnergyLogicCenter()
	{
		containerNodes = new ArrayList<IEnergyContainer>();
		consumerNodes = new ArrayList<IEnergyConsumer>();
	}

	private ArrayList<IEnergyContainer> containerNodes;
	private ArrayList<IEnergyConsumer> consumerNodes;

	public void registerContainer(IEnergyContainer energyContainer)
	{
		if (energyContainer != null && energyContainer.getTileEntity() != null && containerNodes.indexOf(energyContainer) < 0)
		{
			containerNodes.add(energyContainer);
		}
	}

	public void removeContainer(IEnergyContainer energyContainer)
	{
		int index = containerNodes.indexOf(energyContainer);
		if (index >= 0)
			containerNodes.remove(index);
	}

	public void registerConsumer(IEnergyConsumer consumer)
	{
		if (consumer != null && consumer.getTileEntity() != null && consumerNodes.indexOf(consumer) < 0)
		{
			consumerNodes.add(consumer);
		}
	}

	public void removeConsumer(IEnergyConsumer consumer)
	{
		int index = consumerNodes.indexOf(consumer);
		if (index >= 0)
			consumerNodes.remove(index);
	}

	public void updateEnergyFlow()
	{
		for (IEnergyContainer c : containerNodes)
		{
			IEnergyContainer nearest = getNearestAvailableContainer(c, false);
			evenTheEnergyLevelBetween2Containers(c, nearest);
			pullEnergyFromProvider(c, nearest);
		}

		for (IEnergyConsumer c : consumerNodes)
		{
			IEnergyContainer container = getNearestAvailableContainer(c, true);
			applyEnergyToConsumer(container, c);
		}
	}

	public IEnergyContainer getNearestAvailableContainer(IEnergyConsumer consumer, boolean includingSelf)
	{
		if (consumer.getTileEntity() != null)
		{
			return getNearestAvailableContainer(consumer.getTileEntity(), consumer.getConsumerEnergyID(), includingSelf);
		}
		return null;
	}

	public IEnergyContainer getNearestAvailableContainer(IEnergyContainer container, boolean includingSelf)
	{
		if (container.getTileEntity() != null)
		{
			return getNearestAvailableContainer(container.getTileEntity(), container.getContainerEnergyID(), includingSelf);
		}
		return null;
	}

	public IEnergyContainer getNearestAvailableContainer(TileEntity te, int energyID, boolean includingSelf)
	{
		double lastDistance = Integer.MAX_VALUE;
		IEnergyContainer lastContainer = null;
		for (IEnergyContainer c : containerNodes)
		{
			if (c.getTileEntity() == null)
				continue;
			if (!includingSelf && te == c.getTileEntity())
				continue;
			if (c.getContainerEnergyID() != energyID)
				continue;
			double distance = EnergyUtils.getDistanceBetweenTiles(te, c.getTileEntity());
			if (distance < c.getEffectiveRange() && lastDistance > distance && c.getEnergyLevel() > 0)
			{
				lastDistance = distance;
				lastContainer = c;
			}
		}
		return lastContainer;
	}

	private void evenTheEnergyLevelBetween2Containers(IEnergyContainer c1, IEnergyContainer c2)
	{
		if (c1 == null || c2 == null || c1.getIsEnergyProvider() || c2.getIsEnergyProvider())
			return;
		IEnergyContainer toGain = c1.getEnergyLevel() > c2.getEnergyLevel() ? c2 : c1;
		IEnergyContainer toTake = toGain == c1 ? c2 : c1;
		int expectedFlow = Math.min(toGain.getMaxEnergyRequest(), MathHelper.abs_int(c1.getEnergyLevel() - c2.getEnergyLevel()));
		int actualFlow = Math.min(toTake.take(expectedFlow, false), toGain.gain(expectedFlow, false));
		if (actualFlow > 0)
		{
			toGain.gain(toTake.take(actualFlow, true), true);
		}
	}

	private void pullEnergyFromProvider(IEnergyContainer c1, IEnergyContainer c2)
	{
		if (c1 == null || c2 == null ||
		        (!c1.getIsEnergyProvider() && !c2.getIsEnergyProvider()) ||
		        (c1.getIsEnergyProvider() && c2.getIsEnergyProvider()) ||
		        c1.getContainerEnergyID() != c2.getContainerEnergyID())
			return;
		IEnergyContainer provider = c1.getIsEnergyProvider() ? c1 : c2;
		IEnergyContainer container = c1.getIsEnergyProvider() ? c2 : c1;
		int expectedFlow = Math.min(container.getMaxEnergyRequest(), provider.getEnergyLevel());
		int actualFlow = Math.min(container.gain(expectedFlow, false), provider.take(expectedFlow, false));
		if (actualFlow > 0)
		{
			container.gain(provider.take(actualFlow, true), true);
		}
	}

	private void applyEnergyToConsumer(IEnergyContainer container, IEnergyConsumer consumer)
	{
		if (container == null || consumer == null || !consumer.getIsRequestingEnergy() || consumer.getConsumerEnergyID() != container.getContainerEnergyID())
			return;
		int expectedFlow = Math.min(container.getEnergyLevel(), consumer.getMaxEnergyRequest());
		int actualFlow = container.take(expectedFlow, false);
		if (actualFlow > 0)
		{
			consumer.acceptEnergy(container.take(actualFlow, true));
		}
	}
}
