package snake.mcmods.theinvoker.renderer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.entities.EntitySoulStoneMonitor;
import snake.mcmods.theinvoker.utils.Utils;

public class RenderSoulStoneMonitor extends Render
{
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float pariticalTimer)
	{
		if (!(entity instanceof EntitySoulStoneMonitor))
			return;
		EntitySoulStoneMonitor essm = (EntitySoulStoneMonitor)entity;
		String label = essm.getLabel();
		EntityLivingBase player = RenderManager.instance.livingPlayer;
		if (player == null)
			return;
		double distance = x * x + y * y + z * z;
		if (distance > 100)
			return;
		FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
		float f0 = 1.6F;
		float f1 = 0.016666668F * f0;
		GL11.glPushMatrix();

		ForgeDirection dir = essm.getDirection();
		float xOff = 0, yOff = 0, zOff = 0;
		if (dir != null)
		{
			switch (dir)
			{
				case DOWN:
					yOff = 0.3F;
					break;
				case EAST:
					xOff = -0.4F;
					break;
				case NORTH:
					zOff = 0.4F;
					break;
				case SOUTH:
					zOff = -0.4F;
					break;
				case UP:
					yOff = -0.3F;
					break;
				case WEST:
					xOff = 0.4F;
					break;
				default:
					break;

			}
		}
		GL11.glTranslatef((float)x + 0.5F + xOff, (float)y + 0.5F + yOff, (float)z + 0.5F + zOff);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f1, -f1, f1);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.instance;
		byte b0 = 0;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tessellator.startDrawingQuads();
		int j = fontrenderer.getStringWidth(label) / 2;
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tessellator.addVertex(-j - 1, -1 + b0, 0.0D);
		tessellator.addVertex(-j - 1, 8 + b0, 0.0D);
		tessellator.addVertex(j + 1, 8 + b0, 0.0D);
		tessellator.addVertex(j + 1, -1 + b0, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		fontrenderer.drawString(label, -fontrenderer.getStringWidth(label) / 2, b0, 553648127);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		fontrenderer.drawString(label, -fontrenderer.getStringWidth(label) / 2, b0, -1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation func_110775_a(Entity entity)
	{
		return null;
	}

}
