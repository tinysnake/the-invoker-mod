package snake.mcmods.theinvoker.logic.potion;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import scala.Console;

public class PotionCenter
{
	public void onUpdate(LivingUpdateEvent e)
	{
		
	}

	@ForgeSubscribe
	public void onBeenAttacked(LivingAttackEvent e)
	{
		Console.println(e.entityLiving.worldObj.isRemote);
	}

	@ForgeSubscribe
	public void onHurt(LivingHurtEvent e)
	{
		
	}

	@ForgeSubscribe
	public void onFall(LivingFallEvent e)
	{
		
	}

	@ForgeSubscribe
	public void onJump(LivingJumpEvent e)
	{
		
	}

	@ForgeSubscribe
	public void onDied(LivingDeathEvent e)
	{
		
	}
	
	@ForgeSubscribe
	public void onAttack(AttackEntityEvent e)
	{
		
	}
}
