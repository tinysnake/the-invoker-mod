package snake.mcmods.theinvoker.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;

public class TileTIBase extends TileEntity
{
	protected String ownerName;

	public String getOwnerName()
	{
		return ownerName;
	}

	public void setOwnerName(String name)
	{
		ownerName = name;
	}

	protected ForgeDirection direction;

	public ForgeDirection getDirection()
	{
		return direction;
	}

	public void setDirection(ForgeDirection val)
	{
		direction = val;
	}

	public void setDirection(int dir)
	{
		direction = ForgeDirection.getOrientation(dir);
	}

	public boolean getIsGhostBlock()
	{
		return false;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		// return super.getDescriptionPacket();
		return PacketTypeHandler.serialize(new PacketTileEntityUpdate(xCoord, yCoord, zCoord, getDirection().ordinal(), getOwnerName()));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("direction"))
			setDirection(nbt.getInteger("direction"));
		if (nbt.hasKey("owner"))
			setOwnerName(nbt.getString("owner"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("direction", getDirection().ordinal());
		nbt.setString("owner", getOwnerName());
	}
}
