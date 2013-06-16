package snake.mcmods.theinvoker.lib;

public enum TotemType
{
	GHOST, SOUL, SOUL_ATTRACTIVE, RUNE_ICE, RUNE_FIRE, RUNE_WIND, RUNE_DARKNESS, MASSACRE;

	public static TotemType getType(int i)
	{
		if (i < values().length)
			return values()[i];
		return GHOST;
	}

	public boolean isSomeKindOfRuneTotem()
	{
		return this.ordinal() > SOUL_ATTRACTIVE.ordinal() && this.ordinal() < MASSACRE.ordinal();
	}
}