package snake.mcmods.theinvoker.client.renderer;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.models.ModelTotem;
import snake.mcmods.theinvoker.tileentities.TileTIBase;
import snake.mcmods.theinvoker.utils.Utils;

public class RenderTotem extends RenderTileBase
{
	public RenderTotem()
	{
		model = new ModelTotem();
		resources = new ArrayList<ResourceLocation>(TotemType.values().length-1);
		for(int i=1;i<TotemType.values().length;i++)
		{
			resources.add(Utils.wrapResourcePath(Textures.MODEL_BASE_PATH + ItemTotem.NAMES[i] + ".png"));
		}
	}

	private ModelTotem model;
	private ArrayList<ResourceLocation> resources;

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		TileTIBase tb = (TileTIBase)tileentity;
		if (tb.getIsGhostBlock())
			return;
		super.renderTileEntityAt(tileentity, x, y, z, f);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
		GL11.glScalef(1F, -1F, -1F);
		GL11.glRotatef(angle, 0F, 1F, 0F);
		Minecraft.getMinecraft().renderEngine.func_110577_a(resources.get(tileentity.getBlockMetadata()-1));
		model.render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);

	}

}
