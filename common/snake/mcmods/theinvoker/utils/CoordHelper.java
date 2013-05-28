package snake.mcmods.theinvoker.utils;


public class CoordHelper
{
    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        double dx = x1-x2;
        double dy = y1-y2;
        double dz = z1-z2;
        return Math.sqrt(dx*dx+dy*dy+dz*dz);
    }
    
    public static double getBlockDistance(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        int dx = x1-x2;
        int dy = y1-y2;
        int dz = z1-z2;
        return Math.sqrt(dx*dx+dy*dy+dz*dz);
    }
}
