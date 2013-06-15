package snake.mcmods.theinvoker.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;


public class EntitySoulStoneMonitor extends Entity
{
	public static final int LIFE_SPAN = 100;
	
	public EntitySoulStoneMonitor(World world, TileSoulStone tss)
    {
	    super(world);
	    this.tss=tss;
    }
	
	private TileSoulStone tss;
	
	private int age = 0;

	@Override
    protected void entityInit()
    {
	    
    }
	
	@Override
	public void onEntityUpdate()
	{
	    super.onEntityUpdate();
	    age--;
	    if(age<0)
	    {
	    	this.isDead=true;
	    }
	}

	@Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
	   
    }

	@Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
	    
    }

	public String getLabel()
	{
		if(tss!=null&&tss.getEnergyContainer()!=null)
		{
			return String.valueOf(tss.getEnergyContainer().getEnergyLevel());
		}
		return "";
	}
}
