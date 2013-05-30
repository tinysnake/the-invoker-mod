package snake.mcmods.theinvoker.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.RuneType;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.LivingDropMaps;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.tileentities.TileTotem;

public class RuneMisc
{
    public static final float[] SOUL_DROP_TOTEM_TYPE_MODIFIERS =
    {
            // ghost block
            0F,
            // soul
            1.5F,
            // soulAttractive
            1F,
            // all of runes
            0F,
            0F,
            0F,
            0F,
            // massacre
            0F
    };

    public static final float[] RUNE_DROP_TOTEM_TYPE_MODIFIERS =
    {
            // ghost block
            0F,
            // soul
            0F,
            // soulAttractive
            1F,
            // all of runes
            1.5F,
            1.5F,
            1.5F,
            1.5F,
            // massacre
            0F
    };

    public static final int[] RUNE_OF_TOTEM_DROP_CHANCE =
    {
            0,
            0,
            20,
            30,
            30,
            30,
            0
    };

    public static final int EXTRA_CHANCE_OF_TOTEM_RUNE = 15;
    public static final int QUANTITY_OF_EXTRA_DROP = 1;

    public static final HashMap<Integer, RuneType> CHANCE_OF_DROP_TYPE;

    static
    {
        CHANCE_OF_DROP_TYPE = new HashMap<Integer, RuneType>();
        CHANCE_OF_DROP_TYPE.put(100, RuneType.NEUTRAL);
        CHANCE_OF_DROP_TYPE.put(20, RuneType.ICE);
        CHANCE_OF_DROP_TYPE.put(20, RuneType.FIRE);
        CHANCE_OF_DROP_TYPE.put(20, RuneType.WIND);
        CHANCE_OF_DROP_TYPE.put(10, RuneType.DARKNESS);
    }

    public static RuneType getPossibleTypeOfRuneDrop(Random rand, TileTotem tt)
    {
        int chance = rand.nextInt(100);
        ArrayList<Entry<Integer, RuneType>> list = new ArrayList<Entry<Integer, RuneType>>();
        for (Entry<Integer, RuneType> entry : CHANCE_OF_DROP_TYPE.entrySet())
        {
            if (entry.getKey() >= chance)
            {
                list.add(entry);
            }
        }
        RuneType type;
        if (list.size() == 1)
        {
            type = list.get(0).getValue();
        }
        else
        {
            type = list.get(rand.nextInt(list.size())).getValue();
        }
        return type;
    }

    public static RuneType getRuneTypeFrom(TotemType type)
    {
        RuneType rt;
        switch (type)
        {
            case RUNE_DARKNESS:
                rt = RuneType.DARKNESS;
                break;
            case RUNE_FIRE:
                rt = RuneType.FIRE;
                break;
            case RUNE_ICE:
                rt = RuneType.ICE;
                break;
            case RUNE_WIND:
                rt = RuneType.WIND;
                break;
            default:
                rt = RuneType.NEUTRAL;
                break;
        }
        return rt;
    }

    public static boolean getExtraDropChanceOfTotemRune(Random rand)
    {
        return rand.nextInt(100) <= EXTRA_CHANCE_OF_TOTEM_RUNE;
    }

    public static int dropSoulShardsBy(EntityLiving e, TotemType type)
    {
        int drops = LivingDropMaps.getSoulDropQuantityByName(e);
        drops = Math.round(drops * SOUL_DROP_TOTEM_TYPE_MODIFIERS[type.ordinal()]);
        if (drops > 0)
            e.entityDropItem(new ItemStack(TIItems.soulShard.itemID, drops, 0), 0);
        return drops;
    }

    public static int dropRunesBy(EntityLiving e, TotemType type)
    {
        int dropChance = RUNE_OF_TOTEM_DROP_CHANCE[type.ordinal()];
        if (dropChance <= e.worldObj.rand.nextInt(TIGlobal.NORMAL_CHANCE_MULTIPLIER))
            return 0;
        int drops = 0;
        RuneType runeType = LivingDropMaps.getRuneTypeBy(e);
        RuneType totemRuneType = RuneMisc.getRuneTypeFrom(type);
        if (type.isSomeKindOfRuneTotem())
        {
            if (LivingDropMaps.isRuneTypeLockedFor(e) && totemRuneType == runeType)
            {
                drops = LivingDropMaps.getRuneDropQuantityByName(e);
            }
        }
        else
        {
            drops = LivingDropMaps.getRuneDropQuantityByName(e);
        }
        drops = Math.round(drops * RUNE_DROP_TOTEM_TYPE_MODIFIERS[type.ordinal()]);
        if (drops > 0)
            e.entityDropItem(new ItemStack(TIItems.soulRune.itemID, drops, runeType.ordinal()), 0);
        return drops;
    }

    public static int dropBonusRunes(EntityLiving e, TotemType type)
    {
        if (type.isSomeKindOfRuneTotem() && getExtraDropChanceOfTotemRune(e.worldObj.rand))
        {
            RuneType rt = getRuneTypeFrom(type);
            e.entityDropItem(new ItemStack(TIItems.soulRune.itemID, QUANTITY_OF_EXTRA_DROP, rt.ordinal()), 0);
            return QUANTITY_OF_EXTRA_DROP;
        }
        return 0;
    }
}
