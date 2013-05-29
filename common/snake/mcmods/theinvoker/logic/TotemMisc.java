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

    public static void dropItems(EntityLiving e, TileTotem tt)
    {
        Random rand = e.worldObj.rand;
        int shardDrops = 0;
        int runeDrops = 0;
        RuneType runeType = RuneMisc.getPossibleTypeOfRuneDrop(rand, tt);

        switch (tt.getType())
        {
            case TYPE_GHOST:
                return;
            case TYPE_SOUL:
                shardDrops = rand.nextInt(3) + 2;
                runeDrops = 0;
                break;
            case TYPE_SOUL_ATTRACTIVE:
                shardDrops = rand.nextInt(2);
                runeDrops = rand.nextInt(1) + 1;
                break;
            case TYPE_MASSACRE:
                shardDrops = 0;
                runeDrops = 0;
                break;
            case TYPE_RUNE_DARKNESS:
            case TYPE_RUNE_FIRE:
            case TYPE_RUNE_ICE:
            case TYPE_RUNE_WIND:
                shardDrops = 0;
                RuneType ttRuneType = RuneMisc.getRuneTypeFrom(tt.getType());
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
                    runeDrops = rand.nextInt(3) + 1;
                }
                break;
            default:
                shardDrops = 0;
                runeDrops = 0;
                break;

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

    public static int BreakRuneToNumbersOfShards(Random rand, RuneType type)
    {
        if (type == RuneType.NEUTRAL)
        {
            return rand.nextInt(3) + 2;
        }
        return rand.nextInt(2) + 1;
    }
}
