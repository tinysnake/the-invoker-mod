package snake.mcmods.theinvoker.logic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;

import scala.Console;
import snake.mcmods.theinvoker.tileentities.TileTotem;

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
        if(tt!=null)
        {
            totems.add(tt);
        }
    }
    
    public void unregisterTotem(TileTotem tt)
    {
        if(tt!=null)
        {
            totems.remove(tt);
            Console.println("a Totem unregistered.");
        }
    }
    
    public void updateLogicWhileEntityLivingDied(EntityLiving e)
    {
        
    }
    
    public void debug()
    {
        for(TileTotem tt:totems)
        {
            Console.println(tt.xCoord+", "+tt.yCoord+", "+tt.zCoord);
        }
    }
}
