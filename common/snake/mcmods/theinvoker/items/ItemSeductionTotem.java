package snake.mcmods.theinvoker.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.lib.constants.TIBlockID;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemMisc;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;

public class ItemSeductionTotem extends ItemTIBase
{

    public ItemSeductionTotem(int id)
    {
        super(id);
        this.setMaxStackSize(1);
        this.setCreativeTab(TheInvoker.tab);
        this.setUnlocalizedName(TIName.ITEM_SEDUCTION_TOTEM);
        this.setMaxDamage(SeductionTotemMisc.MAX_AGE_DMG_VALUE);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x,
            int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        ForgeDirection fd = ForgeDirection.getOrientation(side);
        int vx = x + fd.offsetX;
        int vy = side == 0 ? y + fd.offsetY - 1 : y + fd.offsetY;
        int vz = z + fd.offsetZ;
        if (world.canPlaceEntityOnSide(TIBlockID.SEDUCTION_TOTEM, vx, vy, vz, true, side, entityPlayer,
                itemStack))
        {
            int metadata = itemStack.getItemDamage();
            boolean isSet = world.setBlock(vx, vy, vz, TIBlocks.seductionTotem.blockID, SeductionTotemMisc.NORMAL_METADATA, 2);
            if (isSet)
            {
                world.setBlock(vx, vy + 1, vz, TIBlocks.seductionTotem.blockID, 0, 2);

                TileSeductionTotem t = (TileSeductionTotem)world.getBlockTileEntity(vx, vy, vz);
                if(t!=null)
                {
                    TIBlocks.totem.onBlockPlacedBy(world, vx, vy, vz, entityPlayer, itemStack);
                    t.setAge(SeductionTotemMisc.getAgeFromDamageValue(metadata));
                }
                
                TIBlocks.totem.onBlockPlacedBy(world, vx, vy + 1, vz, entityPlayer, itemStack);

                itemStack.stackSize--;
                return true;
            }
        }
        return false;
    }
}
