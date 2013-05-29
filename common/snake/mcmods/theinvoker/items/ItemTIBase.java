package snake.mcmods.theinvoker.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTIBase extends Item
{

    public ItemTIBase(int par1)
    {
        super(par1);
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        itemIcon = par1IconRegister.registerIcon(TIGlobal.MOD_ID+":"+getUnlocalizedName().substring(5));
    }
}
