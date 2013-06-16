package snake.mcmods.theinvoker.net.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketTI
{
	public PacketTI(PacketTypeHandler type, boolean isChunkPacket)
	{
		this.type = type;
		this.isChunkPacket = isChunkPacket;
	}

	public boolean isChunkPacket;

	public PacketTypeHandler type;

	public byte[] toByteArray()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try
		{
			dos.writeByte(type.ordinal());
			writeData(dos);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public void fromByteArray(DataInputStream dis)
	{
		try
		{
			readData(dis);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void writeData(DataOutputStream dos) throws IOException
	{

	}

	protected void readData(DataInputStream dis) throws IOException
	{

	}

	public void doItsThing(EntityPlayer player)
	{

	}
}
