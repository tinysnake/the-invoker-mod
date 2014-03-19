package snake.mcmods.theinvoker.logic.totems;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import snake.mcmods.theinvoker.utils.Utils;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TotemCenter implements ITickHandler
{
	public static final TotemCenter INSTANCE = new TotemCenter();

	public TotemCenter()
	{
		totems = new ArrayList<TileTotem>();
		entitiesToRemove = new ArrayList<Entry<Entity, Integer>>();
	}

	private List<TileTotem> totems;
	private List<Entry<Entity, Integer>> entitiesToRemove;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		update();
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{

	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel()
	{
		return TIGlobal.MOD_ID + ":"+this.getClass().getSimpleName();
	}

	public void registerTotem(TileTotem tt)
	{
		if (tt != null && !tt.worldObj.isRemote && tt.getBlockMetadata() > 0 && totems.indexOf(tt) < 0)
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
		EntityLivingBase e = event.entityLiving;
		if (e == null || e.isChild())
			return false;
		TileTotem tt = getMostPowerfulTotemNearBy(e);
		if (tt != null && tt.getOwnerName() != null)
		{
			event.setCanceled(true);
			TotemMisc.dropItems(e, tt, tt.getOwnerName().equals(Utils.getActualDamageSource(event.source).getEntityName()));
			entitiesToRemove.add(new AbstractMap.SimpleEntry<Entity, Integer>(e, 25));
			return true;
		}
		return false;
	}

	private TileTotem getMostPowerfulTotemNearBy(Entity e)
	{
		TileTotem ltt = null;
		for (TileTotem tt : totems)
		{
			if (tt.isEntityInRange(e))
			{
				if (ltt != null && tt.getBlockMetadata() > ltt.getBlockMetadata() && getTotemInTheSameDimension(e, tt))
				{
					ltt = tt;
				}
				else if (getTotemInTheSameDimension(e, tt))
				{
					ltt = tt;
				}
			}
		}
		return ltt;
	}

	public boolean getTotemInTheSameDimension(Entity e, TileTotem tt)
	{
		return e.worldObj.provider.dimensionId == tt.worldObj.provider.dimensionId;
	}

	public boolean totemHasBeenDefeated(TileTotem tt)
	{
		int range = TotemMisc.getEffectiveRangeByMetadata(TotemType.getType(tt.getBlockMetadata()));
		for (TileTotem tt1 : totems)
		{
			if (tt1.equals(tt))
				continue;
			double distance = Utils.getDistance(tt.xCoord, tt.yCoord, tt.zCoord, tt1.xCoord, tt1.yCoord, tt1.zCoord);
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

	public void update()
	{
		Iterator<Entry<Entity, Integer>> it = entitiesToRemove.iterator();
		while (it.hasNext())
		{
			Entry<Entity, Integer> entry = it.next();
			if (entry.getValue() > 0)
			{
				entry.setValue(entry.getValue() - 1);
			}
			else
			{
				Entity e = entry.getKey();
				e.worldObj.removeEntity(e);
				it.remove();
			}
		}
	}
}
