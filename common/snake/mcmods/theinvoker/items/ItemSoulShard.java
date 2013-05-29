package snake.mcmods.theinvoker.items;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIName;

public class ItemSoulShard extends ItemTIBase
{
    public ItemSoulShard(int id)
    {
        super(id);
        this.setUnlocalizedName(TIName.ITEM_SOUL_SHARD);
        this.setCreativeTab(TheInvoker.tab);
    }
}
