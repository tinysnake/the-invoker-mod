package snake.mcmods.theinvoker.tileentities;


public abstract class TileMultiBlockBase extends TileTIBase
{
	public TileMultiBlockBase()
	{
		setDirection(0);
		originCoords = new int[3];
	}

	private boolean isFormless;

	private int[] originCoords;

	public boolean getIsFormless()
	{
		return isFormless;
	}

	public void setIsFormless(boolean isFormless)
	{
		this.isFormless = isFormless;
	}

	public int[] getOriginCoords()
	{
		return originCoords;
	}

	public void setOriginCoords(int x, int y, int z)
	{
		this.originCoords[0] = x;
		this.originCoords[1] = y;
		this.originCoords[2] = z;
	}
	
	public abstract void transferFrom(TileMultiBlockBase tmb);
}
