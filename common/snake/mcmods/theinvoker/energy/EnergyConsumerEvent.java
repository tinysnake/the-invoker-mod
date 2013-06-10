package snake.mcmods.theinvoker.energy;

import net.minecraft.world.World;
import net.minecraftforge.event.Event;

public class EnergyConsumerEvent extends Event
{
	public EnergyConsumerEvent(World w, IEnergyConsumer c, int x, int y, int z)
	{
		world = w;
		consumer = c;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int x;
	public int y;
	public int z;
	public World world;
	public IEnergyConsumer consumer;
	
	public static class EnergyConsumerPlacedEvent extends EnergyConsumerEvent
	{
		public EnergyConsumerPlacedEvent(World w, IEnergyConsumer c, int x, int y, int z)
        {
	        super(w, c, x, y, z);
        }
	}
	
	public static class EnergyConsumerDestoryedEvent extends EnergyConsumerEvent
	{
		public EnergyConsumerDestoryedEvent(World w, IEnergyConsumer c, int x, int y, int z)
        {
	        super(w, c, x, y, z);
        }
	}
	
	public static class EnergyAcceptedEvent extends EnergyConsumerEvent
	{
		public EnergyAcceptedEvent(World w, IEnergyConsumer c, int x, int y, int z)
        {
	        super(w, c, x, y, z);
        }
	}
}
