package snake.mcmods.theinvoker.lib;

import snake.mcmods.theinvoker.energy.TIEnergy;

public enum ElemPillarType
{
	ICE,
	FIRE,
	WIND,
	DARNESS;
	
	public static int convertToEnergyID(ElemPillarType t)
	{
		switch(t)
		{
			case DARNESS:
				return TIEnergy.darkness.getID();
			case FIRE:
				return TIEnergy.fire.getID();
			case ICE:
				return TIEnergy.ice.getID();
			case WIND:
				return TIEnergy.wind.getID();
			default:
				return 0;
			
		}
	}
}
