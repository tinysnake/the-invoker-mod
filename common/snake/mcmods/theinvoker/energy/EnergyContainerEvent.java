package snake.mcmods.theinvoker.energy;

import net.minecraft.world.World;
import net.minecraftforge.event.Event;

public class EnergyContainerEvent extends Event
{
	public EnergyContainerEvent(World w, IEnergyContainer c,int x,int y,int z)
	{
		this.world=w;
		this.container=c;
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	
	public final IEnergyContainer container;
	public final int x;
	public final int y;
	public final int z;
	public final World world;
	
	
	public static class EnergyContainerPlacedEvent extends EnergyContainerEvent
	{
		public EnergyContainerPlacedEvent(World w, IEnergyContainer c, int x, int y, int z)
        {
	        super(w, c, x, y, z);
        }
	}

	public static class EnergyContainerDestoryedEvent extends EnergyContainerEvent
	{
		public EnergyContainerDestoryedEvent(World w, IEnergyContainer c, int x, int y, int z)
        {
	        super(w, c, x, y, z);
        }
	}
	
	public static class EnergyGainedEvent extends EnergyContainerEvent
	{
		public EnergyGainedEvent(World w, IEnergyContainer c, int x, int y, int z,int level)
        {
	        super(w, c, x, y, z);
	        energyLevel = level;
        }
		public final int energyLevel;
	}
	
	public static class EnergyTookEvent extends EnergyContainerEvent
	{
		public EnergyTookEvent(World w, IEnergyContainer c, int x, int y, int z,int level)
        {
	        super(w, c, x, y, z);
	        energyLevel = level;
		}

		public final int energyLevel;
	}
}
