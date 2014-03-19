package snake.mcmods.theinvoker.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.blocks.BlockElemPillarDummy;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemElemPillar extends ItemBlock
{

	public ItemElemPillar(int id)
	{
		super(id);
		setMaxDamage(0);
		setMaxStackSize(64);
		setCreativeTab(TheInvoker.tab);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int dmg)
	{
		return TIBlocks.elemPillarDummy.getIcon(0, dmg);
	}

	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return TIBlocks.elemPillarDummy.getUnlocalizedName() + "." + BlockElemPillarDummy.NAMES[itemStack.getItemDamage()];
	}
}
