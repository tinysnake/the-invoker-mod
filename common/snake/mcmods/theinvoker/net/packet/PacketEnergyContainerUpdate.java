package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import snake.mcmods.theinvoker.energy.EnergyLogicCenter;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketEnergyContainerUpdate extends PacketTI
{
	public PacketEnergyContainerUpdate()
	{
		super(PacketTypeHandler.ENERGY_CONTAINER, false);
	}

	public PacketEnergyContainerUpdate(int x, int y, int z, int energyLevel)
	{
		this();
		this.x = x;
		this.y = y;
		this.z = z;
		this.energyLevel = energyLevel;
	}

	public int x;
	public int y;
	public int z;
	public int energyLevel;
	
	@Override
	protected void readData(DataInputStream dis) throws IOException
	{
	    x=dis.readInt();
	    y=dis.readInt();
	    z=dis.readInt();
	    energyLevel=dis.readInt();
	}
	
	@Override
	protected void writeData(DataOutputStream dos) throws IOException
	{
	    dos.writeInt(x);
	    dos.writeInt(y);
	    dos.writeInt(z);
	    dos.writeInt(energyLevel);
	}
	@Override
	public void doItsThing()
	{
	    EnergyLogicCenter.syncDataFromServer(this);
	}
}
