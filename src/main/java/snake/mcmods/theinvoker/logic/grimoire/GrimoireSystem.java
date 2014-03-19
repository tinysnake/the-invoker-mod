package snake.mcmods.theinvoker.logic.grimoire;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.spirits.ISpiritState;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class GrimoireSystem implements ITickHandler
{
	public static final GrimoireSystem INSTANCE = new GrimoireSystem();

	private boolean isHoldingGrimoire;

	private boolean isCasting;

	private boolean isCharging;

	private int castTimer;

	private int chargeTimer;

	private ISpiritState state;

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
		if (!isCasting)
			castTimer = 0;
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

	public int getMaxCastDuration()
	{
		return state.getCurrentSpell().getMaxCastDuration();
	}

	public int getChargeTimer()
	{
		return chargeTimer;
	}

	public int getMaxChargeTimer()
	{
		return state.getCurrentSpell().getMaxChargeDuration();
	}

	public void startCasting()
	{

	}

	public void stopCasting()
	{

	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		EntityPlayer ep = (EntityPlayer)tickData[0];
		if (ep.getHeldItem() != null && ep.getHeldItem().itemID == TIItems.grimoire.itemID)
		{
			if (!isHoldingGrimoire)
				setIsHoldingGrimoire(true);
		}
		else if (isHoldingGrimoire)
		{
			setIsHoldingGrimoire(false);
		}
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
				if (getIsCasting())
				{
					castTimer++;
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel()
	{
		return TIGlobal.MOD_ID + ":" + this.getClass().getSimpleName();
	}
}
