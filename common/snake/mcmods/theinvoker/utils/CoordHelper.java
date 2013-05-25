package snake.mcmods.theinvoker.utils;

import net.minecraft.util.Vec3;

public class CoordHelper
{
    public static Vec3 getPlaceXYZ(int x, int y, int z, int side)
    {
        Vec3 v = Vec3.createVectorHelper(x, y, z);
        switch(side)
        {
            case 0:
                v.yCoord--;
                break;
            case 1:
                v.yCoord++;
                break;
            case 2:
                v.xCoord--;
                break;
            case 3:
                v.xCoord++;
                break;
            case 4:
                v.zCoord--;
                break;
            case 5:
                v.zCoord++;
                break;
        }
        return v;
    }
}
