package snake.mcmods.theinvoker.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.models.ModelSeductionTotem;
import snake.mcmods.theinvoker.tileentities.TileTIBase;

public class RenderSeductionTotem extends RenderTileBase
{
    public RenderSeductionTotem()
    {
        model = new ModelSeductionTotem();
    }
    private ModelSeductionTotem model;
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
    {
        TileTIBase tb = (TileTIBase)tileentity;
        if(tb.isGhostBlock())
            return;
        super.renderTileEntityAt(tileentity, x, y, z, f);
        String textureFileName = ItemTotem.NAMES[tileentity.getBlockMetadata()];
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.MODEL_BASE_PATH + textureFileName + ".png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glRotatef(angle, 0F, 1F, 0F);
        model.render();
        GL11.glPopMatrix();
    }
}
