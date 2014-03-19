package snake.mcmods.theinvoker.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.net.AbstractPacket;

public class PacketTI extends AbstractPacket
{
	public void doItsThing(EntityPlayer player)
	{

	}

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        
    }
}
