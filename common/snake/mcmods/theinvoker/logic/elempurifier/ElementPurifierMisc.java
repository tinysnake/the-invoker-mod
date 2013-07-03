package snake.mcmods.theinvoker.logic.elempurifier;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.RuneType;
import snake.mcmods.theinvoker.lib.constants.TIEnergyID;
import snake.mcmods.theinvoker.net.packet.PacketElementPurifierUpdate;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;

public class ElementPurifierMisc
{
	public static final int SOUL_ENERGY_PER_RUNE = 100;
	public static final int ELEMENT_PER_RUNE = 1;
	public static final int DEFAULT_BOIL_TICKS = 200;

	private static final HashMap<String, String> boilRegistry = new HashMap<String, String>();

	public static void registerRecipe(int itemID, int damageVal, int energyID, int totalBoilTicks)
	{
		if (itemID <= 0 || totalBoilTicks <= 0)
			return;
		String key = itemID + ":" + damageVal;
		if (boilRegistry.containsKey(key))
		{
			boilRegistry.remove(key);
		}
		boilRegistry.put(key, energyID + ":" + totalBoilTicks);
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

	public static int getEnergyID(int itemID)
	{
		return getEnergyID(itemID, 0);
	}

	public static int getEnergyID(int itemID, int damageVal)
	{
		String key = itemID + ":" + damageVal;
		if (getIsValidRecipe(itemID, damageVal))
		{
			return Integer.valueOf(boilRegistry.get(key).split(":")[0]);
		}
		return -1;
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
			return Integer.valueOf(boilRegistry.get(key).split(":")[1]);
		}
		return -1;
	}

	public static void initDefaultRecipies()
	{
		registerRecipe(TIItems.soulRune.itemID, RuneType.NEUTRAL.ordinal(), TIEnergyID.SOUL, DEFAULT_BOIL_TICKS);
		registerRecipe(TIItems.soulRune.itemID, RuneType.ICE.ordinal(), TIEnergyID.ICE, DEFAULT_BOIL_TICKS);
		registerRecipe(TIItems.soulRune.itemID, RuneType.FIRE.ordinal(), TIEnergyID.FIRE, DEFAULT_BOIL_TICKS);
		registerRecipe(TIItems.soulRune.itemID, RuneType.WIND.ordinal(), TIEnergyID.WIND, DEFAULT_BOIL_TICKS);
		registerRecipe(TIItems.soulRune.itemID, RuneType.DARKNESS.ordinal(), TIEnergyID.DARKNESS, DEFAULT_BOIL_TICKS);
	}

	public static void syncState(PacketElementPurifierUpdate p, EntityPlayer player)
	{
		TileEntity te = player.worldObj.getBlockTileEntity(p.x,p.y,p.z);
		if(te instanceof TileElementPurifier)
		{
			TileElementPurifier tep = (TileElementPurifier)te;
			tep.setDirection(p.direction);
			tep.setOwnerName(p.ownerName);
			tep.hasWork=p.hasWork;
		}
	}
}
