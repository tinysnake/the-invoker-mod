package snake.mcmods.theinvoker.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import snake.mcmods.theinvoker.constants.Textures;
import snake.mcmods.theinvoker.models.ModelTotem;

public class RenderTotem extends TileEntitySpecialRenderer
{
    public RenderTotem()
    {
        model = new ModelTotem();
    }
    
    private ModelTotem model;

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
    {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.MODEL_TOTEM);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d0 + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        model.render();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LIGHTING);
    }
}
