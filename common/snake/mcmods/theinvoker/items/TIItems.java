package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.lib.constants.TIItemID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import cpw.mods.fml.common.registry.GameRegistry;

public class TIItems
{
	public static ItemTotem totem;
	public static ItemSoulShard soulShard;
	public static ItemSoulRune soulRune;
	public static ItemSeductionTotem seductionTotem;
	public static ItemBrokenSeductionTotem brokenSeductionTotem;
	public static ItemEvilTouch evilTouch;

	public static void init()
	{
		totem = new ItemTotem(TIItemID.TOTEM);
		soulShard = new ItemSoulShard(TIItemID.SOUL_SHARD);
		soulRune = new ItemSoulRune(TIItemID.SOUL_RUNE);
		seductionTotem = new ItemSeductionTotem(TIItemID.SEDUCTION_TOTEM);
		brokenSeductionTotem = new ItemBrokenSeductionTotem(TIItemID.BROKEN_SEDUCTION_TOTEM);
		evilTouch = new ItemEvilTouch(TIItemID.EVIL_TOUCH);

		GameRegistry.registerItem(totem, TIName.ITEM_TOTEM);
		GameRegistry.registerItem(soulShard, TIName.ITEM_SOUL_SHARD);
		GameRegistry.registerItem(soulRune, TIName.ITEM_SOUL_RUNE);
		GameRegistry.registerItem(seductionTotem, TIName.ITEM_SEDUCTION_TOTEM);
		GameRegistry.registerItem(brokenSeductionTotem, TIName.ITEM_BROKEN_SEDUCTION_TOTEM);
		GameRegistry.registerItem(evilTouch, TIName.ITEM_EVIL_TOUCH);
	}
}
