package snake.mcmods.theinvoker.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidStack;

public class Utils
{
	public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public static double getBlockDistance(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		int dx = x1 - x2;
		int dy = y1 - y2;
		int dz = z1 - z2;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public static double getDistanceBetweenTiles(TileEntity te1, TileEntity te2)
	{
		if (te1 != null && te2 != null)
			return te1.getDistanceFrom(te2.xCoord, te2.yCoord, te2.zCoord);
		return Integer.MAX_VALUE;
	}

	public static Entity getActualDamageSource(DamageSource ds)
	{
		Entity e = ds.getSourceOfDamage();
		if (!(e instanceof EntityLiving))
		{
			if (e instanceof EntityArrow)
			{
				return ((EntityArrow)e).shootingEntity;
			}
		}
		return e;
	}

	public static int getScaledLiquidAmount(LiquidStack ls, int maxAmount, int scaleFactor)
	{
		if (ls == null)
			return 0;
		return (int)(ls.amount * scaleFactor / (float)maxAmount);
	}

	public static int getPlaceDirection(EntityLiving entityLiving)
	{
		int direction = 2;
		int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0)
		{
			direction = ForgeDirection.NORTH.ordinal();
		}
		else if (facing == 1)
		{
			direction = ForgeDirection.EAST.ordinal();
		}
		else if (facing == 2)
		{
			direction = ForgeDirection.SOUTH.ordinal();
		}
		else if (facing == 3)
		{
			direction = ForgeDirection.WEST.ordinal();
		}
		return direction;
	}

	public static ForgeDirection getPlaceDirectionForge(EntityLiving entityLiving)
	{
		ForgeDirection direction = ForgeDirection.NORTH;
		int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0)
		{
			direction = ForgeDirection.NORTH;
		}
		else if (facing == 1)
		{
			direction = ForgeDirection.EAST;
		}
		else if (facing == 2)
		{
			direction = ForgeDirection.SOUTH;
		}
		else if (facing == 3)
		{
			direction = ForgeDirection.WEST;
		}
		return direction;
	}

	public static int getVerticalPlaceDirection(double px, double py, double pz, double x, double y, double z)
	{
		double tx = px - x;
		double tz = pz - z;
		if (tx * tx + tz * tz > 3)
		{
			return -1;
		}
		else
		{
			return py - y > 0 ? 2 : 1;
		}
	}
}
