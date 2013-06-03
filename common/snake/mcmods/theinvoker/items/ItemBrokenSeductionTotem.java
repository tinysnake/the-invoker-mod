package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIName;
import net.minecraft.item.Item;

public class ItemBrokenSeductionTotem extends Item
{

    public ItemBrokenSeductionTotem(int id)
    {
        super(id);
        this.setUnlocalizedName(TIName.ITEM_BROKEN_SEDUCTION_TOTEM);
        this.setMaxStackSize(16);
        this.setCreativeTab(TheInvoker.tab);
    }
}
