package snake.mcmods.theinvoker.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.constants.TIBlockID;

public class ItemTotem extends ItemTIBase
{
    public enum TotemType{
        TYPE_GHOST(0),
        TYPE_SOUL_ATTRACTIVE(1);
        
        TotemType(int md)
        {
            _metadata = md;
        }
        
        private int _metadata;
        public int toMetadata()
        {
            return _metadata;
        }
    }

    public ItemTotem(int id,TotemType type, String name)
    {
        super(id);
        _type = type;
        this.setHasSubtypes(true);
        this.setMaxStackSize(4);
        this.setCreativeTab(TheInvoker.tab);
        this.setMaxDamage(0);
        this.setUnlocalizedName(name);
    }
    
    private TotemType _type;
    
    public TotemType getTotemType()
    {
        return _type;
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
            boolean isSet = world.setBlock(vx, vy, vz, TIBlocks.totem.blockID, this.getTotemType().toMetadata(), 2);
            if (isSet)
            {
                world.setBlock(vx, vy + 1, vz, TIBlocks.totem.blockID, TotemType.TYPE_GHOST.toMetadata(), 2);

                TIBlocks.totem.onBlockPlacedBy(world, vx, vy, vz, entityPlayer, itemStack);
                TIBlocks.totem.onBlockPlacedBy(world, vx, vy + 1, vz, entityPlayer, itemStack);

                itemStack.stackSize--;
                return true;
            }
        }
        return false;
    }
}
