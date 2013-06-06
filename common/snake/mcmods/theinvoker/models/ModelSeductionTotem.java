package snake.mcmods.theinvoker.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSeductionTotem extends ModelBase
{
	// fields
	protected ModelRenderer leg;
	protected ModelRenderer body;
	protected ModelRenderer head;
	protected ModelRenderer handleft;
	protected ModelRenderer handright;

	public ModelSeductionTotem()
	{
		textureWidth = 64;
		textureHeight = 32;

		leg = new ModelRenderer(this, 0, 0);
		leg.addBox(-1.5F, 0F, -1.5F, 3, 13, 3);
		leg.setRotationPoint(0F, 11F, 0F);
		leg.setTextureSize(64, 32);
		setRotation(leg, 0F, 0F, 0F);

		body = new ModelRenderer(this, 12, 0);
		body.addBox(-2.5F, 0F, -2.5F, 5, 10, 5);
		body.setRotationPoint(0F, 1F, 0F);
		body.setTextureSize(64, 32);
		setRotation(body, 0F, 0F, 0F);

		head = new ModelRenderer(this, 0, 16);
		head.addBox(-4F, -7F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(64, 32);
		setRotation(head, 0F, 0F, 0F);

		handleft = new ModelRenderer(this, 32, 0);
		handleft.addBox(-1F, 0F, -1F, 2, 9, 2);
		handleft.setRotationPoint(-2F, 1F, 0F);
		handleft.setTextureSize(64, 32);
		setRotation(handleft, 0F, 0F, 0.6981317F);

		handright = new ModelRenderer(this, 32, 0);
		handright.addBox(-1F, 0F, -1F, 2, 9, 2);
		handright.setRotationPoint(2F, 1F, 0F);
		handright.setTextureSize(64, 32);
		handright.mirror = true;
		setRotation(handright, 0F, 0F, -0.6981317F);
	}

	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		leg.render(par7);
		body.render(par7);
		head.render(par7);
		handleft.render(par7);
		handright.render(par7);
	}

	protected void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}

	public void render()
	{
		leg.render(0.0625F);
		body.render(0.0625F);
		head.render(0.0625F);
		handleft.render(0.0625F);
		handright.render(0.0625F);
	}
}
