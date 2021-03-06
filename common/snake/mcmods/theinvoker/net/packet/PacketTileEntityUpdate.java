package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketTileEntityUpdate extends PacketTI
{
	public PacketTileEntityUpdate()
	{
		super(PacketTypeHandler.TILE_TI, true);
	}

	public PacketTileEntityUpdate(int x, int y, int z, int direction, String ownerName)
	{
		super(PacketTypeHandler.TILE_TI, true);
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;
		this.ownerName = ownerName;
	}

	public int x;
	public int y;
	public int z;
	public int direction;
	public String ownerName;

	@Override
	protected void writeData(DataOutputStream dos) throws IOException
	{
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(z);
		dos.writeInt(direction);
		if (ownerName != null)
			dos.writeUTF(ownerName);
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
	}

	@Override
	public void doItsThing(EntityPlayer player)
	{
		TheInvoker.proxy.handleTileEntityUpdate(this, player);
	}

}
