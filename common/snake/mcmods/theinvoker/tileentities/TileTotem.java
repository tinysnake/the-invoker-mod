package snake.mcmods.theinvoker.tileentities;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.constants.TotemMisc;
import snake.mcmods.theinvoker.constants.TotemMisc.TotemType;
import snake.mcmods.theinvoker.logic.TotemLogicHandler;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketTotemUpdate;
import snake.mcmods.theinvoker.utils.CoordHelper;

public class TileTotem extends TileEntity
{
    public TileTotem()
    {
        _direction = ForgeDirection.NORTH;
    }

    protected ForgeDirection _direction;
    private boolean init;

    private void init()
    {
        if (this.worldObj.isRemote)
        {
            if (this.getBlockMetadata() > 0)
                TotemLogicHandler.INSTANCE.registerTotem(this);
        }
    }

    public ForgeDirection getDirection()
    {
        return _direction;
    }

    public void setDirection(ForgeDirection val)
    {
        _direction = val;
    }

    public void setDirection(int dir)
    {
        _direction = ForgeDirection.getOrientation(dir);
    }

    public int getEffectiveDirection()
    {
        return TotemMisc.getEffectiveRangeByMetadata(TotemType.getType(this.blockMetadata));
    }

    @Override
    public void updateEntity() {
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
        return PacketTypeHandler.serialize(new PacketTotemUpdate(xCoord, yCoord, zCoord, getDirection().ordinal()));
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
