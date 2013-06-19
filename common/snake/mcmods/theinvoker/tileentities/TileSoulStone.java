package snake.mcmods.theinvoker.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.energy.IEnergyContainerWrapper;
import snake.mcmods.theinvoker.energy.IMultiblockEnergyWrapper;
import snake.mcmods.theinvoker.energy.TIEnergy;
import snake.mcmods.theinvoker.entities.EntitySoulStoneMonitor;
import snake.mcmods.theinvoker.logic.SoulStoneMisc;

public class TileSoulStone extends TileTIBase implements IEnergyContainerWrapper, IMultiblockEnergyWrapper
{
	public static final int MAX_ENERGY_REQUEST = 5;

	public TileSoulStone()
	{
		setDirection(0);
		originCoords = new int[3];
	}

	private EnergyContainer energyContainer;
	private int[] originCoords;
	public EntitySoulStoneMonitor monitor;

	@Override
	public EnergyContainer getEnergyContainer()
	{
		return energyContainer;
	}

	public boolean getIsFormless()
	{
		return !energyContainer.getIsAvailable();
	}

	public void setIsFormless(boolean val)
	{
		energyContainer.setIsAvailable(!val);
	}
	
	public void setOriginCoords(int x, int y, int z)
	{
		originCoords[0]=x;
		originCoords[1]=y;
		originCoords[2]=z;
	}

	@Override
	public int[] getCloestCoordsTo(int x, int y, int z)
	{
		int dx = x, dy = y, dz = z;
		int meta = getBlockMetadata();
		if (meta >= 0 && meta < SoulStoneMisc.FORMABLE_SIZE.length)
		{
			int[] size = SoulStoneMisc.FORMABLE_SIZE[meta];
			if (x <= originCoords[0])
				dx = originCoords[0];
			else if (x >= originCoords[0] + size[0])
				dx = originCoords[0] + size[0];
			
			if (y <= originCoords[1])
				dy = originCoords[1];
			else if (y >= originCoords[1] + size[1])
				dy = originCoords[1] + size[1];
			
			if (z <= originCoords[2])
				dz = originCoords[2];
			else if (z >= originCoords[2] + size[2])
				dz = originCoords[2] + size[2];
		}
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

	public void transferFrom(TileSoulStone tss)
	{
		this.ownerName = tss.getOwnerName();
		this.energyContainer = tss.getEnergyContainer();
	}

	private void setupEnergyContainer()
	{
		if (energyContainer == null)
		{
			energyContainer = new EnergyContainer(this, false, TIEnergy.soul.getID());
			energyContainer.setEffectiveRange(SoulStoneMisc.EFFECTIVE_RANGE);
			energyContainer.setEnergyCapacity(SoulStoneMisc.CAPACITY_OF_METADATA[getBlockMetadata()]);
			energyContainer.setMaxEnergyRequest(MAX_ENERGY_REQUEST * (getBlockMetadata() + 1));
		}
		if (!energyContainer.getIsRegistered())
		{
			energyContainer.register();
		}
	}

	private static final String TAG_ENERGY_CONTAINER = "energyContainer";
	private static final String TAG_ORIGN_COORDS = "origin";
}
