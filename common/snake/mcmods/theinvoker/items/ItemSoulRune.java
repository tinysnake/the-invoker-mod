package snake.mcmods.theinvoker.items;

import java.util.List;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemSoulRune extends Item
{
    public static final String[] NAMES =
    {
            TIName.ITEM_SOUL_RUNE_NEUTRAL,
            TIName.ITEM_SOUL_RUNE_ICE,
            TIName.ITEM_SOUL_RUNE_FIRE,
            TIName.ITEM_SOUL_RUNE_WIND,
            TIName.ITEM_SOUL_RUNE_DARKNESS
    };

    public ItemSoulRune(int id)
    {
        super(id);
        this.setHasSubtypes(true);
        this.setCreativeTab(TheInvoker.tab);
        this.setMaxDamage(0);
    }

    private Icon[] itemIcons;

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int dmg = itemStack.getItemDamage();
        if (dmg > -1 && dmg < NAMES.length)
            return "item." + NAMES[dmg];
        return "";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int metadata) {
        if (metadata > -1 && metadata < NAMES.length)
            return itemIcons[metadata];
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        itemIcons = new Icon[NAMES.length];
        for (int i = 0; i < itemIcons.length; i++)
        {
            itemIcons[i] = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + NAMES[i]);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int itemID, CreativeTabs tab, List list) {
        for(int i = 0; i < NAMES.length;i++)
        {
            list.add(new ItemStack(itemID, 1, i));
        }
    }
}
