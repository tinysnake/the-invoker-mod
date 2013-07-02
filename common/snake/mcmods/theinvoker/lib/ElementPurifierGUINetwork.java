package snake.mcmods.theinvoker.lib;

public enum ElementPurifierGUINetwork
{
	ENERGY_ID,
	PROCESS_TICKS,
	TOTAL_TICKS;
	public static ElementPurifierGUINetwork fromInt(int i)
	{
		if(i>=0&&i<values().length)
		{
			return values()[i];
		}
		return null;
	}
}
