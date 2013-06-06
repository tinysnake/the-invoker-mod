package snake.mcmods.theinvoker.utils.others;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabTI extends CreativeTabs
{

	public CreativeTabTI(int par1, String par2Str)
	{
		super(par1, par2Str);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return Item.netherStar.itemID;
	}
}
