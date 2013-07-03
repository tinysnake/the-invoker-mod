package snake.mcmods.theinvoker.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.inventory.ContainerElementPurifier;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;

public class GuiElementPurifier extends GuiContainer
{
	private static final int INDICATOR_SIZE = 22;
	private static final int INDICATOR_X = 78;
	private static final int INDICATOR_Y = 4;
	
	public GuiElementPurifier(InventoryPlayer player, TileElementPurifier tep)
	{
		super(new ContainerElementPurifier(player, tep));
		this.tep = tep;
		xSize = 178;
		ySize = 142;
	}

	private TileElementPurifier tep;

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		String containerName = StatCollector.translateToLocal(tep.getInvName());
		fontRenderer.drawString(containerName, 8, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(Textures.GUI_ELEMENT_PURIFIER);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

		if (tep.getIsProcessing())
		{
			int iy = (int)Math.floor(tep.getProcessProgress() / 0.125F) * INDICATOR_SIZE;
			int ix = 178;
			this.drawTexturedModalRect(xStart + INDICATOR_X, yStart + INDICATOR_Y, ix, iy, INDICATOR_SIZE, INDICATOR_SIZE);
		}
	}

}
