package snake.mcmods.theinvoker.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;

public class EnergyUtils
{
	public static void fireEvent(Event event)
	{
		MinecraftForge.EVENT_BUS.post(event);
	}

	public static double getDistanceBetweenTiles(TileEntity te1, TileEntity te2)
    {
		if(te1!=null && te2!=null)
			return te1.getDistanceFrom(te2.xCoord, te2.yCoord, te2.zCoord);
	    return Integer.MAX_VALUE;
    }
}
