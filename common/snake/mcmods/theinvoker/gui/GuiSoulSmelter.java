package snake.mcmods.theinvoker.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.liquids.LiquidStack;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.inventory.ContainerSoulSmelter;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;
import snake.mcmods.theinvoker.utils.Utils;

public class GuiSoulSmelter extends GuiContainer
{

	private static final int TANK_Y = 54;
	private static final int TANK_X = 130;
	private static final int TANK_HEIGHT = 48;
	private static final int FIRE_HEIGHT = 11;

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
		String containerName = StatCollector.translateToLocal(soulSmelter.getInvName());
		fontRenderer.drawString(containerName, 8, 5, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 50, 4210752);
		String energyLevel = String.valueOf(soulSmelter.getEnergyContainer().getEnergyLevel());
		fontRenderer.drawString(energyLevel, 32 + 8 - fontRenderer.getStringWidth(energyLevel) / 2, 40, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(Textures.GUI_SOUL_SMELTER);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

		// draw fire indicator
		// draw position: 53, 25
		// fire position: 178, 50, 11,10
		if (soulSmelter.getBoilTicksLeft() > 0)
		{
			int fireH = (int) (soulSmelter.getBoilProgress() * FIRE_HEIGHT);
			this.drawTexturedModalRect(xStart + 53, yStart + 25 + fireH, 178, 50 + fireH, 12, FIRE_HEIGHT - fireH);
		}

		// draw lava tank
		// 130, 54
		LiquidStack lq = soulSmelter.getLavaTank().getLiquid();
		if (lq != null)
		{

			Icon lavaIcon = lq.getRenderingIcon();
			String textureSheet = lq.getTextureSheet();
			int start = 0;
			int lavaH = Utils.getScaledLiquidAmount(lq, soulSmelter.getLavaTank().getCapacity(), TANK_HEIGHT);
			mc.renderEngine.bindTexture(textureSheet);

			while (lavaH > 0)
			{
				int h = 0;
				if (lavaH > 16)
				{
					h = 16;
					lavaH -= 16;
				}
				else
				{
					h = lavaH;
					lavaH = 0;
				}
				this.drawTexturedModelRectFromIcon(xStart + TANK_X, yStart + TANK_Y - h - start, lavaIcon, 16, h);
				start += h;
			}

			mc.renderEngine.bindTexture(Textures.GUI_SOUL_SMELTER);
			this.drawTexturedModalRect(xStart + TANK_X, yStart + TANK_Y - TANK_HEIGHT, 178, 0, 8, TANK_HEIGHT);
		}
	}

	@Override
	@SuppressWarnings({"all"})
	public void drawScreen(int mouseX, int mouseY, float pTicks)
	{
		super.drawScreen(mouseX, mouseY, pTicks);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		if (mouseX > xStart + 130 && mouseX < xStart + 148 && mouseY > yStart + 6 && mouseY < yStart + 54)
		{
			List arr = new ArrayList();
			String amount = soulSmelter.getLavaTank().getLiquid() != null ?
			        String.valueOf(soulSmelter.getLavaTank().getLiquid().amount) : "0";
			String capacity = String.valueOf(soulSmelter.getLavaTank().getCapacity());
			arr.add(EnumChatFormatting.WHITE + amount + "/" + capacity);
			drawHoveringText(arr, mouseX, mouseY, fontRenderer);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

}
