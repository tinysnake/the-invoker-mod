package snake.mcmods.theinvoker.gui;

import snake.mcmods.theinvoker.lib.constants.TIGuiID;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class TIGuiHanlder implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        switch(ID)
        {
            case TIGuiID.SOUL_SMELTER:
                if(te instanceof TileSoulSmelter)
                    return new GuiSoulSmelter(player, (TileSoulSmelter)te);
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
