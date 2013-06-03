package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.EvilTouchMisc;

public class ItemEvilTouch extends ItemTIBase
{

    public ItemEvilTouch(int id)
    {
        super(id);
        this.setUnlocalizedName(TIName.ITEM_EVIL_TOUCH);
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setCreativeTab(TheInvoker.tab);
        this.setMaxDamage(EvilTouchMisc.MAX_USAGE_DAMGE_VALUE);
    }

}
