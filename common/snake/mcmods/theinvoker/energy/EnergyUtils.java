package snake.mcmods.theinvoker.energy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import snake.mcmods.theinvoker.net.packet.PacketEnergyConsumerUpdate;
import snake.mcmods.theinvoker.net.packet.PacketEnergyContainerUpdate;

public class EnergyUtils
{
	public static void fireEvent(Event event)
	{
		MinecraftForge.EVENT_BUS.post(event);
	}

	public static void syncDataFromPacket(PacketEnergyContainerUpdate p, EntityPlayer player)
	{
		World world = player.worldObj;
		TileEntity te = world.getBlockTileEntity(p.x, p.y, p.z);
		if (te != null && te instanceof IEnergyContainerWrapper)
		{
			EnergyContainer c = ((IEnergyContainerWrapper)te).getEnergyContainer();
			if (c != null)
			{
				c.setIsAvailable(p.available);
				c.setEnergyLevel(p.energyLevel);
				c.setEnergyCapacity(p.capacity);
				c.setMaxEnergyRequest(p.maxRequest);
				c.setEffectiveRange(p.range);
			}
		}
	}

	public static void syncDataFromePacket(PacketEnergyConsumerUpdate p, EntityPlayer player)
	{
		World world = player.worldObj;
		TileEntity te = world.getBlockTileEntity(p.x, p.y, p.z);
		if (te != null && te instanceof IEnergyConsumerWrapper)
		{
			EnergyConsumer c = ((IEnergyConsumerWrapper)te).getEnergyConsumer();
			if (c != null)
			{
				c.setIsAvailable(p.available);
				c.setMaxEnergyRequest(p.maxRequest);
				c.requestEnergy(p.energyLevel);
			}
		}
	}
	
	public static EnergyContainer containerTransferToTE(TileEntity te, EnergyContainer c)
	{
		EnergyContainer nc = new EnergyContainer(te, c.getIsEnergyProvider(), c.getEnergyID());
		nc.setEffectiveRange(c.getEffectiveRange());
		nc.setEnergyCapacity(c.getEnergyCapacity());
		nc.setEnergyLevel(c.getEnergyLevel());
		nc.setIsAvailable(c.getIsAvailable());
		nc.setMaxEnergyRequest(c.getMaxEnergyRequest());
		return nc;
	}
	
	public static EnergyConsumer consumerTransferToTE(TileEntity te, EnergyConsumer c)
	{
		EnergyConsumer nc = new EnergyConsumer(te, c.getEnergyID());
		nc.effectiveRange=c.effectiveRange;
		nc.energyRequesting=c.energyRequesting;
		nc.isAvailable=c.isAvailable;
		nc.maxEnergyRequest = c.maxEnergyRequest;
		return nc;
	}
}
