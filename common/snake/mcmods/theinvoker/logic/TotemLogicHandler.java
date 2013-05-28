package snake.mcmods.theinvoker.logic;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import scala.Console;
import snake.mcmods.theinvoker.constants.TotemMisc;
import snake.mcmods.theinvoker.constants.TotemMisc.TotemType;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import snake.mcmods.theinvoker.utils.CoordHelper;

public class TotemLogicHandler
{
    public static final TotemLogicHandler INSTANCE = new TotemLogicHandler();

    public TotemLogicHandler()
    {
        totems = new ArrayList<TileTotem>();
        entitiesToRemove = new ArrayList<Entry<Entity, Integer>>();
    }

    private List<TileTotem> totems;
    private List<Entry<Entity, Integer>> entitiesToRemove;

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
        TileTotem tt = getMostPowerfulTotem(e);
        if(tt!=null)
        {
            event.setCanceled(true);
            e.experienceValue = 0;
            Random rand = e.worldObj.rand;
            int dropMax = rand.nextInt(2)+1;
            for(int i=0;i<dropMax;i++)
            {
                e.entityDropItem(new ItemStack(Item.seeds, 1, 0), 0);
            }
            entitiesToRemove.add(new AbstractMap.SimpleEntry<Entity, Integer>(e, 40));
            return true;
        }
        return false;
//        for (TileTotem tt : totems)
//        {
//            if (tt.isInvalid())
//                continue;
//            double distance = tt.getDistanceFrom(e.posX, e.posY, e.posZ);
//            if (distance / 16F <= tt.getEffectiveRange() && !totemHasBeenDefeated(tt)
//                    && getTotemInTheSameDimension(e, tt))
//            {
//                event.setCanceled(true);
//                e.experienceValue = 0;
//                Random rand = e.worldObj.rand;
//                int dropMax = rand.nextInt(2)+1;
//                for(int i=0;i<dropMax;i++)
//                {
//                    e.entityDropItem(new ItemStack(Item.seeds, 1, 0), 0);
//                }
//                entitiesToRemove.add(new AbstractMap.SimpleEntry<Entity, Integer>(e, 40));
//                return true;
//            }
//        }
//        return false;
    }

    private TileTotem getMostPowerfulTotem(EntityLiving e)
    {
        TileTotem ltt = null;
        for(TileTotem tt:totems)
        {
            if(tt.isEntityInRange(e))
            {
                if(ltt!=null&&tt.getBlockMetadata()>ltt.getBlockMetadata()&&getTotemInTheSameDimension(e, tt))
                {
                    ltt=tt;
                }
                else if(getTotemInTheSameDimension(e, tt))
                {
                    ltt=tt;
                }
            }
        }
        return ltt;
    }

    public boolean getTotemInTheSameDimension(Entity e, TileTotem tt)
    {
        TileEntity te = e.worldObj.getBlockTileEntity(tt.xCoord, tt.yCoord, tt.zCoord);
        if (te instanceof TileTotem)
        {
            return tt.getBlockMetadata() == te.getBlockMetadata();
        }
        return false;
    }

    public boolean totemHasBeenDefeated(TileTotem tt)
    {
        int range = TotemMisc.getEffectiveRangeByMetadata(TotemType.getType(tt.getBlockMetadata()));
        for (TileTotem tt1 : totems)
        {
            if (tt1.equals(tt))
                continue;
            double distance = CoordHelper.getDistance(tt.xCoord, tt.yCoord, tt.zCoord, tt1.xCoord,
                    tt1.yCoord, tt1.zCoord);
            if (distance / 16F <= range)
            {
                if (tt1.getBlockMetadata() >= tt.getBlockMetadata())
                {
                    if (tt1.getBlockMetadata() == tt.getBlockMetadata())
                    {
                        double thisSum = tt.xCoord * tt.xCoord + tt.yCoord * tt.yCoord + tt.zCoord
                                * tt.zCoord;
                        double thatSum = tt1.xCoord * tt1.xCoord + tt1.yCoord * tt1.yCoord
                                + tt1.zCoord * tt1.zCoord;
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

    public void update()
    {
        Iterator<Entry<Entity, Integer>> it = entitiesToRemove.iterator();
        while (it.hasNext())
        {
            Entry<Entity, Integer> entry = it.next();
            if (entry.getValue() > 0)
            {
                entry.setValue(entry.getValue() - 1);
            } else
            {
                Entity e = entry.getKey();
                e.worldObj.removeEntity(e);
                it.remove();
            }

        }
    }
}
