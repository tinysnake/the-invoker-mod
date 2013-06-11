package snake.mcmods.theinvoker.energy;

import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIName;
import net.minecraft.util.StatCollector;

public class EnergyForce
{
	public static void init()
	{
		EnergyCenter.INSTANCE.registerEnergyForce(new EnergyForce(TIItems.soulShard.itemID).setUnlocalizedName(TIName.ENERGY_SOUL));
	}

	public EnergyForce(int id)
	{
		this.id = id;
	}

	private int id;
	private String unlocalizedName;

	public int getID()
	{
		return id;
	}

	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}

	public EnergyForce setUnlocalizedName(String name)
	{
		unlocalizedName = name;
		return this;
	}

	public String getName()
	{
		return StatCollector.translateToLocal("energy." + getUnlocalizedName() + ".name");
	}
}
