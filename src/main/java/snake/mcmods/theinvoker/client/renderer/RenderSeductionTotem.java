package snake.mcmods.theinvoker.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.models.ModelBrokenSeductionTotem;
import snake.mcmods.theinvoker.models.ModelSeductionTotem;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;

public class RenderSeductionTotem extends RenderTileBase
{
	public RenderSeductionTotem()
	{
		normalModel = new ModelSeductionTotem();
	}

	private ModelSeductionTotem normalModel;
	private ModelBrokenSeductionTotem brokenModel;

	private ModelSeductionTotem getNormalModel()
	{
		if (normalModel == null)
			normalModel = new ModelSeductionTotem();
		return normalModel;
	}

	private ModelBrokenSeductionTotem getBrokenModel()
	{
		if (brokenModel == null)
			brokenModel = new ModelBrokenSeductionTotem();
		return brokenModel;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		TileSeductionTotem tst = (TileSeductionTotem)tileentity;
		if (tst.getIsGhostBlock())
			return;

		super.renderTileEntityAt(tileentity, x, y, z, f);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		ResourceLocation textureResource;
		ModelSeductionTotem model;
		if (tst.getIsBroken())
		{
			textureResource = Textures.MODEL_BROKEN_SEDUCTION_TOTEM;
			model = getBrokenModel();
			normalModel = null;
		}
		else
		{
			textureResource = Textures.MODEL_SEDUCTION_TOTEM;
			model = getNormalModel();
			brokenModel = null;
		}
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
		GL11.glRotatef(angle, 0F, 1F, 0F);
		GL11.glScalef(1F, -1F, -1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
		model.render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
