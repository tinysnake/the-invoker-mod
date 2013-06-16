package snake.mcmods.theinvoker.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import snake.mcmods.theinvoker.tileentities.TileTIBase;

public class RenderTileBase extends TileEntitySpecialRenderer
{
	public RenderTileBase()
	{
		// TODO Auto-generated constructor stub
	}

	protected float angle;

	@SuppressWarnings("incomplete-switch")
	public void doRender(TileEntity tileentity, double x, double y, double z, float f)
	{
		TileTIBase tt = (TileTIBase)tileentity;
		if (tt.getIsGhostBlock())
			return;

		switch (tt.getDirection())
		{
			case NORTH:
				angle = 0F;
				break;
			case SOUTH:
				angle = 180F;
				break;
			case EAST:
				angle = 270F;
				break;
			case WEST:
				angle = 90F;
				break;
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		doRender(tileentity, x, y, z, f);
	}

}
