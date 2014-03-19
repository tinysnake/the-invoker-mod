package snake.mcmods.theinvoker.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelElementPurifier extends ModelBase
{
	// fields
	ModelRenderer foundation;
	ModelRenderer body;
	ModelRenderer plate;
	ModelRenderer rod1;
	ModelRenderer rod2;
	ModelRenderer rod3;
	ModelRenderer rod4;

	public ModelElementPurifier()
	{
		textureWidth = 64;
		textureHeight = 32;

		foundation = new ModelRenderer(this, 0, 0);
		foundation.addBox(-8F, -3F, -8F, 16, 5, 16);
		foundation.setRotationPoint(0F, 22F, 0F);
		foundation.setTextureSize(64, 32);
		setRotation(foundation, 0F, 0F, 0F);
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-6F, -5F, -6F, 12, 7, 12);
		body.setRotationPoint(0F, 17F, 0F);
		body.setTextureSize(64, 32);
		setRotation(body, 0F, 0F, 0F);
		plate = new ModelRenderer(this, 0, 21);
		plate.addBox(-1.5F, -5F, -1.5F, 3, 1, 3);
		plate.setRotationPoint(0F, 16F, 0F);
		plate.setTextureSize(64, 32);
		setRotation(plate, 0F, 0F, 0F);
		rod1 = new ModelRenderer(this, 12, 21);
		rod1.addBox(-1F, -6F, 3.5F, 2, 2, 2);
		rod1.setRotationPoint(0F, 16F, 0F);
		rod1.setTextureSize(64, 32);
		setRotation(rod1, 0F, 0F, 0F);
		rod2 = new ModelRenderer(this, 12, 21);
		rod2.addBox(-1F, -6F, -5.5F, 2, 2, 2);
		rod2.setRotationPoint(0F, 16F, 0F);
		rod2.setTextureSize(64, 32);
		setRotation(rod2, 0F, 0F, 0F);
		rod3 = new ModelRenderer(this, 12, 21);
		rod3.addBox(3.5F, -6F, -1F, 2, 2, 2);
		rod3.setRotationPoint(0F, 16F, 0F);
		rod3.setTextureSize(64, 32);
		setRotation(rod3, 0F, 0F, 0F);
		rod4 = new ModelRenderer(this, 12, 21);
		rod4.addBox(-5.5F, -6F, -1F, 2, 2, 2);
		rod4.setRotationPoint(0F, 16F, 0F);
		rod4.setTextureSize(64, 32);
		setRotation(rod4, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		foundation.render(par7);
		body.render(par7);
		plate.render(par7);
		rod1.render(par7);
		rod2.render(par7);
		rod3.render(par7);
		rod4.render(par7);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}

	public void render(float r)
	{
		this.render(null, 0F, 0F, -0.1F, 0F, 0F, 0.0625F);

		foundation.render(0.0625F);
		setRotation(rod4, 0F, r, 0F);
		setRotation(rod3, 0F, r, 0F);
		setRotation(rod2, 0F, r, 0F);
		setRotation(rod1, 0F, r, 0F);
	}
	
	public void renderPlate()
	{
		plate.render(0.0625F);
	}
	
	public void renderWithoutPlate(float r)
	{
		super.render(null, 0F, 0F, -0.1F, 0F, 0F, 0.0625F);
		setRotationAngles(0F, 0F, -0.1F, 0F, 0F, 0.0625F, null);
		foundation.render(0.0625F);
		body.render(0.0625F);
		//plate.render(0.0625F);
		rod1.render(0.0625F);
		rod2.render(0.0625F);
		rod3.render(0.0625F);
		rod4.render(0.0625F);
		
		foundation.render(0.0625F);
		setRotation(rod4, 0F, r, 0F);
		setRotation(rod3, 0F, r, 0F);
		setRotation(rod2, 0F, r, 0F);
		setRotation(rod1, 0F, r, 0F);
	}
}
