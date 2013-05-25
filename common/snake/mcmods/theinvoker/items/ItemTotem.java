package snake.mcmods.theinvoker.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.constants.TIBlockID;
import snake.mcmods.theinvoker.constants.TIName;
import snake.mcmods.theinvoker.utils.CoordHelper;

public class ItemTotem extends ItemTIBase
{
    public static final int TYPE_SOUL_ATTRACTIVE= 0;
    
    public ItemTotem(int id,int type)
    {
        super(id);
        _type = type;
        this.setMaxStackSize(4);
        this.setCreativeTab(TheInvoker.tab);
        setupName();
    }
    
    private void setupName() {
        switch(getType())
        {
            case TYPE_SOUL_ATTRACTIVE:
                this.setUnlocalizedName(TIName.ITEM_TOTEM_SOUL_ATTRACTIVE);
                break;
        }
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if(par7==0)
            return false;
        Vec3 v = CoordHelper.getPlaceXYZ(par4, par5, par6, par7);
        if(par3World.canPlaceEntityOnSide(TIBlockID.TOTEM, (int)v.xCoord, (int)v.yCoord, (int)v.zCoord, false, par7, par2EntityPlayer, par1ItemStack))
        {
            return par3World.setBlock(par4, par5+1, par6, TIBlocks.totem.blockID, getType(), 4);
        }
        return false;
    }

    private int _type;
    
    public int getType()
    {
        return _type;
    }
}
