package snake.mcmods.theinvoker.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketTotemUpdate;

public class TileTotem extends TileEntity
{
    public TileTotem()
    {
        _direction = 2;
    }
    
    protected byte _direction;

    public byte getDirection()
    {
        return _direction;
    }

    public void setDirection(byte val)
    {
        _direction = val;
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        return PacketTypeHandler.serialize(new PacketTotemUpdate(xCoord, yCoord, zCoord, getDirection()));
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);
        if(nbtCompound.hasKey("direction"))
            setDirection(nbtCompound.getByte("direction"));
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);
        nbtCompound.setByte("direction", getDirection());
    }
    
    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
    }
}
