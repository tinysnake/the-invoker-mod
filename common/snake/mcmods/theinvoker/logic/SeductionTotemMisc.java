package snake.mcmods.theinvoker.logic;

public class SeductionTotemMisc
{
    public static final int MAX_AGE_METADATA = 17281;
    public static final int AGE_METADATA_SCALE = 200;
    public static final int EFFECTIVE_RANGE = 24;
    public static final int LOSE_EFFECT_RANGE = 2;
    public static final int GHOST_BLOCK_METADATA = 0;

    public static boolean isGhostBlock(int metadata)
    {
        return metadata == GHOST_BLOCK_METADATA;
    }

    public static boolean getIsBroken(int age)
    {
        return age / (float) AGE_METADATA_SCALE <= 1F * AGE_METADATA_SCALE;
    }

    public static int getMetadataFromAge(int age)
    {
        return age / AGE_METADATA_SCALE;
    }

    public static int getAgeFromMetadata(int metadata)
    {
        return metadata * AGE_METADATA_SCALE;
    }
}
