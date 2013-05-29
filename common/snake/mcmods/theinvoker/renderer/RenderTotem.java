package snake.mcmods.theinvoker.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.models.ModelTotem;
import snake.mcmods.theinvoker.tileentities.TileTotem;

public class RenderTotem extends TileEntitySpecialRenderer
{
    public RenderTotem()
    {
        model = new ModelTotem();
    }

    private ModelTotem model;

    @SuppressWarnings("incomplete-switch")
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
    {
        TileTotem tt = (TileTotem) tileentity;
        int metadata = tt.getBlockMetadata();
        if (metadata == TotemType.TYPE_GHOST.ordinal())
            return;
        String textureFileName = ItemTotem.NAMES[metadata];
        float angle = 0F;
        
        switch(tt.getDirection())
        {
            case NORTH:
                angle = 0F;
                break;
            case SOUTH:
                angle = 180F;
                break;
            case EAST:
                angle = 270F;
                break;
            case WEST:
                angle = 90F;
                break;
        }

        // GL11.glDisable(GL11.GL_CULL_FACE);
        // GL11.glDisable(GL11.GL_LIGHTING);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.MODEL_BASE_PATH + textureFileName + ".png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glRotatef(angle, 0F, 1F, 0F);
        model.render();
        GL11.glPopMatrix();
        // GL11.glEnable(GL11.GL_CULL_FACE);
        // GL11.glEnable(GL11.GL_LIGHTING);
    }
}
