package snake.mcmods.theinvoker.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.blocks.BlockTotem;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.logic.SeductionTotemMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketSeductionTotemUpdate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSeductionTotem extends TileTIBase
{

    public TileSeductionTotem()
    {
        direction = ForgeDirection.NORTH;
        setAge(0);
    }

    @Override
    public boolean isGhostBlock() 
    {
        return SeductionTotemMisc.isGhostBlock(getBlockMetadata());
    }

    public boolean getIsBroken()
    {
        return SeductionTotemMisc.getIsBroken(getAge());
    }

    private int _age;

    public int getAge()
    {
        return _age;
    }

    public void setAge(int val)
    {
        _age = val;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (this.isGhostBlock())
            return;
        if (getAge() <= 0)
            setAge(SeductionTotemMisc.getAgeFromMetadata(this.getBlockMetadata()));
        if (!getIsBroken())
            _age--;
        else if (!SeductionTotemMisc.isGhostBlock(this.getBlockMetadata()))
        {
            int blockID = this.worldObj.getBlockId(xCoord, yCoord + 1, zCoord);
            if (blockID == TIBlocks.seductionTotem.blockID)
            {
                this.worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        PacketSeductionTotemUpdate p = new PacketSeductionTotemUpdate(xCoord, yCoord, zCoord, this.getDirection().ordinal(), getOwnerName(), getAge());
        return PacketTypeHandler.serialize(p);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound) {
        super.readFromNBT(nbtCompound);
        setAge(nbtCompound.getInteger("age"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtCompound) {
        super.writeToNBT(nbtCompound);
        nbtCompound.setInteger("age", getAge());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        BlockTotem block = TIBlocks.totem;
        return AxisAlignedBB.getAABBPool().getAABB(block.getBlockBoundsMinX() + xCoord, block.getBlockBoundsMinY() + yCoord, block.getBlockBoundsMinZ() + zCoord,
                block.getBlockBoundsMaxX() + xCoord, block.getBlockBoundsMaxY() + yCoord + 1, block.getBlockBoundsMaxZ() + zCoord);
    }
}
