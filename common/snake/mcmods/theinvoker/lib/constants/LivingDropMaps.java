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

		SOUL_SHARD_DROP_MAP.put("Creeper", new int[] { 1, 4 });
		SOUL_SHARD_DROP_MAP.put("Skeleton", new int[] { 1, 4 });
		SOUL_SHARD_DROP_MAP.put("Spider", new int[] { 1, 4 });
		SOUL_SHARD_DROP_MAP.put("Zombie", new int[] { 1, 4 });

		SOUL_SHARD_DROP_MAP.put("Ghast", new int[] { 1, 6 });
		SOUL_SHARD_DROP_MAP.put("Zombie Pigman", new int[] { 1, 6 });
		SOUL_SHARD_DROP_MAP.put("Enderman", new int[] { 1, 6 });
		SOUL_SHARD_DROP_MAP.put("Cave Spider", new int[] { 1, 6 });
		SOUL_SHARD_DROP_MAP.put("Blaze", new int[] { 1, 6 });

		SOUL_SHARD_DROP_MAP.put("Pig", new int[] { 1, 1 });
		SOUL_SHARD_DROP_MAP.put("Sheep", new int[] { 1, 1 });
		SOUL_SHARD_DROP_MAP.put("Cow", new int[] { 1, 1 });
		SOUL_SHARD_DROP_MAP.put("Chicken", new int[] { 1, 1 });
		SOUL_SHARD_DROP_MAP.put("Squid", new int[] { 1, 1 });
		SOUL_SHARD_DROP_MAP.put("Wolf", new int[] { 1, 1 });
		SOUL_SHARD_DROP_MAP.put("Mooshroom", new int[] { 1, 1 });

		SOUL_SHARD_DROP_MAP.put("Slime", new int[] { 0, 0 });
		SOUL_SHARD_DROP_MAP.put("Silverfish", new int[] { 0, 0 });
		SOUL_SHARD_DROP_MAP.put("Magma Cube", new int[] { 0, 0 });
		SOUL_SHARD_DROP_MAP.put("Bat", new int[] { 0, 0 });
		SOUL_SHARD_DROP_MAP.put("Ocelot", new int[] { 0, 0 });
		SOUL_SHARD_DROP_MAP.put("Villager", new int[] { 0, 0 });

		SOUL_SHARD_DROP_MAP.put("Witch", new int[] { 10, 21 });

		/*****************************************************/

		RUNE_DROP_MAP = new HashMap<String, int[]>();

		// special
		RUNE_DROP_MAP.put("Creeper", new int[] { 1, 2 });
		RUNE_DROP_MAP.put("Blaze", new int[] { 1, 3 });
		RUNE_DROP_MAP.put("Ghast", new int[] { 2, 3 });
		RUNE_DROP_MAP.put("Cave Spider", new int[] { 1, 2 });
		RUNE_DROP_MAP.put("Enderman", new int[] { 1, 3 });
		RUNE_DROP_MAP.put("Squid", new int[] { 0, 3 });

		// 1-4
		RUNE_DROP_MAP.put("Skeleton", new int[] { 1, 4 });
		RUNE_DROP_MAP.put("Spider", new int[] { 1, 4 });
		RUNE_DROP_MAP.put("Zombie", new int[] { 1, 4 });

		// 1-5
		RUNE_DROP_MAP.put("Zombie Pigman", new int[] { 1, 5 });

		// 1-1
		RUNE_DROP_MAP.put("Pig", new int[] { 1, 1 });
		RUNE_DROP_MAP.put("Sheep", new int[] { 1, 1 });
		RUNE_DROP_MAP.put("Cow", new int[] { 1, 1 });
		RUNE_DROP_MAP.put("Chicken", new int[] { 1, 1 });
		RUNE_DROP_MAP.put("Wolf", new int[] { 1, 1 });
		RUNE_DROP_MAP.put("Mooshroom", new int[] { 1, 1 });

		// 0-0
		RUNE_DROP_MAP.put("Ocelot", new int[] { 0, 0 });
		RUNE_DROP_MAP.put("Villager", new int[] { 0, 0 });
		RUNE_DROP_MAP.put("Slime", new int[] { 0, 0 });
		RUNE_DROP_MAP.put("Silverfish", new int[] { 0, 0 });
		RUNE_DROP_MAP.put("Magma Cube", new int[] { 0, 0 });
		RUNE_DROP_MAP.put("Bat", new int[] { 0, 0 });

		// 5-16
		RUNE_DROP_MAP.put("Witch", new int[] { 5, 16 });

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
		RUNE_TYPE_DROP_MAP.put("Chicken", null);
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
			int rand = arr[1] == 0 ? 0 : e.worldObj.rand.nextInt(arr[1]);
			return rand + arr[0];
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
			return RUNE_TYPE_DROP_MAP.get(e.getEntityName()) != null;
		return false;
	}

	public static RuneType getRuneTypeBy(EntityLiving e)
	{
		if (e == null)
			return RuneType.NEUTRAL;
		if (RUNE_TYPE_DROP_MAP.containsKey(e.getEntityName()))
		{
			RuneType rt = RUNE_TYPE_DROP_MAP.get(e.getEntityName());
			return rt != null ? rt : RuneType.values()[e.worldObj.rand.nextInt(RuneType.values().length)];
		}
		else
			return RuneType.values()[e.worldObj.rand.nextInt(RuneType.values().length)];
	}
}
