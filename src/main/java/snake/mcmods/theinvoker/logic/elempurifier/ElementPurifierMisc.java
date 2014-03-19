package snake.mcmods.theinvoker.logic.elempurifier;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

    public static void registerRecipe(Item item, int damageVal, int energyID, int totalBoilTicks)
    {
        if (item == null || totalBoilTicks <= 0)
            return;
        String key = item.getUnlocalizedName() + ":" + damageVal;
        if (boilRegistry.containsKey(key))
        {
            boilRegistry.remove(key);
        }
        boilRegistry.put(key, energyID + ":" + totalBoilTicks);
    }

    public static boolean getIsValidRecipe(Item item)
    {
        return getIsValidRecipe(item, 0);
    }

    public static boolean getIsValidRecipe(Item item, int damageVal)
    {
        String key = item + ":" + damageVal;
        return boilRegistry.containsKey(key);
    }

    public static int getEnergyID(Item item)
    {
        return getEnergyID(item, 0);
    }

    public static int getEnergyID(Item item, int damageVal)
    {
        String key = item + ":" + damageVal;
        if (getIsValidRecipe(item, damageVal))
        {
            return Integer.valueOf(boilRegistry.get(key).split(":")[0]);
        }
        return -1;
    }

    public static int getTotalBoilTicks(Item item)
    {
        return getTotalBoilTicks(item, 0);
    }

    public static int getTotalBoilTicks(Item item, int damageVal)
    {
        String key = item + ":" + damageVal;
        if (getIsValidRecipe(item, damageVal))
        {
            return Integer.valueOf(boilRegistry.get(key).split(":")[1]);
        }
        return -1;
    }

    public static void initDefaultRecipies()
    {
        registerRecipe(TIItems.soulRune, RuneType.NEUTRAL.ordinal(), TIEnergyID.SOUL, DEFAULT_BOIL_TICKS);
        registerRecipe(TIItems.soulRune, RuneType.ICE.ordinal(), TIEnergyID.ICE, DEFAULT_BOIL_TICKS);
        registerRecipe(TIItems.soulRune, RuneType.FIRE.ordinal(), TIEnergyID.FIRE, DEFAULT_BOIL_TICKS);
        registerRecipe(TIItems.soulRune, RuneType.WIND.ordinal(), TIEnergyID.WIND, DEFAULT_BOIL_TICKS);
        registerRecipe(TIItems.soulRune, RuneType.DARKNESS.ordinal(), TIEnergyID.DARKNESS, DEFAULT_BOIL_TICKS);
    }

    public static void syncState(PacketElementPurifierUpdate p, EntityPlayer player)
    {
        TileEntity te = player.worldObj.getTileEntity(p.x, p.y, p.z);
        if (te instanceof TileElementPurifier)
        {
            TileElementPurifier tep = (TileElementPurifier) te;
            tep.setDirection(p.direction);
            tep.setOwnerName(p.ownerName);
            tep.hasWork = p.hasWork;
        }
    }
}
