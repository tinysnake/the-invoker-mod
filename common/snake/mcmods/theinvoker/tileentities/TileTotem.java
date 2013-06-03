package snake.mcmods.theinvoker.tileentities;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.blocks.BlockTotem;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.logic.TotemLogicHandler;
import snake.mcmods.theinvoker.logic.TotemMisc;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileTotem extends TileTIBase
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
    public boolean getIsGhostBlock() {
        return this.getType() == TotemType.GHOST;
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
        return PacketTypeHandler.serialize(new PacketTileEntityUpdate(xCoord, yCoord, zCoord,
                getDirection().ordinal(), getOwnerName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        BlockTotem block = TIBlocks.totem;
        return AxisAlignedBB.getAABBPool().getAABB(block.getBlockBoundsMinX() + xCoord, block.getBlockBoundsMinY() + yCoord, block.getBlockBoundsMinZ() + zCoord,
                block.getBlockBoundsMaxX() + xCoord, block.getBlockBoundsMaxY() + yCoord + 1, block.getBlockBoundsMaxZ() + zCoord);
    }
}
