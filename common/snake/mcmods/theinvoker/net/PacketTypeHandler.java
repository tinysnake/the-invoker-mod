package snake.mcmods.theinvoker.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.net.packet.PacketEnergyConsumerUpdate;
import snake.mcmods.theinvoker.net.packet.PacketEnergyContainerUpdate;
import snake.mcmods.theinvoker.net.packet.PacketSeductionTotemUpdate;
import snake.mcmods.theinvoker.net.packet.PacketSoulSmelterUpdate;
import snake.mcmods.theinvoker.net.packet.PacketTI;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;

public enum PacketTypeHandler
{
	TILE_TI(PacketTileEntityUpdate.class), SEDUCTION_TOTEM(PacketSeductionTotemUpdate.class), ENERGY_CONTAINER(PacketEnergyContainerUpdate.class), ENERGY_CONSUMER(PacketEnergyConsumerUpdate.class), SOUL_SMELTER_UPDATE(PacketSoulSmelterUpdate.class);

	private Class<? extends PacketTI> clazz;

	PacketTypeHandler(Class<? extends PacketTI> clazz)
	{

		this.clazz = clazz;
	}

	public static PacketTI deserialize(byte[] data)
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int type = bis.read();
		PacketTI p = null;
		try
		{
			p = values()[type].clazz.newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		DataInputStream dis = new DataInputStream(bis);
		p.fromByteArray(dis);

		return p;
	}

	public static Packet serialize(PacketTI p)
	{
		byte[] data = p.toByteArray();
		Packet250CustomPayload result = new Packet250CustomPayload();
		result.channel = TIGlobal.CHANNEL;
		result.data = data;
		result.length = data.length;
		result.isChunkDataPacket = p.isChunkPacket;

		return result;
	}
}
