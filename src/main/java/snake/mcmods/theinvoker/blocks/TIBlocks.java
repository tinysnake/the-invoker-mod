package snake.mcmods.theinvoker.blocks;

import snake.mcmods.theinvoker.items.ItemElemPillar;
import snake.mcmods.theinvoker.lib.constants.TIBlockID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import cpw.mods.fml.common.registry.GameRegistry;

public class TIBlocks
{
    public static BlockTotem totem;
    public static BlockSeductionTotem seductionTotem;
    public static BlockSoulSmelter soulSmelter;
    public static BlockSoulStone soulStone;
    public static BlockSoulStoneDummy soulStoneDummy;
    public static BlockElementPurifier elementPurifier;
    public static BlockElemPillar elemPillar;
    public static BlockElemPillarDummy elemPillarDummy;

    public static void init()
    {
        totem = new BlockTotem();
        seductionTotem = new BlockSeductionTotem();
        soulSmelter = new BlockSoulSmelter();
        soulStone = new BlockSoulStone();
        soulStoneDummy = new BlockSoulStoneDummy(soulStone);
        elementPurifier = new BlockElementPurifier();
        elemPillar = new BlockElemPillar();
        elemPillarDummy = new BlockElemPillarDummy(elemPillar);

        GameRegistry.registerBlock(soulSmelter, TIName.BLOCK_SOUL_SMELTER);
        GameRegistry.registerBlock(soulStoneDummy, TIName.BLOCK_SOUL_STONE);
        GameRegistry.registerBlock(elementPurifier, TIName.BLOCK_ELEMENT_PURIFIER);
        GameRegistry.registerBlock(elemPillarDummy, ItemElemPillar.class, TIName.BLOCK_ELEMENT_PILLAR);
    }
}
