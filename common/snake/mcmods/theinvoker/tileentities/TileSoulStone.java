package snake.mcmods.theinvoker.tileentities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import snake.mcmods.theinvoker.blocks.BlockSoulStone;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.energy.IEnergyContainerWrapper;
import snake.mcmods.theinvoker.energy.IMultiblockEnergyWrapper;
import snake.mcmods.theinvoker.energy.TIEnergy;
import snake.mcmods.theinvoker.net.PacketTypeHandler;
import snake.mcmods.theinvoker.net.packet.PacketEnergyContainerUpdate;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileSoulStone extends TileMultiBlockBase implements IEnergyContainerWrapper, IMultiblockEnergyWrapper
{
	public static final int MAX_ENERGY_REQUEST = 5;

	public TileSoulStone()
	{
		setDirection(0);
		structureSize = new int[3];
	}

	protected EnergyContainer energyContainer;
	protected int[] structureSize;
	public Entity monitor;

	@Override
	public EnergyContainer getEnergyContainer()
	{
		return energyContainer;
	}

	@Override
	public boolean getIsFormless()
	{
		return !energyContainer.getIsAvailable();
	}

	@Override
	public void setIsFormless(boolean val)
	{
		energyContainer.setIsAvailable(!val);
	}

	public void setStructureSize(int x, int y, int z)
	{
		structureSize[0] = x;
		structureSize[1] = y;
		structureSize[2] = z;
	}

	@Override
	public int[] getCloestCoordsTo(int x, int y, int z)
	{
		int dx = x, dy = y, dz = z;

		if (x <= originCoords[0])
			dx = originCoords[0];
		else if (x >= originCoords[0] + structureSize[0])
			dx = originCoords[0] + structureSize[0];

		if (y <= originCoords[1])
			dy = originCoords[1];
		else if (y >= originCoords[1] + structureSize[1])
			dy = originCoords[1] + structureSize[1];

		if (z <= originCoords[2])
			dz = originCoords[2];
		else if (z >= originCoords[2] + structureSize[2])
			dz = originCoords[2] + structureSize[2];

		return new int[] { dx, dy, dz };
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		setupEnergyContainer();
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		if (energyContainer != null)
			energyContainer.destroy();
		if (monitor != null)
		{
			monitor.isDead = true;
			monitor = null;
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		setupEnergyContainer();
		return super.getDescriptionPacket();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtCompound)
	{
		super.readFromNBT(nbtCompound);
		if (nbtCompound.hasKey(TAG_ENERGY_CONTAINER))
			energyContainer = EnergyContainer.readFromNBT(nbtCompound.getCompoundTag(TAG_ENERGY_CONTAINER), this);
		setupEnergyContainer();
		if (nbtCompound.hasKey(TAG_ORIGN_COORDS))
			originCoords = nbtCompound.getIntArray(TAG_ORIGN_COORDS);
		else
			originCoords = new int[] { this.xCoord, this.yCoord, this.zCoord };

		if (nbtCompound.hasKey(TAG_STRUCTURE_SIZE))
			originCoords = nbtCompound.getIntArray(TAG_STRUCTURE_SIZE);
		else
			originCoords = new int[] { 1, 1, 1 };
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtCompound)
	{
		super.writeToNBT(nbtCompound);
		setupEnergyContainer();
		if (energyContainer != null)
		{
			nbtCompound.setTag(TAG_ENERGY_CONTAINER, energyContainer.writeToNBT(new NBTTagCompound()));
		}
		nbtCompound.setIntArray(TAG_ORIGN_COORDS, originCoords);
	}

	@Override
	public void transferFrom(TileMultiBlockBase tmb)
	{
		TileSoulStone tss = (TileSoulStone)tmb;
		this.ownerName = tss.getOwnerName();
		this.energyContainer = tss.getEnergyContainer();
	}

	protected void setupEnergyContainer()
	{
		if (energyContainer == null)
		{
			energyContainer = new EnergyContainer(this, false, TIEnergy.soul.getID());
			energyContainer.setEffectiveRange(BlockSoulStone.EFFECTIVE_RANGE);
			energyContainer.setEnergyCapacity(BlockSoulStone.CAPACITY_OF_SIZES[getBlockMetadata()]);
			energyContainer.setMaxEnergyRequest(MAX_ENERGY_REQUEST * (getBlockMetadata() + 1));
		}
		if (!energyContainer.getIsRegistered())
		{
			energyContainer.register();
		}
	}

	private static final String TAG_ENERGY_CONTAINER = "energyContainer";
	private static final String TAG_ORIGN_COORDS = "origin";
	private static final String TAG_STRUCTURE_SIZE = "size";
}
