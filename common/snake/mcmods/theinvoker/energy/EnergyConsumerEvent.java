package snake.mcmods.theinvoker.energy;

import net.minecraft.world.World;
import net.minecraftforge.event.Event;

public class EnergyConsumerEvent extends Event
{
	public EnergyConsumerEvent(World w, EnergyConsumer c, int x, int y, int z)
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
	public EnergyConsumer consumer;
	
	public static class EnergyRequestedEvent extends EnergyConsumerEvent
	{
		public EnergyRequestedEvent(World w, EnergyConsumer c, int x, int y, int z, int level)
        {
	        super(w, c, x, y, z);
	        energyLevel=level;
        }
		public final int energyLevel;
	}
	
	public static class EnergyAcceptedEvent extends EnergyConsumerEvent
	{
		public EnergyAcceptedEvent(World w, EnergyConsumer c, int x, int y, int z, int level)
        {
	        super(w, c, x, y, z);
	        energyLevel=level;
        }
		public final int energyLevel;
	}
}
