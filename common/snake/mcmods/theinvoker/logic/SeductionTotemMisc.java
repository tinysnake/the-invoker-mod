package snake.mcmods.theinvoker.logic;

public class SeductionTotemMisc
{
    public static final int MAX_AGE_DMG_VALUE = 17281;
    public static final int AGE_DMG_VALUE_SCALE = 200;
    public static final int EFFECTIVE_RANGE = 24;
    public static final int LOSE_EFFECT_RANGE = 2;
    public static final int GHOST_BLOCK_METADATA = 0;
    public static final int NORMAL_METADATA = 1;
    public static final int BROKEN_METADATA = 2;

    public static boolean isGhostBlock(int metadata)
    {
        return metadata == GHOST_BLOCK_METADATA || metadata == BROKEN_METADATA;
    }

    public static boolean getIsBroken(int age)
    {
        // return age / (float) AGE_METADATA_SCALE <= 1F * AGE_METADATA_SCALE;
        return age / (float) AGE_DMG_VALUE_SCALE >= MAX_AGE_DMG_VALUE;
    }

    public static int getDamageDataFromAge(int age)
    {
        return age / AGE_DMG_VALUE_SCALE;
    }

    public static int getAgeFromDamageValue(int metadata)
    {
        return metadata * AGE_DMG_VALUE_SCALE;
    }
}
