package snake.mcmods.theinvoker.logic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import scala.Console;
import snake.mcmods.theinvoker.constants.TotemMisc;
import snake.mcmods.theinvoker.constants.TotemMisc.TotemType;
import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import snake.mcmods.theinvoker.utils.CoordHelper;

public class TotemLogicHandler
{
    public static final TotemLogicHandler INSTANCE = new TotemLogicHandler();

    public TotemLogicHandler()
    {
        totems = new ArrayList<TileTotem>();
    }

    private List<TileTotem> totems;

    public void registerTotem(TileTotem tt)
    {
        if (tt != null && totems.indexOf(tt) < 0)
        {
            totems.add(tt);
        }
    }

    public void unregisterTotem(TileTotem tt)
    {
        if (tt != null)
        {
            totems.remove(tt);
        }
    }

    public boolean updateLogicWhileEntityLivingDrops(LivingDropsEvent event)
    {
        EntityLiving e = event.entityLiving;
        if (e == null)
            return false;
        for (TileTotem tt : totems)
        {
            if (tt.isInvalid())
                continue;
            double distance = tt.getDistanceFrom(e.posX, e.posY, e.posZ);
            if (distance / 16F <= distance && 
                    !totemHasBeenDefeated(tt) && 
                    getTotemInTheSameDimension(e,tt))
            {
                e.experienceValue=0;
                event.setCanceled(true);
                Console.println("that worked!");
                return true;
            }
        }
        return false;
    }
    
    public boolean getTotemInTheSameDimension(Entity e,TileTotem tt)
    {
        TileEntity te = e.worldObj.getBlockTileEntity(tt.xCoord, tt.yCoord, tt.zCoord);
        if(te instanceof TileTotem)
        {
            return tt.getBlockMetadata()==te.getBlockMetadata();
        }
        return false;
    }

    public boolean totemHasBeenDefeated(TileTotem tt)
    {
        int range = TotemMisc.getEffectiveRangeByMetadata(TotemType.getType(tt.getBlockMetadata()));
        for (TileTotem tt1 : totems)
        {
            if(tt1.equals(tt))
                continue;
            double distance = CoordHelper.getDistance(tt.xCoord, tt.yCoord, tt.zCoord, tt1.xCoord, tt1.yCoord, tt1.zCoord);
            if (distance / 16F <= range)
            {
                if (tt1.getBlockMetadata() >= tt.getBlockMetadata())
                {
                    if (tt1.getBlockMetadata() == tt.getBlockMetadata())
                    {
                        double thisSum = tt.xCoord * tt.xCoord + tt.yCoord * tt.yCoord + tt.zCoord * tt.zCoord;
                        double thatSum = tt1.xCoord * tt1.xCoord + tt1.yCoord * tt1.yCoord + tt1.zCoord * tt1.zCoord;
                        if (thisSum < thatSum)
                            return true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void debug()
    {
        for (TileTotem tt : totems)
        {
            Console.println(tt.xCoord + ", " + tt.yCoord + ", " + tt.zCoord);
        }
    }
}
