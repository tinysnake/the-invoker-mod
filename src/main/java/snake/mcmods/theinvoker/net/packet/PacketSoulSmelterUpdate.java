package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.logic.soulsmelter.SoulSmelterMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketSoulSmelterUpdate extends PacketTileEntityUpdate
{
	public PacketSoulSmelterUpdate()
	{
		super();
		this.type = PacketTypeHandler.SOUL_SMELTER_UPDATE;
	}

	public PacketSoulSmelterUpdate(int x, int y, int z, int direction, String ownerName, boolean hasWork, int lavaAmount)
	{
		super(x, y, z, direction, ownerName);
		this.type = PacketTypeHandler.SOUL_SMELTER_UPDATE;
		this.hasWork = hasWork;
		this.lavaAmount = lavaAmount;
	}

	public boolean hasWork;
	public int lavaAmount;

	@Override
	protected void writeData(DataOutputStream dos) throws IOException
	{
		super.writeData(dos);
		dos.writeBoolean(hasWork);
		dos.writeInt(lavaAmount);
	}

	@Override
	protected void readData(DataInputStream dis) throws IOException
	{
		super.readData(dis);
		hasWork = dis.readBoolean();
		lavaAmount = dis.readInt();
	}

	@Override
	public void doItsThing(EntityPlayer player)
	{
		SoulSmelterMisc.syncSoulSmelterState(this, player);
	}
}
