package snake.mcmods.theinvoker.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;

public class EntitySoulStoneMonitor extends Entity implements IEntityNameTagSprite
{
	public static final int LIFE_SPAN = 100;

	public EntitySoulStoneMonitor(World world, TileSoulStone tss)
	{
		super(world);
		this.tss = tss;
		if (tss.monitor != null)
		{
			tss.monitor.isDead = true;
		}
		tss.monitor = this;
		this.setSize(0.25F, 0.25F);
		this.ignoreFrustumCheck = true;

	}

	private TileSoulStone tss;

	private int age = 0;

	private ForgeDirection dir;

	public ForgeDirection getDirection()
	{
		return dir;
	}

	public void setDirection(ForgeDirection dir)
	{
		this.dir = dir;
	}

	@Override
	protected void entityInit()
	{

	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (isDead)
			return;
		if (age == LIFE_SPAN)
		{
			int len = rand.nextInt(2) + 3;
			for (int i = 0; i < len; i++)
			{
				this.worldObj.spawnParticle("flame", this.posX + 0.5F + (rand.nextFloat() * 2 - 1) / 6, this.posY + 0.5F + (rand.nextFloat() * 2 - 1) / 3, this.posZ + 0.5F + (rand.nextFloat() * 2 - 1) / 6, 0, 0, 0);
			}
		}
		age--;

		if (age < 0)
		{
			tss.monitor = null;
			this.isDead = true;
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

	public void setupLifeSpan()
	{
		age = LIFE_SPAN;
	}
	
	@Override
	public String getLabel()
	{
		if (tss != null && tss.getEnergyContainer() != null)
		{
			return String.valueOf(tss.getEnergyContainer().getEnergyLevel());
		}
		return "";
	}
}
