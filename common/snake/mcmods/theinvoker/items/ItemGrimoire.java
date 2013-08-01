package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.grimoire.GrimoireNBT;
import snake.mcmods.theinvoker.logic.grimoire.GrimoireSystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrimoire extends ItemTIBase
{
	public static final String TAG_OWNER = "owner";
	public static final int MAX_DAMAGE_VALUE = 2399;
	
	public ItemGrimoire(int id)
	{
		super(id);
		this.setUnlocalizedName(TIName.ITEM_GRIMOIRE);
		this.maxStackSize = 1;
		this.setCreativeTab(TheInvoker.tab);
		this.setMaxDamage(MAX_DAMAGE_VALUE);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int itemInUseCount)
	{
		GrimoireSystem.INSTANCE.setIsCasting(false);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.isItemDamaged())
			return itemStack;
		entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
		return itemStack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
	{
	    itemStack.getTagCompound().setString(TAG_OWNER, player.getEntityName());
	}
	
	
}
