package snake.mcmods.theinvoker.net;

import snake.mcmods.theinvoker.net.packet.PacketTI;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        PacketTI p = PacketTypeHandler.deserialize(packet.data);
        
        p.doItsThing();
    }

}
