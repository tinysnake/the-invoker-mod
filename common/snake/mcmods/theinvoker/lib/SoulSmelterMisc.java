package snake.mcmods.theinvoker.lib;

import java.util.HashMap;

import net.minecraftforge.liquids.LiquidContainerRegistry;

import snake.mcmods.theinvoker.items.TIItems;

public class SoulSmelterMisc
{
    private static final int SOUL_SHARD_BOIL_TICKS = 80;

    private static final HashMap<Integer, Integer> boilRegistry = new HashMap<Integer, Integer>();

    public static void registerRecipe(int itemID, int totalBoilTicks)
    {
        if (itemID <= 0 || totalBoilTicks <= 0)
            return;
        if (boilRegistry.containsKey(itemID))
        {
            boilRegistry.remove(itemID);
        }
        boilRegistry.put(itemID, totalBoilTicks);
    }

    public static boolean getIsValidRecipe(int itemID)
    {
        return boilRegistry.containsKey(itemID);
    }

    public static int getTotalBoilTicks(int itemID)
    {
        if (getIsValidRecipe(itemID))
        {
            return boilRegistry.get(itemID);
        }
        return -1;
    }

    public static int getLavaBurnAmount(int itemID)
    {
        if (getIsValidRecipe(itemID))
        {
            return LiquidContainerRegistry.BUCKET_VOLUME * 4 / getTotalBoilTicks(itemID);
        }
        return -1;
    }

    public static void initDefaultRecipies()
    {
        registerRecipe(TIItems.soulShard.itemID, SOUL_SHARD_BOIL_TICKS);
    }
}
