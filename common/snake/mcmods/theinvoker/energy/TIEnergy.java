package snake.mcmods.theinvoker.energy;

import snake.mcmods.theinvoker.lib.constants.TIEnergyID;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class TIEnergy
{
	public static EnergyForce soul;
	public static EnergyForce ice;
	public static EnergyForce fire;
	public static EnergyForce wind;
	public static EnergyForce darkness;
	
	public static void init()
	{
		soul = new EnergyForce(TIEnergyID.SOUL).setUnlocalizedName(TIName.ENERGY_SOUL);
		ice = new EnergyForce(TIEnergyID.ICE).setUnlocalizedName(TIName.ENERGY_ICE);
		fire = new EnergyForce(TIEnergyID.FIRE).setUnlocalizedName(TIName.ENERGY_FIRE);
		wind = new EnergyForce(TIEnergyID.WIND).setUnlocalizedName(TIName.ENERGY_WIND);
		darkness = new EnergyForce(TIEnergyID.DARKNESS).setUnlocalizedName(TIName.ENERGY_DARKNESS);
		
		TIEnergyID.SOUL = EnergyCenter.INSTANCE.registerEnergyForce(soul);
		TIEnergyID.ICE = EnergyCenter.INSTANCE.registerEnergyForce(ice);
		TIEnergyID.FIRE = EnergyCenter.INSTANCE.registerEnergyForce(fire);
		TIEnergyID.WIND = EnergyCenter.INSTANCE.registerEnergyForce(wind);
		TIEnergyID.DARKNESS = EnergyCenter.INSTANCE.registerEnergyForce(darkness);
	}
}
