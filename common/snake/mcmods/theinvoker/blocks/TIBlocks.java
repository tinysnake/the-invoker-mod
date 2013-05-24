package snake.mcmods.theinvoker.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import snake.mcmods.theinvoker.constants.TIBlockID;
import snake.mcmods.theinvoker.constants.TINames;

public class TIBlocks
{
    public static BlockTotemBase totem;
    
    public static void init()
    {
        totem = new BlockTotemBase(TIBlockID.TOTEM);
        
        GameRegistry.registerBlock(totem,TINames.BLOCK_TOTEM);
    }
}
