package snake.mcmods.theinvoker.tileentities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.blocks.BlockTotem;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemCenter;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemMisc;
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
	public boolean getIsGhostBlock()
	{
		return SeductionTotemMisc.isGhostBlock(getBlockMetadata());
	}

	public boolean getIsBroken()
	{
		return SeductionTotemMisc.getIsBroken(getAge());
	}

	private int _age;
	private boolean init;

	public int getAge()
	{
		return _age;
	}

	public void setAge(int val)
	{
		_age = val;
	}

	public boolean isEntityInRange(Entity e)
	{
		return isInRange(e.posX, e.posY, e.posZ);
	}

	public boolean isInRange(double x, double y, double z)
	{
		double distance = this.getDistanceFrom(x, y, z) / 16F;
		return distance < SeductionTotemMisc.EFFECTIVE_RANGE && distance > SeductionTotemMisc.LOSE_EFFECT_RANGE;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!init)
		{
			setup();
			init = true;
		}
		if (this.getIsGhostBlock() || getIsBroken())
			return;
		if (getAge() <= 0)
			setAge(SeductionTotemMisc.getAgeFromDamageValue(this.getBlockMetadata()));
		if (!getIsBroken())
			_age++;
		if (getIsBroken()
		        && this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != SeductionTotemMisc.BROKEN_METADATA)
		{
			this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord,
			        SeductionTotemMisc.BROKEN_METADATA, 4);
			int blockID = this.worldObj.getBlockId(xCoord, yCoord + 1, zCoord);
			if (blockID == TIBlocks.seductionTotem.blockID)
			{
				this.worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
			}
		}
	}

	private void setup()
	{
		if (this.worldObj.isRemote && !this.getIsGhostBlock() && !this.getIsBroken())
		{
			SeductionTotemCenter.INSTANCE.registerSeductionTotem(this);
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		PacketSeductionTotemUpdate p = new PacketSeductionTotemUpdate(xCoord, yCoord, zCoord, this
		        .getDirection().ordinal(), getOwnerName(), getAge());
		return PacketTypeHandler.serialize(p);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtCompound)
	{
		super.readFromNBT(nbtCompound);
		setAge(nbtCompound.getInteger("age"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtCompound)
	{
		super.writeToNBT(nbtCompound);
		nbtCompound.setInteger("age", getAge());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		BlockTotem block = TIBlocks.totem;
		return AxisAlignedBB.getAABBPool().getAABB(block.getBlockBoundsMinX() + xCoord,
		        block.getBlockBoundsMinY() + yCoord, block.getBlockBoundsMinZ() + zCoord,
		        block.getBlockBoundsMaxX() + xCoord, block.getBlockBoundsMaxY() + yCoord + 1,
		        block.getBlockBoundsMaxZ() + zCoord);
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		if (this.worldObj.isRemote && !this.getIsGhostBlock())
		{
			SeductionTotemCenter.INSTANCE.unregisterSeductionTotem(this);
		}
		init = false;
	}
}
