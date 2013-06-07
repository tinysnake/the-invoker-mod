package snake.mcmods.theinvoker.lib.constants;

public enum SoulSmelterGUINetwork
{
	BOIL_PROGRESS,
	LAVA_CAPACITY;
	
	public static SoulSmelterGUINetwork fromInt(int i)
	{
		if(i>=0&&i<values().length)
			return values()[i];
		return null;
	}
}
