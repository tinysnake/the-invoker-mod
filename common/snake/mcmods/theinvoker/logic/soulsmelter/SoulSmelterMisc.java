package snake.mcmods.theinvoker.logic.soulsmelter;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.liquids.LiquidEvent;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;

public class SoulSmelterMisc
{
    private static final int SOUL_SHARD_BOIL_TICKS = 50;

    private static final int SOUL_SHARD_LAVA_COST = 20;

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
            return SOUL_SHARD_LAVA_COST * getTotalBoilTicks(itemID) / SOUL_SHARD_BOIL_TICKS ;
        }
        return -1;
    }

    public static void initDefaultRecipies()
    {
        registerRecipe(TIItems.soulShard.itemID, SOUL_SHARD_BOIL_TICKS);
    }
    
    @ForgeSubscribe
    public void handleSoulSmelterLiquidChanged(LiquidEvent e)
    {
       TileEntity te = e.world.getBlockTileEntity(e.x, e.y, e.z);
       if(te!=null && te instanceof TileSoulSmelter)
       {
           e.world.updateAllLightTypes(e.x, e.y, e.z);
       }
    }
}
