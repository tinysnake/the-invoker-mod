package snake.mcmods.theinvoker.potions;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;

import scala.Console;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class PotionTickHandler implements ITickHandler
{

	@Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
	    
    }

	@Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
	    EntityPlayer p = (EntityPlayer)tickData[0];
	    p.addPotionEffect(par1PotionEffect)
    }

	@Override
    public EnumSet<TickType> ticks()
    {
	    return EnumSet.of(TickType.PLAYER);
    }

	@Override
    public String getLabel()
    {
	    return TIGlobal.MOD_ID+":"+this.getClass().getSimpleName();
    }

}
