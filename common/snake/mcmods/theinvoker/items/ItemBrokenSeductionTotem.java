package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class ItemBrokenSeductionTotem extends ItemTIBase
{

    public ItemBrokenSeductionTotem(int id)
    {
        super(id);
        this.setUnlocalizedName(TIName.ITEM_BROKEN_SEDUCTION_TOTEM);
        this.setMaxStackSize(16);
        this.setCreativeTab(TheInvoker.tab);
    }
}
