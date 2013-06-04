package snake.mcmods.theinvoker.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import snake.mcmods.theinvoker.lib.constants.TIBlockID;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class TIBlocks
{
    public static BlockTotem totem;
    public static BlockSeductionTotem seductionTotem;
    public static BlockSoulSmelter soulSmelter;
    
    public static void init()
    {
        totem = new BlockTotem(TIBlockID.TOTEM);
        seductionTotem = new BlockSeductionTotem(TIBlockID.SEDUCTION_TOTEM);
        soulSmelter = new BlockSoulSmelter(TIBlockID.SOUL_SMELTER);
        
        GameRegistry.registerBlock(soulSmelter, TIName.BLOCK_SOUL_SMELTER);
    }
}
