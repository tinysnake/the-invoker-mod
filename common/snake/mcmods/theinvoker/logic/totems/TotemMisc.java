package snake.mcmods.theinvoker.logic.totems;

import net.minecraft.entity.EntityLiving;
import scala.Console;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.tileentities.TileTotem;

public class TotemMisc
{
	public static final int[] TOTEM_EFFECTIVE_RANGES = {
			// ghost
	0,
			// soul_attractive
	8,
			// soul
	16,
			// runes
	8, 8, 8, 8,
			// massacre
	4 };

	public static final int DROP_CHANCE_OF_FIRE_ESSENCE = 5;
	public static final int DROP_CHANGE_OF_DARK_ESSENCE = 10;

	public static int getEffectiveRangeByMetadata(TotemType metadata)
	{
		return TOTEM_EFFECTIVE_RANGES[metadata.ordinal()];
	}

	public static void dropItems(EntityLiving e, TileTotem tt, boolean doDropRune)
	{
		TotemType totemType = tt.getType();

		RuneMisc.dropSoulShardsBy(e, totemType);
		if (doDropRune)
		{
			int runeDrops = RuneMisc.dropRunesBy(e, totemType);
			if (runeDrops == 0)
			{
				RuneMisc.dropBonusRunes(e, totemType);
			}
			if (totemType == TotemType.MASSACRE)
			{
				tryDropEssence(e);
			}
		}
	}

	private static boolean tryDropEssence(EntityLiving e)
	{
		int chance = 0;
		if (e.getEntityName() == "Blaze")
			chance = DROP_CHANCE_OF_FIRE_ESSENCE;
		else if (e.getEntityName() == "Enderman")
			chance = DROP_CHANGE_OF_DARK_ESSENCE;
		if (e.worldObj.rand.nextInt(TIGlobal.RARE_CHANCE_MULTIPLIER) <= chance)
		{
			Console.println("congratulations!");
			return true;
		}
		return false;
	}
}
