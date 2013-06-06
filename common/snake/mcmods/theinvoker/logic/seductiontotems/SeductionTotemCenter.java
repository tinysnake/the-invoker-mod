package snake.mcmods.theinvoker.logic.seductiontotems;

import java.util.ArrayList;

import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;

public class SeductionTotemCenter
{
	public static final SeductionTotemCenter INSTANCE = new SeductionTotemCenter();

	public SeductionTotemCenter()
	{
		_seductionTotems = new ArrayList<TileSeductionTotem>();
	}

	private ArrayList<TileSeductionTotem> _seductionTotems;

	public ArrayList<TileSeductionTotem> getSeductionTotems()
	{
		return _seductionTotems;
	}

	public void registerSeductionTotem(TileSeductionTotem tst)
	{
		if (tst != null && _seductionTotems.indexOf(tst) < 0)
			_seductionTotems.add(tst);
	}

	public void unregisterSeductionTotem(TileSeductionTotem tst)
	{
		if (tst != null && _seductionTotems.indexOf(tst) >= 0)
			_seductionTotems.remove(tst);
	}

	public TileSeductionTotem getNearestSeductionTotem(double x, double y, double z)
	{
		double distance = Double.MAX_VALUE;
		TileSeductionTotem st = null;
		for (TileSeductionTotem tst : getSeductionTotems())
		{
			double d = tst.getDistanceFrom(x, y, z) / 16F;
			if (!tst.getIsBroken() && d > SeductionTotemMisc.LOSE_EFFECT_RANGE && d < SeductionTotemMisc.EFFECTIVE_RANGE && d < distance)
			{
				distance = d;
				st = tst;
			}
		}
		return st;
	}

	public boolean isThereAnySeductionTotemInRange(double x, double y, double z)
	{
		for (TileSeductionTotem tst : getSeductionTotems())
		{
			if (tst.isInRange(x, y, z) && !tst.getIsBroken())
			{
				return true;
			}
		}
		return false;
	}
}
