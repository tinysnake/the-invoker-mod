package snake.mcmods.theinvoker.energy;

import java.util.ArrayList;
import java.util.EnumSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Unload;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketEnergyConsumerUpdate;
import snake.mcmods.theinvoker.net.packet.PacketEnergyContainerUpdate;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class EnergyCenter implements ITickHandler
{
	public static final EnergyCenter INSTANCE = new EnergyCenter();
	private static int energyID = 1;

	public EnergyCenter()
	{
		containerNodes = new ArrayList<EnergyContainer>();
		consumerNodes = new ArrayList<EnergyConsumer>();
		energyForceRegistry = new ArrayList<EnergyForce>();
	}

	private ArrayList<EnergyContainer> containerNodes;
	private ArrayList<EnergyConsumer> consumerNodes;
	private ArrayList<EnergyForce> energyForceRegistry;
	private int updateTick;

	@ForgeSubscribe
	public void onWorldUnloaded(Unload e)
	{
		if (e.world.provider.dimensionId == 0)
		{
			containerNodes.clear();
			consumerNodes.clear();
		}
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		EnergyCenter.INSTANCE.updateEnergyFlow();
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{

	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel()
	{
		return  TIGlobal.MOD_ID + ":"+this.getClass().getSimpleName();
	}

	public boolean registerContainer(EnergyContainer energyContainer)
	{
		if (energyContainer != null && energyContainer.getTileEntity() != null && containerNodes.indexOf(energyContainer) < 0)
		{
			containerNodes.add(energyContainer);
			return true;
		}
		return false;
	}

	public void removeContainer(EnergyContainer energyContainer)
	{
		int index = containerNodes.indexOf(energyContainer);
		if (index >= 0)
			containerNodes.remove(index);
	}

	public boolean registerConsumer(EnergyConsumer consumer)
	{
		if (consumer != null && consumer.getTileEntity() != null && consumerNodes.indexOf(consumer) < 0)
		{
			consumerNodes.add(consumer);
			return true;
		}
		return false;
	}

	public void removeConsumer(EnergyConsumer consumer)
	{
		int index = consumerNodes.indexOf(consumer);
		if (index >= 0)
			consumerNodes.remove(index);
	}

	public int registerEnergyForce(EnergyForce force)
	{
		if (force != null && energyForceRegistry.indexOf(force) < 0)
		{
			int validID = getAvailableEnergyID(force.getID());
			force.id = validID;
			energyForceRegistry.add(force);
			return force.getID();
		}
		return -1;
	}

	public EnergyForce getEnergyForce(int id)
	{
		for (EnergyForce ef : energyForceRegistry)
		{
			if (ef.getID() == id)
				return ef;
		}
		return null;
	}

	public void updateEnergyFlow()
	{
		updateTick--;
		for (EnergyContainer c : containerNodes)
		{
			EnergyContainer nearest = getNearestAvailableContainer(c, false);
			evenTheEnergyLevelBetween2Containers(c, nearest);
			pullEnergyFromProvider(c, nearest);
			if (updateTick <= 0)
			{
				TileEntity te = c.getTileEntity();
				PacketDispatcher.sendPacketToAllAround(te.xCoord, te.yCoord, te.zCoord, 128, te.worldObj.provider.dimensionId,
						PacketTypeHandler.serialize(new PacketEnergyContainerUpdate(te.xCoord, te.yCoord, te.zCoord,
								c.isAvailable, c.getEnergyLevel(), c.getEnergyCapacity(), c.getMaxEnergyRequest(), c.getEffectiveRange())));
			}
		}

		for (EnergyConsumer c : consumerNodes)
		{
			EnergyContainer container = getNearestAvailableContainer(c, true);
			applyEnergyToConsumer(container, c);
			if (updateTick <= 0)
			{
				TileEntity te = c.getTileEntity();
				PacketDispatcher.sendPacketToAllAround(te.xCoord, te.yCoord, te.zCoord, 128, te.worldObj.provider.dimensionId,
						PacketTypeHandler.serialize(new PacketEnergyConsumerUpdate(te.xCoord, te.yCoord, te.zCoord,
								c.isAvailable, c.getEnergyIsRequesting(), c.getMaxEnergyRequest())));
			}
		}
		if (updateTick <= 0)
			updateTick = 5;
	}

	public EnergyContainer getNearestAvailableContainer(EnergyUnit unit, boolean includingSelf)
	{
		if (unit.getTileEntity() != null)
		{
			return getNearestAvailableContainer(unit.getTileEntity(), unit.getEnergyID(), includingSelf);
		}
		return null;
	}

	public EnergyContainer getNearestAvailableContainer(TileEntity te, int energyID, boolean includingSelf)
	{
		double lastDistance = Integer.MAX_VALUE;
		EnergyContainer lastContainer = null;
		for (EnergyContainer c : containerNodes)
		{
			if (c.getTileEntity() == null)
				continue;
			if (!includingSelf && te == c.getTileEntity())
				continue;
			if (!c.getIsAvailable())
				continue;
			if (c.getEnergyID() != energyID)
				continue;
			if (te.worldObj.provider.dimensionId != c.getTileEntity().worldObj.provider.dimensionId)
				continue;
			double distance = getDistanceSqBetweenEnergyUnits(te, c.getTileEntity());
			if (distance < c.getEffectiveRangeSq() && lastDistance > distance && c.getEnergyLevel() > 0)
			{
				lastDistance = distance;
				lastContainer = c;
			}
		}
		return lastContainer;
	}

	public double getDistanceSqBetweenEnergyUnits(TileEntity te1, TileEntity te2)
	{
		if (te1 == null || te2 == null)
			return Integer.MAX_VALUE;
		int x1 = te1.xCoord;
		int y1 = te1.yCoord;
		int z1 = te1.zCoord;
		int x2 = te2.xCoord;
		int y2 = te2.yCoord;
		int z2 = te2.zCoord;
		if (te1 instanceof IMultiblockEnergyWrapper)
		{
			int[] coords = ((IMultiblockEnergyWrapper)te1).getCloestCoordsTo(x2, y2, z2);
			x1 = coords[0];
			y1 = coords[1];
			z1 = coords[2];
		}
		if (te2 instanceof IMultiblockEnergyWrapper)
		{
			int[] coords = ((IMultiblockEnergyWrapper)te2).getCloestCoordsTo(x1, y1, z1);
			x2 = coords[0];
			y2 = coords[1];
			z2 = coords[2];
		}
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;
		return dx * dx + dy * dy + dz * dz;
	}

	private void evenTheEnergyLevelBetween2Containers(EnergyContainer c1, EnergyContainer c2)
	{
		if (c1 == null || c2 == null || !c1.getIsAvailable() || !c2.getIsAvailable() || c1.getIsEnergyProvider() || c2.getIsEnergyProvider())
			return;
		EnergyContainer toGain = c1.getEnergyLevel() > c2.getEnergyLevel() ? c2 : c1;
		EnergyContainer toTake = toGain == c1 ? c2 : c1;
		int expectedFlow = Math.min(toGain.getMaxEnergyRequest(), MathHelper.abs_int(c1.getEnergyLevel() - c2.getEnergyLevel()));
		int actualFlow = Math.min(toTake.take(expectedFlow, false), toGain.gain(expectedFlow, false));
		if (actualFlow > 0)
		{
			toGain.gain(toTake.take(actualFlow, true), true);
		}
	}

	private void pullEnergyFromProvider(EnergyContainer c1, EnergyContainer c2)
	{
		if (c1 == null || c2 == null || !c1.getIsAvailable() || !c2.getIsAvailable() || (!c1.getIsEnergyProvider() && !c2.getIsEnergyProvider()) || (c1.getIsEnergyProvider() && c2.getIsEnergyProvider()) || c1.getEnergyID() != c2.getEnergyID())
			return;
		EnergyContainer provider = c1.getIsEnergyProvider() ? c1 : c2;
		EnergyContainer container = c1.getIsEnergyProvider() ? c2 : c1;
		int expectedFlow = Math.min(container.getMaxEnergyRequest(), provider.getEnergyLevel());
		int actualFlow = Math.min(container.gain(expectedFlow, false), provider.take(expectedFlow, false));
		if (actualFlow > 0)
		{
			container.gain(provider.take(actualFlow, true), true);
		}
	}

	private void applyEnergyToConsumer(EnergyContainer container, EnergyConsumer consumer)
	{
		if (container == null || consumer == null || !container.getIsAvailable() || !consumer.getIsAvailable() || !consumer.getIsRequestingEnergy() || consumer.getEnergyID() != container.getEnergyID())
			return;
		int expectedFlow = Math.min(container.getEnergyLevel(), consumer.getMaxEnergyRequest());
		int actualFlow = container.take(expectedFlow, false);
		if (actualFlow > 0)
		{
			consumer.acceptEnergy(container.take(actualFlow, true));
		}
	}

	private int getAvailableEnergyID(int defaultID)
	{
		ArrayList<Integer> ids = new ArrayList<Integer>(energyForceRegistry.size());
		for (EnergyForce ef : energyForceRegistry)
		{
			ids.add(ef.getID());
		}
		if (ids.indexOf(defaultID) < 0)
			return defaultID;
		while (ids.indexOf(energyID) >= 0)
			energyID++;
		return energyID;
	}
}
