package snake.mcmods.theinvoker.tileentities;


public abstract class TileMultiBlockBase extends TileTIBase
{
	public TileMultiBlockBase()
	{
		setDirection(0);
		originCoords = new int[3];
	}

	protected boolean isFormless;

	protected int[] originCoords;
	
	protected boolean isTransforming;

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
	
	public Boolean getIsTransforming()
	{
		return isTransforming;
	}

	public void setBeginTransfer()
	{
		isTransforming=true;
	}

	public void setEndTransfer()
	{
		isTransforming=false;
	}
}
