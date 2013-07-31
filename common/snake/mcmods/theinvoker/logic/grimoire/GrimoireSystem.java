package snake.mcmods.theinvoker.logic.grimoire;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class GrimoireSystem implements ITickHandler
{
	public static final GrimoireSystem INSTANCE = new GrimoireSystem();
	
	private boolean isHoldingGrimoire;
	
	private boolean isCasting;
	
	private boolean isCharging;
	
	private int castTimer;
	
	private int maxCastTimer;
	
	private int chargeTimer;
	
	private int maxChargeTimer;

	public boolean getIsHoldingGrimoire()
	{
		return isHoldingGrimoire;
	}

	public void setIsHoldingGrimoire(boolean isHoldingGrimoire)
	{
		this.isHoldingGrimoire = isHoldingGrimoire;
	}

	public boolean getIsCasting()
	{
		return isCasting;
	}

	public void setIsCasting(boolean isCasting)
	{
		this.isCasting = isCasting;
		if(!isCasting)
			castTimer=0;
	}

	public boolean getIsCharging()
	{
		return isCharging;
	}

	public void setIsCharging(boolean isCharging)
	{
		this.isCharging = isCharging;
	}

	public int getCastTimer()
	{
		return castTimer;
	}

//	public void setCastTimer(int castTimer)
//	{
//		this.castTimer = castTimer;
//	}

	public int getMaxCastTimer()
	{
		return maxCastTimer;
	}

	public void setMaxCastTimer(int maxCastTimer)
	{
		this.maxCastTimer = maxCastTimer;
	}

	public int getChargeTimer()
	{
		return chargeTimer;
	}

//	public void setChargeTimer(int chargeTimer)
//	{
//		this.chargeTimer = chargeTimer;
//	}

	public int getMaxChargeTimer()
	{
		return maxChargeTimer;
	}

	public void setMaxChargeTimer(int maxChargeTimer)
	{
		this.maxChargeTimer = maxChargeTimer;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		EntityPlayer ep = (EntityPlayer)tickData[0];
		if (ep.inventory.hasItem(TIItems.grimoire.itemID))
		{
			ItemStack grimoire = null;
			for (ItemStack stk : ep.inventory.mainInventory)
			{
				if (stk != null && stk.itemID == TIItems.grimoire.itemID)
				{
					grimoire = stk;
					break;
				}
			}
			if (grimoire != null)
			{
				if (GrimoireNBT.getOwnerName(grimoire) == null)
				{
					GrimoireNBT.setOwner(grimoire, ep);
				}
				if(getIsCasting())
				{
					castTimer++;
				}
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel()
	{
		return  TIGlobal.MOD_ID + ":"+this.getClass().getSimpleName();
	}
}
