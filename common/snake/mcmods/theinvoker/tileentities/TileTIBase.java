package snake.mcmods.theinvoker.tileentities;

import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;


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
        return PacketTypeHandler.serialize(new PacketTileEntityUpdate(xCoord, yCoord, zCoord,
                getDirection().ordinal(), getOwnerName()));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);
        if (nbtCompound.hasKey("direction"))
            setDirection(nbtCompound.getInteger("direction"));
        if(nbtCompound.hasKey("owner"))
            setOwnerName(nbtCompound.getString("owner"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);
        nbtCompound.setInteger("direction", getDirection().ordinal());
        nbtCompound.setString("owner", getOwnerName());
    }
}
