package snake.mcmods.theinvoker.energy;

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class EnergyTickHandler implements ITickHandler
{
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
	    return "EnergyLogicCenter";
    }
}
