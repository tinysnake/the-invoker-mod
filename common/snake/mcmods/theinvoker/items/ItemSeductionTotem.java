package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class ItemSeductionTotem extends ItemTIBase
{

    public ItemSeductionTotem(int id)
    {
        super(id);
        this.setMaxStackSize(1);
        this.setCreativeTab(TheInvoker.tab);
        this.setUnlocalizedName(TIName.ITEM_SEDUCTION_TOTEM);
    }

}
