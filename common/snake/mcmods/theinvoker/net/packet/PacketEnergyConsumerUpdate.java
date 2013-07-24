package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.energy.EnergyUtils;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketEnergyConsumerUpdate extends PacketTI
{
	public PacketEnergyConsumerUpdate()
	{
		super(PacketTypeHandler.ENERGY_CONSUMER, false);
	}

	public PacketEnergyConsumerUpdate(int x, int y, int z, boolean avaialbe, int energyLevel, int maxRequest)
	{
		this();

		this.x = x;
		this.y = y;
		this.z = z;
		this.available=avaialbe;
		this.energyLevel = energyLevel;
		this.maxRequest = maxRequest;
	}

	public int x;
	public int y;
	public int z;
	public boolean available;
	public int energyLevel;
	public int maxRequest;

	@Override
	protected void readData(DataInputStream dis) throws IOException
	{
		x = dis.readInt();
		y = dis.readInt();
		z = dis.readInt();
		available = dis.readBoolean();
		energyLevel = dis.readInt();
		maxRequest = dis.readInt();
	}

	@Override
	protected void writeData(DataOutputStream dos) throws IOException
	{
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(z);
		dos.writeBoolean(available);
		dos.writeInt(energyLevel);
		dos.writeInt(maxRequest);
	}

	@Override
	public void doItsThing(EntityPlayer player)
	{
		EnergyUtils.syncDataFromePacket(this, player);
	}
}
