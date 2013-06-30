package snake.mcmods.theinvoker.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import scala.Console;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.models.ModelElementPurifier;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;

public class RenderElementPurifier extends RenderTileBase
{

	public RenderElementPurifier()
	{
		model = new ModelElementPurifier();
		ghostItemRenderer = new RenderItem(){
			@Override
            public boolean shouldBob() {

                return false;
            };
		};
		ghostItemRenderer.setRenderManager(RenderManager.instance);
	}

	private ModelElementPurifier model;
	private final RenderItem ghostItemRenderer;

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		if (tileentity instanceof TileElementPurifier)
		{
			TileElementPurifier tep = (TileElementPurifier)tileentity;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			GL11.glScalef(1F, -1F, -1F);
			GL11.glRotatef(angle, 0F, 1F, 0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(Textures.MODEL_ELEMENT_PURIFIER);
			if(tep.hasWork)
			{
				float shockX = -.0125F + tep.worldObj.rand.nextFloat() * .025F;
				float shockZ = -.0125F + tep.worldObj.rand.nextFloat() * .025F;
				GL11.glPushMatrix();
				GL11.glTranslatef(shockX, 0F, shockZ);
				model.renderPlate();
				GL11.glPopMatrix();
				model.renderWithoutPlate(tep.spinValue);
			}
			else
			{
				model.render(tep.spinValue);
			}
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();

			if (tep.getProcessItem() != null)
			{

				GL11.glPushMatrix();
				EntityItem ghostItem = new EntityItem(tep.worldObj);
				ghostItem.hoverStart = 0.0F;
				ghostItem.setEntityItemStack(tep.getProcessItem());
				//ghostItem.setEntityItemStack(new ItemStack(TIItems.totem, 1, 1));
				//ghostItem.setEntityItemStack(new ItemStack(Block.blockDiamond));
				translateGhostItem(ghostItem.getEntityItem(), x, y, z);
				float timeFactor = ((float)(System.currentTimeMillis()&0x3FFFL)/0x3FFFL);
				float rotationAngle = 720.0F * timeFactor;
				float floatHeight = (float)Math.sin(tep.floatValue);
				float scaleFactor = getGhostItemScaleFactor(ghostItem.getEntityItem());
				GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
				GL11.glRotatef(rotationAngle, 0, .8F, 0);
				GL11.glTranslatef(0F, 0.06F*floatHeight, 0F);
				ghostItemRenderer.doRenderItem(ghostItem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix();
			}
		}
	}

	private void translateGhostItem(ItemStack item, double x, double y, double z)
	{
		if (item != null)
		{
			if (item.getItem() instanceof ItemBlock)
			{
				GL11.glTranslatef((float)x+.5F, (float)y+ .96F, (float)z+.5F);
			}
			else
			{
				GL11.glTranslatef((float)x+.5F, (float)y+ .94F, (float)z+.5F);
			}
		}
	}

	private float getGhostItemScaleFactor(ItemStack item)
	{
		if (item != null)
		{
			if (item.getItem() instanceof ItemBlock)
			{
				return .7F;
			}
			else
			{
				return .6F;
			}
		}
		return 1;
	}

}
