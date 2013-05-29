package snake.mcmods.theinvoker.blocks;

import snake.mcmods.theinvoker.lib.constants.TIBlockID;

public class TIBlocks
{
    public static BlockTotem totem;
    
    public static void init()
    {
        totem = new BlockTotem(TIBlockID.TOTEM);
        
    }
}
