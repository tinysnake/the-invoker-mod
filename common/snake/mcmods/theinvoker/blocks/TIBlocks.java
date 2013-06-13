package snake.mcmods.theinvoker.blocks;

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

	public static void init()
	{
		totem = new BlockTotem(TIBlockID.TOTEM);
		seductionTotem = new BlockSeductionTotem(TIBlockID.SEDUCTION_TOTEM);
		soulSmelter = new BlockSoulSmelter(TIBlockID.SOUL_SMELTER);
		soulStone = new BlockSoulStone(TIBlockID.SOUL_STONE_DUMMY);
		soulStoneDummy = new BlockSoulStoneDummy(TIBlockID.SOUL_STONE);

		GameRegistry.registerBlock(soulSmelter, TIName.BLOCK_SOUL_SMELTER);
		GameRegistry.registerBlock(soulStoneDummy, TIName.BLOCK_SOUL_STONE);
	}
}
