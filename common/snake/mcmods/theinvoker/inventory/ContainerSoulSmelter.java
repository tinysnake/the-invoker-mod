package snake.mcmods.theinvoker.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import snake.mcmods.theinvoker.lib.SoulSmelterGUINetwork;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;

public class ContainerSoulSmelter extends Container
{

	private final int PLAYER_INVENTORY_ROWS = 3;
	private final int PLAYER_INVENTORY_COLUMNS = 9;

	public ContainerSoulSmelter(InventoryPlayer player, TileSoulSmelter soulSmelter)
	{
		this.addSlotToContainer(new Slot(soulSmelter, 0, 32, 22));
		
		this.soulSmelter = soulSmelter;

		// Add the player's inventory slots to the container
		for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
		{
			for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
			{
				this.addSlotToContainer(new Slot(player, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 9 + inventoryColumnIndex * 18, 60 + inventoryRowIndex * 18));
			}
		}

		// Add the player's action bar slots to the container
		for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
		{
			this.addSlotToContainer(new Slot(player, actionBarSlotIndex, 9 + actionBarSlotIndex * 18, 119));
		}
	}
	
	private TileSoulSmelter soulSmelter;
	
	@Override
	public void detectAndSendChanges()
	{
	    super.detectAndSendChanges();
	    for(Object c:crafters)
	    {
	    	soulSmelter.sendNetworkGUIData(this, (ICrafting)c);
	    }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int signiture, int value)
	{
	    super.updateProgressBar(signiture, value);
	    soulSmelter.receiveNetworkGUIData(signiture, value);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		ItemStack itemStack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		if (slot != null && slot.getHasStack())
		{
			ItemStack slotItemStack = slot.getStack();
			itemStack = slotItemStack.copy();
			if (slotIndex < 1)
			{

				if (!this.mergeItemStack(slotItemStack, 1, inventorySlots.size(), true))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(slotItemStack, 0, 1, false))
				{
					return null;
				}
			}

			if (slotItemStack.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		return itemStack;
	}

}
