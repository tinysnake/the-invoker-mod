package snake.mcmods.theinvoker.logic.ai;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemCenter;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemMisc;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;

public class EntityAISeduction extends EntityAIBase
{

	public EntityAISeduction(EntityLiving e)
	{
		this.living = e;
		this.setMutexBits(4);
	}

	private EntityLiving living;
	private boolean originAvoidWater;
	private int tickingTimer;

	@Override
	public boolean shouldExecute()
	{
		return living != null ? SeductionTotemCenter.INSTANCE.isThereAnySeductionTotemInRange(living.posX, living.posY, living.posZ) : false;
	}

	@Override
	public boolean continueExecuting()
	{
		return !living.getNavigator().noPath();
	}

	@Override
	public void startExecuting()
	{
		originAvoidWater = living.getNavigator().getAvoidsWater();
		living.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void updateTask()
	{
		if (--tickingTimer <= 0)
		{
			tickingTimer = 10;
			TileSeductionTotem tst = SeductionTotemCenter.INSTANCE.getNearestSeductionTotem(living.posX, living.posY, living.posZ);
			if (tst != null)
			{
				living.getNavigator().tryMoveToXYZ(tst.xCoord + randomOffset(living.getRNG()), tst.yCoord, tst.zCoord + randomOffset(living.getRNG()), 0.1F);
			}
		}
	}

	@Override
	public void resetTask()
	{
		living.getNavigator().clearPathEntity();
		living.getNavigator().setAvoidsWater(originAvoidWater);
	}

	private float randomOffset(Random r)
	{
		return 2 * r.nextInt(SeductionTotemMisc.LOSE_EFFECT_RANGE) - SeductionTotemMisc.LOSE_EFFECT_RANGE;
	}
}
