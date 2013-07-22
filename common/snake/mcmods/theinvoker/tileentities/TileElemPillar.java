package snake.mcmods.theinvoker.tileentities;

import snake.mcmods.theinvoker.blocks.BlockSoulStone;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.lib.ElemPillarType;

public class TileElemPillar extends TileSoulStone
{
	@Override
	protected void setupEnergyContainer()
	{
		if (energyContainer == null)
		{
			int metadata = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int eid = ElemPillarType.convertToEnergyID(ElemPillarType.values()[metadata]);
			energyContainer = new EnergyContainer(this, false, eid);
			energyContainer.setEffectiveRange(BlockSoulStone.EFFECTIVE_RANGE);
			energyContainer.setEnergyCapacity(BlockSoulStone.CAPACITY_OF_SIZES[getBlockMetadata()]);
			energyContainer.setMaxEnergyRequest(MAX_ENERGY_REQUEST * (getBlockMetadata() + 1));
		}
		if (!energyContainer.getIsRegistered())
		{
			energyContainer.register();
		}
	}
}
