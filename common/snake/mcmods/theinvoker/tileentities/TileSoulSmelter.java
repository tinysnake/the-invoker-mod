package snake.mcmods.theinvoker.tileentities;

import net.minecraftforge.common.ForgeDirection;

public class TileSoulSmelter extends TileTIBase
{

    public TileSoulSmelter()
    {
        setDirection(ForgeDirection.SOUTH.ordinal());
    }
    
    public boolean getIsActive()
    {
        return false;
    }

    
}
