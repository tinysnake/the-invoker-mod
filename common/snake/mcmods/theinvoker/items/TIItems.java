package snake.mcmods.theinvoker.items;

import cpw.mods.fml.common.registry.GameRegistry;
import snake.mcmods.theinvoker.constants.TIItemID;
import snake.mcmods.theinvoker.constants.TIName;

public class TIItems
{
    public static ItemTotem totem;

    public static void init()
    {
        totem = new ItemTotem(TIItemID.ITEM_TOTEM);

        GameRegistry.registerItem(totem,TIName.ITEM_TOTEM);
    }
}
