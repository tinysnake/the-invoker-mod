package snake.mcmods.theinvoker.tileentities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.logic.TotemLogicHandler;
import snake.mcmods.theinvoker.logic.TotemMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketTotemUpdate;

public class TileTotem extends TileEntity
{
    public TileTotem()
    {
        direction = ForgeDirection.NORTH;
    }

    private boolean init;

    private void init()
    {
        if (this.worldObj.isRemote)
        {
            if (this.getBlockMetadata() > 0)
                TotemLogicHandler.INSTANCE.registerTotem(this);
        }
    }
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
    
    public TotemType getType()
    {
        return TotemType.getType(this.getBlockMetadata());
    }

    public int getEffectiveRange()
    {
        return TotemMisc.getEffectiveRangeByMetadata(TotemType.getType(this.getBlockMetadata()));
    }

    public boolean isEntityInRange(Entity e)
    {
        return this.getDistanceFrom(e.posX, e.posY, e.posZ) / 16F <= getEffectiveRange();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!init && !isInvalid())
        {
            init();
            init = true;
        }
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        if (this.worldObj.isRemote && this.getBlockMetadata() > 0)
        {
            TotemLogicHandler.INSTANCE.unregisterTotem(this);
        }
        init = false;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return PacketTypeHandler.serialize(new PacketTotemUpdate(xCoord, yCoord, zCoord,
                getDirection().ordinal(), getOwnerName()));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);
        if (nbtCompound.hasKey("direction"))
            setDirection(nbtCompound.getInteger("direction"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);
        nbtCompound.setInteger("direction", getDirection().ordinal());
    }
}
