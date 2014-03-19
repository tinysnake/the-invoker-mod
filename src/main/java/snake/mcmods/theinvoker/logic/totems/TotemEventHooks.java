package snake.mcmods.theinvoker.logic.totems;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class TotemEventHooks
{
	@ForgeSubscribe
	public void handleEntityDropsEvent(LivingDropsEvent e)
	{
		TotemCenter.INSTANCE.updateLogicWhileEntityLivingDrops(e);
	}
}
