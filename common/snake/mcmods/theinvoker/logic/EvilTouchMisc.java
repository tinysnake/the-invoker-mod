package snake.mcmods.theinvoker.logic;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import snake.mcmods.theinvoker.items.TIItems;

public class EvilTouchMisc
{
	public static final int MAX_USAGE_DAMGE_VALUE = 500;

	public static void onEvilTouchUsed(ItemStack is, EntityLiving p)
	{
		if (is.getItem().itemID == TIItems.evilTouch.itemID)
		{
			is.damageItem(1, p);
		}
	}
	
	public static boolean isPlayHoldingEvilTouch(EntityPlayer ep)
	{
		if(ep==null)
			return false;
		ItemStack item = ep.getHeldItem();
		if(item!=null&&item.itemID==TIItems.evilTouch.itemID&&item.getItemDamage()<MAX_USAGE_DAMGE_VALUE)
		{
			return true;
		}
		return false;
	}
}
