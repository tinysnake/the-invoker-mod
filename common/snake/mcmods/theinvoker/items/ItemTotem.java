package snake.mcmods.theinvoker.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.TIBlockID;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTotem extends Item
{
    public static final String[] NAMES =
    { "", TIName.ITEM_TOTEM_SOUL, TIName.ITEM_TOTEM_SOUL_ATTRACTIVE, TIName.ITEM_TOTEM_RUNE_ICE, TIName.ITEM_TOTEM_RUNE_FIRE, TIName.ITEM_TOTEM_RUNE_WIND, TIName.ITEM_TOTEM_RUNE_DARKNESS, TIName.ITEM_TOTEM_MASSACRE };

    public ItemTotem(int id)
    {
        super(id);
        // _type = type;
        this.setHasSubtypes(true);
        this.setMaxStackSize(4);
        this.setCreativeTab(TheInvoker.tab);
        this.setMaxDamage(0);
    }

    private Icon[] itemIcons = new Icon[NAMES.length];

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int dmg = itemStack.getItemDamage();
        if(dmg>-1&&dmg<NAMES.length)
            return "item." + NAMES[dmg];
        return "";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int damageVal)
    {
        if(damageVal>-1&&damageVal<NAMES.length)
            return itemIcons[damageVal];
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        for (int i = 0; i < NAMES.length; i++)
        {
            itemIcons[i] = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + NAMES[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings(
    { "all" })
    public void getSubItems(int itemID, CreativeTabs tab, List list)
    {
        for (int i = 1; i < NAMES.length; i++)
        {
            list.add(new ItemStack(itemID, 1, i));
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x,
            int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        ForgeDirection fd = ForgeDirection.getOrientation(side);
        int vx = x + fd.offsetX;
        int vy = side == 0 ? y + fd.offsetY - 1 : y + fd.offsetY;
        int vz = z + fd.offsetZ;
        if (world.canPlaceEntityOnSide(TIBlockID.TOTEM, vx, vy, vz, true, side, entityPlayer,
                itemStack))
        {
            boolean isSet = world.setBlock(vx, vy, vz, TIBlocks.totem.blockID, itemStack.getItemDamage(), 4);
            if (isSet)
            {
                world.setBlock(vx, vy + 1, vz, TIBlocks.totem.blockID, TotemType.TYPE_GHOST.ordinal(), 4);

                TIBlocks.totem.onBlockPlacedBy(world, vx, vy, vz, entityPlayer, itemStack);
                TIBlocks.totem.onBlockPlacedBy(world, vx, vy + 1, vz, entityPlayer, itemStack);

                itemStack.stackSize--;
                return true;
            }
        }
        return false;
    }
}
