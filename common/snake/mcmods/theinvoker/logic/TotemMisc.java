package snake.mcmods.theinvoker.logic;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.RuneType;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.tileentities.TileTotem;

public class TotemMisc
{
    public static final int[] TOTEM_EFFECTIVE_RANGES =
    {
            // ghost
            0,
            // soul_attractive
            8,
            // soul
            16,
            // runes
            8,
            8,
            8,
            8,
            // massacre
            4
    };

    public static int getEffectiveRangeByMetadata(TotemType metadata)
    {
        return TOTEM_EFFECTIVE_RANGES[metadata.ordinal()];
    }

    public static void dropItems(EntityLiving e, TileTotem tt, boolean doDropRune)
    {
        Random rand = e.worldObj.rand;
        int shardDrops = 0;
        int runeDrops = 0;
        TotemType totemType = tt.getType();
        RuneType runeType = RuneMisc.getPossibleTypeOfRuneDrop(rand, tt);

        shardDrops = RuneMisc.getSoulShardDropBy(rand, totemType);
        if(doDropRune)
        {
            if(tt.getType().isSomeKindOfRuneTotem())
            {
                RuneType ttRuneType = RuneMisc.getRuneTypeFrom(totemType);
                if (runeType != ttRuneType)
                {
                    if (RuneMisc.getExtraDropChanceOfTotemRune(rand))
                    {
                        runeDrops = 1;
                        runeType = ttRuneType;
                    }
                }
                else
                {
                    runeDrops = RuneMisc.getRuneDropBy(rand, totemType);;
                }
            }
            else
            {
                runeDrops = RuneMisc.getRuneDropBy(rand, totemType);
            }
        }

        if (shardDrops > 0)
        {
            e.entityDropItem(new ItemStack(TIItems.soulShard, shardDrops, 0), 0);
        }

        if (runeDrops > 0)
        {
            e.entityDropItem(new ItemStack(TIItems.soulRune, runeDrops, runeType.ordinal()), 0);
        }
    }
}
