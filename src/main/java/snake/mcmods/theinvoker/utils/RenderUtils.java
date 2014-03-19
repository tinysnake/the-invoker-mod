package snake.mcmods.theinvoker.utils;

import net.minecraft.client.renderer.Tessellator;

public class RenderUtils
{
	public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float z)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)z, (double)((float)(u + 0) * f), (double)((float)(v + height) * f1));
		tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)z, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
		tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)z, (double)((float)(u + width) * f), (double)((float)(v + 0) * f1));
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)z, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
		tessellator.draw();
	}

	public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
	{
		drawTexturedModalRect(x, y, u, v, width, height, 0);
	}
}
