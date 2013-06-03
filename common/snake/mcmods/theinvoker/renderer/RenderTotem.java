package snake.mcmods.theinvoker.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.models.ModelTotem;
import snake.mcmods.theinvoker.tileentities.TileTIBase;

public class RenderTotem extends RenderTileBase
{
    public RenderTotem()
    {
        model = new ModelTotem();
    }

    private ModelTotem model;

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
    {
        TileTIBase tb = (TileTIBase) tileentity;
        if (tb.getIsGhostBlock())
            return;
        super.renderTileEntityAt(tileentity, x, y, z, f);

        Tessellator t = Tessellator.instance;
        float brightness = tb.worldObj.getLightBrightness(tb.xCoord, tb.yCoord, tb.zCoord);
        int l = tb.worldObj.getLightBrightnessForSkyBlocks(tb.xCoord, tb.yCoord, tb.zCoord, 0);
        t.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l%65535, l/65535);
        
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y+1.5F, (float) z + 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        //GL11.glRotatef(angle, 0F, 1F, 0F);
        String textureFileName = ItemTotem.NAMES[tileentity.getBlockMetadata()];
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.MODEL_BASE_PATH + textureFileName + ".png");
        model.render();
        GL11.glPopMatrix();
        
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

}
