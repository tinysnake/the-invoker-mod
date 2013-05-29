package snake.mcmods.theinvoker.lib.constants;

import java.util.HashMap;

import net.minecraft.entity.EntityLiving;

import snake.mcmods.theinvoker.lib.RuneType;

public class LivingDropMaps
{
    public static final HashMap<String, int[]> SOUL_SHARD_DROP_MAP;
    public static final HashMap<String, int[]> RUNE_DROP_MAP;
    public static final HashMap<String, RuneType> RUNE_TYPE_DROP_MAP;

    static
    {
        SOUL_SHARD_DROP_MAP = new HashMap<String, int[]>();

        int[] arg =
        { 1, 4 };
        SOUL_SHARD_DROP_MAP.put("Creeper", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Skeleton", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Spider", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Zombie", arg.clone());

        arg[0] = 1;
        arg[1] = 6;
        SOUL_SHARD_DROP_MAP.put("Ghast", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Zombie Pigman", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Enderman", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Cave Spider", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Blaze", arg.clone());

        arg[0] = 1;
        arg[1] = 1;
        SOUL_SHARD_DROP_MAP.put("Pig", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Sheep", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Cow", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Chiken", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Squid", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Wolf", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Mooshroom", arg.clone());

        arg[0] = 0;
        arg[1] = 0;
        SOUL_SHARD_DROP_MAP.put("Slime", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Silverfish", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Magma Cube", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Bat", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Ocelot", arg.clone());
        SOUL_SHARD_DROP_MAP.put("Villager", arg.clone());

        arg[0] = 10;
        arg[1] = 21;
        SOUL_SHARD_DROP_MAP.put("Witch", arg.clone());

        /*****************************************************/
        
        RUNE_DROP_MAP = new HashMap<String, int[]>();

        arg[0] = 2;
        arg[1] = 2;
        // special
        RUNE_DROP_MAP.put("Creeper", arg.clone());
        arg[0] = 3;
        arg[1] = 2;
        RUNE_DROP_MAP.put("Blaze", arg.clone());
        arg[0] = 2;
        arg[1] = 3;
        RUNE_DROP_MAP.put("Ghast", arg.clone());
        arg[0] = 1;
        arg[1] = 2;
        RUNE_DROP_MAP.put("Cave Spider", arg.clone());
        arg[0] = 1;
        arg[1] = 4;
        RUNE_DROP_MAP.put("Enderman", arg.clone());
        arg[0] = 0;
        arg[1] = 3;
        RUNE_DROP_MAP.put("Squid", arg.clone());

        // 1-4
        arg[0] = 1;
        arg[1] = 4;
        RUNE_DROP_MAP.put("Skeleton", arg.clone());
        RUNE_DROP_MAP.put("Spider", arg.clone());
        RUNE_DROP_MAP.put("Zombie", arg.clone());

        // 1-5
        arg[0] = 1;
        arg[1] = 5;
        RUNE_DROP_MAP.put("Zombie Pigman", arg.clone());

        // 1-1
        arg[0] = 1;
        arg[1] = 1;
        RUNE_DROP_MAP.put("Pig", arg.clone());
        RUNE_DROP_MAP.put("Sheep", arg.clone());
        RUNE_DROP_MAP.put("Cow", arg.clone());
        RUNE_DROP_MAP.put("Chiken", arg.clone());
        RUNE_DROP_MAP.put("Wolf", arg.clone());
        RUNE_DROP_MAP.put("Mooshroom", arg.clone());

        // 0-0
        arg[0] = 0;
        arg[1] = 0;
        RUNE_DROP_MAP.put("Ocelot", arg.clone());
        RUNE_DROP_MAP.put("Villager", arg.clone());
        RUNE_DROP_MAP.put("Slime", arg.clone());
        RUNE_DROP_MAP.put("Silverfish", arg.clone());
        RUNE_DROP_MAP.put("Magma Cube", arg.clone());
        RUNE_DROP_MAP.put("Bat", arg.clone());

        // 5-16
        arg[0] = 5;
        arg[1] = 16;
        RUNE_DROP_MAP.put("Witch", arg.clone());
        
        
        /*****************************************************/

        RUNE_TYPE_DROP_MAP = new HashMap<String, RuneType>();
        
        RUNE_TYPE_DROP_MAP.put("Creeper", RuneType.FIRE);
        RUNE_TYPE_DROP_MAP.put("Skeleton", null);
        RUNE_TYPE_DROP_MAP.put("Spider", null);
        RUNE_TYPE_DROP_MAP.put("Zombie", null);
        RUNE_TYPE_DROP_MAP.put("Slime", null);
        RUNE_TYPE_DROP_MAP.put("Ghast", RuneType.DARKNESS);
        RUNE_TYPE_DROP_MAP.put("Zombie Pigman", null);
        RUNE_TYPE_DROP_MAP.put("Enderman", RuneType.DARKNESS);
        RUNE_TYPE_DROP_MAP.put("CaveSpider", null);
        RUNE_TYPE_DROP_MAP.put("Silverfish", null);
        RUNE_TYPE_DROP_MAP.put("Cave Spider", RuneType.WIND);
        RUNE_TYPE_DROP_MAP.put("Blaze", RuneType.FIRE);
        RUNE_TYPE_DROP_MAP.put("Magma Cube", null);
        RUNE_TYPE_DROP_MAP.put("Bat", null);
        RUNE_TYPE_DROP_MAP.put("Witch", null);
        RUNE_TYPE_DROP_MAP.put("Pig", null);
        RUNE_TYPE_DROP_MAP.put("Sheep", null);
        RUNE_TYPE_DROP_MAP.put("Cow", null);
        RUNE_TYPE_DROP_MAP.put("Chiken", null);
        RUNE_TYPE_DROP_MAP.put("Squid", RuneType.ICE);
        RUNE_TYPE_DROP_MAP.put("Wolf", null);
        RUNE_TYPE_DROP_MAP.put("Mooshroom", null);
        RUNE_TYPE_DROP_MAP.put("Ocelot", null);
        RUNE_TYPE_DROP_MAP.put("Villager", null);
    }

    public static int getSoulDropQuantityByName(EntityLiving e)
    {
        if (e != null && SOUL_SHARD_DROP_MAP.containsKey(e.getEntityName()))
        {
            int[] arr = SOUL_SHARD_DROP_MAP.get(e.getEntityName());
            return e.worldObj.rand.nextInt(arr[1]) + arr[0];
        }
        return 0;
    }

    public static int getRuneDropQuantityByName(EntityLiving e)
    {
        if (e != null && RUNE_DROP_MAP.containsKey(e.getEntityName()))
        {
            int[] arr = RUNE_DROP_MAP.get(e.getEntityName());
            return e.worldObj.rand.nextInt(arr[1]) + arr[0];
        }
        return 0;
    }

    public static boolean isRuneTypeLockedFor(EntityLiving e)
    {
        if (e != null && RUNE_TYPE_DROP_MAP.containsKey(e.getEntityName()))
        {
            return RUNE_TYPE_DROP_MAP.get(e.getEntityName()) != null;
        }
        return false;
    }

    public static RuneType getRuneTypeBy(EntityLiving e)
    {
        if(e==null)
            return RuneType.NEUTRAL;
        if(RUNE_TYPE_DROP_MAP.containsKey(e.getEntityName()))
        {
            return RUNE_TYPE_DROP_MAP.get(e.getEntityName());
        }
        else
        {
            return RuneType.values()[e.worldObj.rand.nextInt(RuneType.values().length)];
        }
    }
}
