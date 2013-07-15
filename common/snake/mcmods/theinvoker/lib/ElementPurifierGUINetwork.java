package snake.mcmods.theinvoker.lib;

public enum ElementPurifierGUINetwork
{
	ENERGY_ID,
	PROCESS_TICKS,
	TOTAL_TICKS, 
	SOUL_REQUESTING, 
	SOUL_LEVEL, 
	ICE_LEVEL, 
	FIRE_LEVEL, 
	WIND_LEVEL, 
	DARKNESS_LEVEL;
	public static ElementPurifierGUINetwork fromInt(int i)
	{
		if(i>=0&&i<values().length)
		{
			return values()[i];
		}
		return null;
	}
}
