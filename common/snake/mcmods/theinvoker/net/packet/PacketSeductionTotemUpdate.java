package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketSeductionTotemUpdate extends PacketTileEntityUpdate
{

	public PacketSeductionTotemUpdate()
	{
		super();
	}

	public PacketSeductionTotemUpdate(int x, int y, int z, int direction, String ownerName, int age)
	{
		super(x, y, z, direction, ownerName);
		this.type = PacketTypeHandler.SEDUCTION_TOTEM;
		this.age = age;
	}

	public int age;

	@Override
	protected void writeData(DataOutputStream dos) throws IOException
	{
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(z);
		dos.writeInt(direction);
		if (ownerName != null)
			dos.writeUTF(ownerName);
		dos.writeInt(age);
	}

	@Override
	protected void readData(DataInputStream dis) throws IOException
	{
		x = dis.readInt();
		y = dis.readInt();
		z = dis.readInt();
		direction = dis.readInt();
		if (dis.available() > 0)
			ownerName = dis.readUTF();
		age = dis.readInt();
	}

	@Override
	public void doItsThing()
	{
		SeductionTotemMisc.syncDataFromPacket(this);
	}
}
