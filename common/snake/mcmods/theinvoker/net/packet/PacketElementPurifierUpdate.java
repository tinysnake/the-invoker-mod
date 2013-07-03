package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.logic.elempurifier.ElementPurifierMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketElementPurifierUpdate extends PacketTileEntityUpdate
{

	public PacketElementPurifierUpdate()
	{
		super();
		this.type = PacketTypeHandler.ELEMENT_PURIFIER;
	}

	public PacketElementPurifierUpdate(int x, int y, int z, int direction, String ownerName, boolean hasWork)
	{
		super(x, y, z, direction, ownerName);
		this.type = PacketTypeHandler.ELEMENT_PURIFIER;
		this.hasWork = hasWork;
	}

	public boolean hasWork;

	@Override
	protected void writeData(DataOutputStream dos) throws IOException
	{
		super.writeData(dos);
		dos.writeBoolean(hasWork);
	}

	@Override
	protected void readData(DataInputStream dis) throws IOException
	{
		super.readData(dis);
		hasWork = dis.readBoolean();
	}

	@Override
	public void doItsThing(EntityPlayer player)
	{
		ElementPurifierMisc.syncState(this, player);
	}
}
