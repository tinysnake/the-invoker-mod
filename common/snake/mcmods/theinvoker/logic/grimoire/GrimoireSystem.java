package snake.mcmods.theinvoker.logic.grimoire;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import scala.Console;
import snake.mcmods.theinvoker.items.ItemGrimoire;
import snake.mcmods.theinvoker.items.TIItems;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class GrimoireSystem implements ITickHandler
{
	public static final GrimoireSystem INSTANCE = new GrimoireSystem();

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
				if(grimoire.getItemDamage() > 0)
				{
				int stepSize = GrimoireMisc.getCDStepSize(grimoire);
				int dmg = Math.max(0, grimoire.getItemDamage() - stepSize);
				grimoire.setItemDamage(dmg);
				Console.println("charging girmoire");
				}else if(GrimoireMisc.getOwnerName(grimoire)==null)
				{
					GrimoireMisc.setOwner(grimoire, ep);
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
		return this.getClass().getSimpleName();
	}

	public void startCDCounting(ItemStack grimoire)
	{
		if (grimoire != null && grimoire.itemID == TIItems.grimoire.itemID)
		{
			grimoire.setItemDamage(ItemGrimoire.MAX_DAMAGE_VALUE);
			GrimoireMisc.setCDStepSize(grimoire, 5);
		}
	}
}
