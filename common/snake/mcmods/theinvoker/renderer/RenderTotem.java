package snake.mcmods.theinvoker.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.constants.Textures;
import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.models.ModelTotem;
import snake.mcmods.theinvoker.tileentities.TileTotem;

public class RenderTotem extends TileEntitySpecialRenderer
{
    public RenderTotem()
    {
        model = new ModelTotem();
    }

    private ModelTotem model;

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
    {
        TileTotem tt = (TileTotem) tileentity;
        int metadata = tt.getBlockMetadata();
        if (metadata == ItemTotem.TotemType.TYPE_GHOST.toMetadata())
            return;
        String textureFileName = ItemTotem.NAMES[metadata];
        ForgeDirection dir = ForgeDirection.getOrientation(tt.getDirection());
        float angle = 0F;
        if (dir == ForgeDirection.NORTH)
        {
            angle = 0F;
        }
        else if (dir == ForgeDirection.WEST)
        {
            angle = 90F;
        }
        else if (dir == ForgeDirection.SOUTH)
        {
            angle = 180F;
        }
        else if (dir == ForgeDirection.EAST)
        {
            angle = 270F;
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
