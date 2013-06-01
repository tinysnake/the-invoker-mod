package snake.mcmods.theinvoker.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.logic.SeductionTotemMisc;
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
        TileSeductionTotem tst = (TileSeductionTotem) tileentity;
        if (tst.worldObj.getBlockMetadata(tst.xCoord, tst.yCoord, tst.zCoord)==SeductionTotemMisc.GHOST_BLOCK_METADATA)
            return;
        
        super.renderTileEntityAt(tileentity, x, y, z, f);
        String textureFileName;
        ModelSeductionTotem model;
        if (tst.getIsBroken())
        {
            textureFileName = Textures.MODEL_BROKEN_SEDUCTION_TOTEM;
            model = getBrokenModel();
            normalModel = null;
        }
        else
        {
            textureFileName = Textures.MODEL_SEDUCTION_TOTEM;
            model = getNormalModel();
            brokenModel = null;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(textureFileName);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glRotatef(angle, 0F, 1F, 0F);
        model.render();
        GL11.glPopMatrix();
    }
}
