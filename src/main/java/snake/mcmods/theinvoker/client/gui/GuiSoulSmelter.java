package snake.mcmods.theinvoker.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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
	private static final int FIRE_HEIGHT = 10;
	private static final int FIRE_WIDTH = 9;

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
		fontRendererObj.drawString(containerName, 8, 5, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 50, 4210752);
		String energyLevel = String.valueOf(soulSmelter.getEnergyContainer().getEnergyLevel());
		fontRendererObj.drawString(energyLevel, 32 + 8 - fontRendererObj.getStringWidth(energyLevel) / 2, 40, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(Textures.GUI_SOUL_SMELTER);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

		// draw fire indicator
		// draw position: 53, 25
		// fire position: 178, 50, 11,10
		if (soulSmelter.getBoilTicksLeft() > 0)
		{
			int fireH = (int)(soulSmelter.getBoilProgress() * FIRE_HEIGHT);
			this.drawTexturedModalRect(xStart + 53, yStart + 25 + fireH, 178, 50 + fireH, FIRE_WIDTH, FIRE_HEIGHT - fireH);
		}

		// draw lava tank
		// 130, 54
		FluidStack lq = soulSmelter.getLavaTank().getFluid();
		if (lq != null && lq.amount > 0)
		{
			IIcon lavaIcon = FluidRegistry.LAVA.getFlowingIcon();

			int start = 0;
			int lavaH = Utils.getScaledLiquidAmount(lq, soulSmelter.getLavaTank().getCapacity(), TANK_HEIGHT);
			this.mc.renderEngine.bindTexture(Textures.VANILLA_BLOCK_TEXTURE_SHEET);

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
			
			this.mc.renderEngine.bindTexture(Textures.GUI_SOUL_SMELTER);
			this.drawTexturedModalRect(xStart + TANK_X, yStart + TANK_Y - TANK_HEIGHT, 178, 0, 8, TANK_HEIGHT);
		}
	}

	@Override
	@SuppressWarnings({ "all" })
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
			String amount = soulSmelter.getLavaTank().getFluid() != null ? String.valueOf(soulSmelter.getLavaTank().getFluid().amount) : "0";
			String capacity = String.valueOf(soulSmelter.getLavaTank().getCapacity());
			arr.add(EnumChatFormatting.WHITE + amount + "/" + capacity);
			drawHoveringText(arr, mouseX, mouseY, fontRendererObj);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

}
