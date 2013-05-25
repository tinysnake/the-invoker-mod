package snake.mcmods.theinvoker.items;

import cpw.mods.fml.common.registry.GameRegistry;
import snake.mcmods.theinvoker.constants.TIItemID;
import snake.mcmods.theinvoker.constants.TIName;

public class TIItems
{
    public static ItemTotem totemSoulAttractive;

    public static void init()
    {
        totemSoulAttractive = new ItemTotem(TIItemID.ITEM_TOTEM, ItemTotem.TYPE_SOUL_ATTRACTIVE);
        
        GameRegistry.registerItem(totemSoulAttractive, TIName.ITEM_TOTEM_SOUL_ATTRACTIVE);
    }
}
