package snake.mcmods.theinvoker.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrimoire extends ItemTIBase
{
	public static final String TAG_OWNER = "owner";
	
	public ItemGrimoire(int id)
	{
		super(id);
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
	{
	    itemStack.getTagCompound().setString(TAG_OWNER, player.getEntityName());
	}
}
