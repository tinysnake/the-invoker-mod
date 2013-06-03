package snake.mcmods.theinvoker.logic.seductiontotems;

import net.minecraft.client.multiplayer.WorldClient;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.net.packet.PacketSeductionTotemUpdate;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import cpw.mods.fml.client.FMLClientHandler;

public class SeductionTotemMisc
{
    public static final int MAX_AGE_DMG_VALUE = 17281;
    public static final int AGE_DMG_VALUE_SCALE = 200;
    public static final int EFFECTIVE_RANGE = 24;
    public static final int LOSE_EFFECT_RANGE = 2;
    public static final int GHOST_BLOCK_METADATA = 0;
    public static final int NORMAL_METADATA = 1;
    public static final int BROKEN_METADATA = 2;
    public static final String[] SEDUCTION_AI_LIST = 
    {
        "Creeper", "Skeleton", "Zombie", "Zombie Pigman", 
        "Enderman", "Cave Spider", "Blaze", "Pig", "Sheep", 
        "Cow", "Chiken", "Mooshroom", "Spider"
    };

    public static boolean isGhostBlock(int metadata)
    {
        return metadata == GHOST_BLOCK_METADATA;
    }

    public static boolean getIsBroken(int age)
    {
        return age / (float) AGE_DMG_VALUE_SCALE >= MAX_AGE_DMG_VALUE;
    }

    public static int getDamageDataFromAge(int age)
    {
        return age / AGE_DMG_VALUE_SCALE;
    }

    public static int getAgeFromDamageValue(int metadata)
    {
        return metadata * AGE_DMG_VALUE_SCALE;
    }

    public static void syncDataFromPacket(PacketSeductionTotemUpdate p)
    {
        TheInvoker.proxy.handleTileEntityUpdate(p);
        WorldClient world = FMLClientHandler.instance().getClient().theWorld;
        TileSeductionTotem tt = (TileSeductionTotem) world.getBlockTileEntity(p.x, p.y, p.z);
        if (tt != null)
        {
            tt.setAge(p.age);
        }
    }
}
