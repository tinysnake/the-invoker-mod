package snake.mcmods.theinvoker.logic;

import net.minecraft.client.multiplayer.WorldClient;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.net.packet.PacketSeductionTotemUpdate;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import cpw.mods.fml.client.FMLClientHandler;

public class SeductionTotemLogicHandler
{

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
