package snake.mcmods.theinvoker.logic.soulsmelter;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.net.packet.PacketSoulSmelterUpdate;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;

public class SoulSmelterMisc
{
	private static final int SOUL_SHARD_BOIL_TICKS = 50;

	private static final int SOUL_SHARD_LAVA_COST = 20;

	private static final HashMap<String, Integer> boilRegistry = new HashMap<String, Integer>();

	public static final int ENERGY_PER_ITEM = 20;

	public static void registerRecipe(int itemID, int damageVal, int totalBoilTicks)
	{
		if (itemID <= 0 || totalBoilTicks <= 0)
			return;
		String key = itemID + ":" + damageVal;
		if (boilRegistry.containsKey(key))
		{
			boilRegistry.remove(key);
		}
		boilRegistry.put(key, totalBoilTicks);
	}

	public static boolean getIsValidRecipe(int itemID)
	{
		return getIsValidRecipe(itemID, 0);
	}

	public static boolean getIsValidRecipe(int itemID, int damageVal)
	{
		String key = itemID + ":" + damageVal;
		return boilRegistry.containsKey(key);
	}

	public static int getTotalBoilTicks(int itemID)
	{
		return getTotalBoilTicks(itemID, 0);
	}

	public static int getTotalBoilTicks(int itemID, int damageVal)
	{
		String key = itemID + ":" + damageVal;
		if (getIsValidRecipe(itemID, damageVal))
		{
			return boilRegistry.get(key);
		}
		return -1;
	}

	public static int getLavaBurnAmount(int itemID)
	{
		return getLavaBurnAmount(itemID, 0);
	}

	public static int getLavaBurnAmount(int itemID, int damageVal)
	{
		if (getIsValidRecipe(itemID, damageVal))
		{
			return SOUL_SHARD_LAVA_COST * getTotalBoilTicks(itemID, damageVal) / SOUL_SHARD_BOIL_TICKS;
		}
		return -1;
	}

	public static void initDefaultRecipies()
	{
		registerRecipe(TIItems.soulShard.itemID, 0, SOUL_SHARD_BOIL_TICKS);
	}

	public static void syncSoulSmelterState(PacketSoulSmelterUpdate p, EntityPlayer player)
	{
		TileEntity te = player.worldObj.getBlockTileEntity(p.x, p.y, p.z);
		if (te != null && te instanceof TileSoulSmelter)
		{
			TileSoulSmelter tss = (TileSoulSmelter)te;
			tss.setDirection(p.direction);
			LiquidTank lt = tss.getLavaTank();
			if (p.lavaAmount > 0)
			{
				LiquidStack ls = lt.getLiquid();
				if (ls == null)
					lt.setLiquid(new LiquidStack(Block.lavaStill, p.lavaAmount));
				else
					lt.getLiquid().amount = p.lavaAmount;
			}
			else
				lt.setLiquid(null);
			tss.hasWork = p.hasWork;

			player.worldObj.updateAllLightTypes(p.x, p.y, p.z);
			player.worldObj.markBlockForUpdate(p.x, p.y, p.z);
		}
	}
}
