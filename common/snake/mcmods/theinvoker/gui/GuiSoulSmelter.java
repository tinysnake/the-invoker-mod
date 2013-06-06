package snake.mcmods.theinvoker.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.inventory.ContainerSoulSmelter;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;

public class GuiSoulSmelter extends GuiContainer
{

	public GuiSoulSmelter(InventoryPlayer player, TileSoulSmelter soulSmelter)
	{
		super(new ContainerSoulSmelter(player, soulSmelter));
		this.soulSmelter = soulSmelter;
        xSize = 178;
        ySize = 142;
	}
	
	private TileSoulSmelter soulSmelter;

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) 
    {
        String containerName = soulSmelter.getInvName();
        fontRenderer.drawString(containerName, 5, 5, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 50, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Textures.GUI_SOUL_SMELTER);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}

}
