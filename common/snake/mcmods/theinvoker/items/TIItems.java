package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.lib.constants.TIItemID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import cpw.mods.fml.common.registry.GameRegistry;

public class TIItems
{
    public static ItemTotem totem;
    public static ItemSoulShard soulShard;
    public static ItemSoulRune soulRune;

    public static void init()
    {
        totem = new ItemTotem(TIItemID.TOTEM);
        soulShard= new ItemSoulShard(TIItemID.SOUL_SHARD);
        soulRune = new ItemSoulRune(TIItemID.SOUL_RUNE);

        GameRegistry.registerItem(totem,TIName.ITEM_TOTEM);
        GameRegistry.registerItem(soulShard, TIName.ITEM_SOUL_SHARD);
        GameRegistry.registerItem(soulRune, TIName.ITEM_SOUL_RUNE);
    }
}
