package snake.mcmods.theinvoker.lib;

public enum TotemType
{
    TYPE_GHOST,
    TYPE_SOUL,
    TYPE_SOUL_ATTRACTIVE,
    TYPE_RUNE_ICE,
    TYPE_RUNE_FIRE,
    TYPE_RUNE_WIND,
    TYPE_RUNE_DARKNESS,
    TYPE_MASSACRE;

    public static TotemType getType(int i)
    {
        if (i < values().length)
            return values()[i];
        return TYPE_GHOST;
    }

    public boolean isSomeKindOfRuneTotem()
    {
        return this.ordinal() > TYPE_SOUL_ATTRACTIVE.ordinal() && this.ordinal() < TYPE_MASSACRE.ordinal();
    }
}