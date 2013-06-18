package snake.mcmods.theinvoker.energy;

import net.minecraft.util.StatCollector;

public class EnergyForce
{
	public EnergyForce(int id)
	{
		this.id = id;
	}

	protected int id;
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
