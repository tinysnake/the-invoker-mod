package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.energy.EnergyUtils;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketEnergyContainerUpdate extends PacketTI
{
	public PacketEnergyContainerUpdate()
	{
		super(PacketTypeHandler.ENERGY_CONTAINER, false);
	}

	public PacketEnergyContainerUpdate(int x, int y, int z, boolean availabe, int energyLevel, int capacity, int maxRequest, int range)
	{
		this();
		this.x = x;
		this.y = y;
		this.z = z;
		this.available = availabe;
		this.energyLevel = energyLevel;
		this.capacity = capacity;
		this.maxRequest = maxRequest;
		this.range = range;
	}

	public int x;
	public int y;
	public int z;
	public boolean available;
	public int energyLevel;
	public int capacity;
	public int maxRequest;
	public int range;

	@Override
	protected void readData(DataInputStream dis) throws IOException
	{
		x = dis.readInt();
		y = dis.readInt();
		z = dis.readInt();
		available = dis.readBoolean();
		energyLevel = dis.readInt();
		capacity = dis.readInt();
		maxRequest = dis.readInt();
		range = dis.readInt();
	}

	@Override
	protected void writeData(DataOutputStream dos) throws IOException
	{
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(z);
		dos.writeBoolean(available);
		dos.writeInt(energyLevel);
		dos.writeInt(capacity);
		dos.writeInt(maxRequest);
		dos.writeInt(range);
	}

	@Override
	public void doItsThing(EntityPlayer player)
	{
		EnergyUtils.syncDataFromPacket(this, player);
	}
}
