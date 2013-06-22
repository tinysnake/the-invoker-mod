package snake.mcmods.theinvoker.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.models.ModelElementPurifier;

public class RenderElementPurifier extends RenderTileBase
{

	public RenderElementPurifier()
	{
		model = new ModelElementPurifier();
	}
	
	private ModelElementPurifier model;

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
		GL11.glScalef(1F, -1F, -1F);
		GL11.glRotatef(angle, 0F, 1F, 0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(Textures.MODEL_ELEMENT_PURIFIER);
		model.render(0);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
	}

}
