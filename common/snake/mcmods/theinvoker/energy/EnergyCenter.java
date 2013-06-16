package snake.mcmods.theinvoker.energy;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketEnergyConsumerUpdate;
import snake.mcmods.theinvoker.net.packet.PacketEnergyContainerUpdate;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.common.network.PacketDispatcher;

public class EnergyCenter
{
    public static final EnergyCenter INSTANCE = new EnergyCenter();

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

    public void registerEnergyForce(EnergyForce force)
    {
        if (force != null && energyForceRegistry.indexOf(force) < 0)
            energyForceRegistry.add(force);
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
                PacketDispatcher.sendPacketToAllAround(te.xCoord, te.yCoord, te.zCoord, 128, te.worldObj.provider.dimensionId, PacketTypeHandler.serialize(new PacketEnergyContainerUpdate(te.xCoord, te.yCoord, te.zCoord, c.getEnergyLevel(), c.getEnergyCapacity(), c.getMaxEnergyRequest(), c.getEffectiveRange())));
            }
        }

        for (EnergyConsumer c : consumerNodes)
        {
            EnergyContainer container = getNearestAvailableContainer(c, true);
            applyEnergyToConsumer(container, c);
            if (updateTick <= 0)
            {
                TileEntity te = c.getTileEntity();
                PacketDispatcher.sendPacketToAllAround(te.xCoord, te.yCoord, te.zCoord, 64, te.worldObj.provider.dimensionId, PacketTypeHandler.serialize(new PacketEnergyConsumerUpdate(te.xCoord, te.yCoord, te.zCoord, c.getEnergyIsRequesting(), c.getMaxEnergyRequest())));
            }
        }
        if (updateTick <= 0)
            updateTick = 5;
    }

    public EnergyContainer getNearestAvailableContainer(EnergyConsumer consumer, boolean includingSelf)
    {
        if (consumer.getTileEntity() != null)
        {
            return getNearestAvailableContainer(consumer.getTileEntity(), consumer.getEnergyID(), includingSelf);
        }
        return null;
    }

    public EnergyContainer getNearestAvailableContainer(EnergyContainer container, boolean includingSelf)
    {
        if (container.getTileEntity() != null)
        {
            return getNearestAvailableContainer(container.getTileEntity(), container.getEnergyID(), includingSelf);
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
            double distance = Utils.getDistanceBetweenTiles(te, c.getTileEntity());
            if (distance < c.getEffectiveRange() * c.getEffectiveRange() && lastDistance > distance && c.getEnergyLevel() > 0)
            {
                lastDistance = distance;
                lastContainer = c;
            }
        }
        return lastContainer;
    }

    private void evenTheEnergyLevelBetween2Containers(EnergyContainer c1, EnergyContainer c2)
    {
        if (c1 == null || c2 == null || c1.getIsEnergyProvider() || c2.getIsEnergyProvider())
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
        if (c1 == null || c2 == null || (!c1.getIsEnergyProvider() && !c2.getIsEnergyProvider()) || (c1.getIsEnergyProvider() && c2.getIsEnergyProvider()) || c1.getEnergyID() != c2.getEnergyID())
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
        if (container == null || consumer == null || !consumer.getIsRequestingEnergy() || consumer.getEnergyID() != container.getEnergyID())
            return;
        int expectedFlow = Math.min(container.getEnergyLevel(), consumer.getMaxEnergyRequest());
        int actualFlow = container.take(expectedFlow, false);
        if (actualFlow > 0)
        {
            consumer.acceptEnergy(container.take(actualFlow, true));
        }
    }
}
