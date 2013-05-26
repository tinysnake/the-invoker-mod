package snake.mcmods.theinvoker.tileentities;

import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketTotemUpdate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class TileTotem extends TileEntity
{
    protected byte _direction;
    protected boolean _isGhostBlock;
    
    public TileTotem()
    {
        _direction = 2;
        _isGhostBlock=true;
    }

    public boolean getIsGhostBlock()
    {
        return _isGhostBlock;
    }

    public void setIsGhostBlock(boolean val)
    {
        _isGhostBlock = val;
    }

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
        return PacketTypeHandler.serialize(new PacketTotemUpdate(xCoord, yCoord, zCoord, getDirection(), getIsGhostBlock()));
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);
        if(nbtCompound.hasKey("direction"))
            setDirection(nbtCompound.getByte("direction"));
        if(nbtCompound.hasKey("isGhostBlock"))
            setIsGhostBlock(nbtCompound.getBoolean("isGhostBlock"));
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);
        nbtCompound.setByte("direction", getDirection());
        nbtCompound.setBoolean("isGhostBlock",getIsGhostBlock());
    }
}
