package snake.mcmods.theinvoker.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElementPurifier extends Container
{
	private final int INPUT_SLOT_X = 81;
	private final int INPUT_SLOT_Y = 7;
	private final int PLAYER_INVENTORY_ROWS = 3;
	private final int PLAYER_INVENTORY_COLUMNS = 9;

	public ContainerElementPurifier(InventoryPlayer player, TileElementPurifier tep)
	{
		this.tep = tep;
		this.addSlotToContainer(new Slot(tep, 0, INPUT_SLOT_X, INPUT_SLOT_Y));
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
	
	private TileElementPurifier tep;


	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (Object c : crafters)
		{
			tep.sendNetworkGUIData(this, (ICrafting)c);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int signiture, int value)
	{
		super.updateProgressBar(signiture, value);
		tep.receiveNetworkGUIData(signiture, value);
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
		Slot slot = (Slot)inventorySlots.get(slotIndex);
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
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		return itemStack;
	}

}
