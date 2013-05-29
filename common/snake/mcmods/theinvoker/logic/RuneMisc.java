package snake.mcmods.theinvoker.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import snake.mcmods.theinvoker.lib.RuneType;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.tileentities.TileTotem;

public class RuneMisc
{
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
    public static final int EXTRA_CHANCE_OF_TOTEM_RUNE = 15;

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
            case TYPE_RUNE_DARKNESS:
                rt = RuneType.DARKNESS;
                break;
            case TYPE_RUNE_FIRE:
                rt = RuneType.FIRE;
                break;
            case TYPE_RUNE_ICE:
                rt = RuneType.ICE;
                break;
            case TYPE_RUNE_WIND:
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
}
