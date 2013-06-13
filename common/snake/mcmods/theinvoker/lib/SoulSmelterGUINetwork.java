package snake.mcmods.theinvoker.lib;

public enum SoulSmelterGUINetwork
{
	LAST_BOIL_TICK,
	BOIL_PROGRESS,
	LAVA_CAPACITY;
	
	public static SoulSmelterGUINetwork fromInt(int i)
	{
		if(i>=0&&i<values().length)
			return values()[i];
		return null;
	}
}
