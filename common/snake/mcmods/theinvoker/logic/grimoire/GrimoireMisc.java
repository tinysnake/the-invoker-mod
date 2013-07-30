package snake.mcmods.theinvoker.logic.grimoire;

import snake.mcmods.theinvoker.items.TIItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GrimoireMisc
{
	public static final String CD_STEP_SIZE = "cdStepSize";
	public static final String OWNER = "owner";

	public static int getCDStepSize(ItemStack stack)
	{
		if (stack == null || stack.itemID != TIItems.grimoire.itemID || !stack.hasTagCompound())
			return 0;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt.hasKey(CD_STEP_SIZE))
		{
			return nbt.getInteger(CD_STEP_SIZE);
		}

		return 0;
	}

	public static void setCDStepSize(ItemStack stack, int size)
	{
		if (stack == null || stack.itemID != TIItems.grimoire.itemID)
			return;
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setInteger(CD_STEP_SIZE, size);
	}

	public static String getOwnerName(ItemStack stack)
	{
		if (stack == null || stack.itemID != TIItems.grimoire.itemID || !stack.hasTagCompound())
			return null;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt.hasKey(OWNER))
		{
			return nbt.getString(OWNER);
		}

		return null;
	}

	public static void setOwner(ItemStack stack, EntityPlayer ep)
	{
		if (stack == null || stack.itemID != TIItems.grimoire.itemID)
			return;
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setString(OWNER, ep.getEntityName());
	}
}
