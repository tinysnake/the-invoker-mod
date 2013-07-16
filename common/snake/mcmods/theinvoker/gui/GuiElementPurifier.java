package snake.mcmods.theinvoker.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.energy.EnergyCenter;
import snake.mcmods.theinvoker.energy.EnergyContainer;
import snake.mcmods.theinvoker.energy.EnergyForce;
import snake.mcmods.theinvoker.inventory.ContainerElementPurifier;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.RuneType;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.logic.elempurifier.ElementPurifierMisc;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;

public class GuiElementPurifier extends GuiContainer
{
	private static final int INDICATOR_SIZE = 22;
	private static final int INDICATOR_X = 78;
	private static final int INDICATOR_Y = 4;
	
	private static final int ENERGY_IND_SIZE = 6;
	private static final int ENERGY_IND_OFFSET_X = 4;
	private static final int ENERGY_IND_DRAW_X = 122;
	private static final int ENERGY_IND_DRAW_Y = 45;
	private static final int ENERGY_IND_TEXTURE_X = 0;
	private static final int ENERGY_IND_TEXTURE_Y = 148;
	
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
		this.mc.func_110434_K().func_110577_a(Textures.GUI_ELEMENT_PURIFIER);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		
		drawElementEnergyIndicator(tep.getEnergyContainer(ElementPurifierMisc.getEnergyID(TIItems.soulRune.itemID, RuneType.NEUTRAL.ordinal())), 0);
		drawElementEnergyIndicator(tep.getEnergyContainer(ElementPurifierMisc.getEnergyID(TIItems.soulRune.itemID, RuneType.ICE.ordinal())), 1);
		drawElementEnergyIndicator(tep.getEnergyContainer(ElementPurifierMisc.getEnergyID(TIItems.soulRune.itemID, RuneType.FIRE.ordinal())), 2);
		drawElementEnergyIndicator(tep.getEnergyContainer(ElementPurifierMisc.getEnergyID(TIItems.soulRune.itemID, RuneType.WIND.ordinal())), 3);
		drawElementEnergyIndicator(tep.getEnergyContainer(ElementPurifierMisc.getEnergyID(TIItems.soulRune.itemID, RuneType.DARKNESS.ordinal())), 4);

		if (tep.getIsProcessing())
		{
			int iy = (int)Math.floor(tep.getProcessProgress() / 0.125F) * INDICATOR_SIZE;
			int ix = 178;
			this.drawTexturedModalRect(xStart + INDICATOR_X, yStart + INDICATOR_Y, ix, iy, INDICATOR_SIZE, INDICATOR_SIZE);
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
		for(int i=0;i<5;i++)
		{
			int indX = xStart+ENERGY_IND_DRAW_X+ i*(ENERGY_IND_SIZE+ENERGY_IND_OFFSET_X);
			int indXMax = indX+ENERGY_IND_SIZE;
			int indY = yStart + ENERGY_IND_DRAW_Y;
			int indYMax = indY+ENERGY_IND_SIZE;
			if(mouseX>=indX&&mouseX<=indXMax&&mouseY>=indY&&mouseY<=indYMax)
			{
				int energyID = ElementPurifierMisc.getEnergyID(TIItems.soulRune.itemID, i);
				
				List arr = new ArrayList();
				EnergyContainer con = tep.getEnergyContainer(energyID);
				arr.add(EnumChatFormatting.WHITE + EnergyCenter.INSTANCE.getEnergyForce(energyID).getName()+ ": " + 
						String.valueOf(con.getEnergyLevel()) + "/" + String.valueOf(con.getEnergyCapacity()));
				drawHoveringText(arr, mouseX, mouseY, fontRenderer);
			}
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	private void drawElementEnergyIndicator(EnergyContainer energyContainer, int i)
	{
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		float percentage = (float)energyContainer.getEnergyLevel() / energyContainer.getEnergyCapacity();
		int indHeight = (int)(ENERGY_IND_SIZE * percentage);
		int drawX = ENERGY_IND_DRAW_X+ i*(ENERGY_IND_SIZE+ENERGY_IND_OFFSET_X);
		this.drawTexturedModalRect(xStart + drawX, yStart + ENERGY_IND_DRAW_Y+ENERGY_IND_SIZE-indHeight, 
				ENERGY_IND_TEXTURE_X+i*ENERGY_IND_SIZE, ENERGY_IND_TEXTURE_Y, ENERGY_IND_SIZE, indHeight);
	}
}
