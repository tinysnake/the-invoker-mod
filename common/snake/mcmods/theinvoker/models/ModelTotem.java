package snake.mcmods.theinvoker.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTotem extends ModelBase
{
	// fields
	ModelRenderer head;
	ModelRenderer lefthandupper;
	ModelRenderer lefthand;
	ModelRenderer righthandupper;
	ModelRenderer righthand;
	ModelRenderer body;
	ModelRenderer foot;

	public ModelTotem()
	{
		textureWidth = 32;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 21);
		head.addBox(-3F, -6F, -3F, 6, 6, 6);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(32, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		lefthandupper = new ModelRenderer(this, 0, 47);
		lefthandupper.addBox(-9F, -2F, -2F, 9, 2, 2);
		lefthandupper.setRotationPoint(-2F, 4F, 1F);
		lefthandupper.setTextureSize(32, 64);
		lefthandupper.mirror = true;
		setRotation(lefthandupper, 0F, 0F, 0.0872665F);
		lefthand = new ModelRenderer(this, 0, 51);
		lefthand.addBox(-7F, -3F, -2F, 7, 3, 2);
		lefthand.setRotationPoint(-2F, 7F, 1F);
		lefthand.setTextureSize(32, 64);
		lefthand.mirror = true;
		setRotation(lefthand, 0F, 0F, 0.0872665F);
		righthandupper = new ModelRenderer(this, 0, 47);
		righthandupper.addBox(0F, -2F, -2F, 9, 2, 2);
		righthandupper.setRotationPoint(2F, 4F, 1F);
		righthandupper.setTextureSize(32, 64);
		righthandupper.mirror = true;
		setRotation(righthandupper, 0F, 0F, -0.0872665F);
		righthand = new ModelRenderer(this, 0, 51);
		righthand.addBox(0F, -3F, -2F, 7, 3, 2);
		righthand.setRotationPoint(2F, 7F, 1F);
		righthand.setTextureSize(32, 64);
		righthand.mirror = true;
		setRotation(righthand, 0F, 0F, -0.0872665F);
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-2F, -17F, -2F, 4, 17, 4);
		body.setRotationPoint(0F, 17F, 0F);
		body.setTextureSize(32, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		foot = new ModelRenderer(this, 0, 33);
		foot.addBox(-3.5F, -7F, -3.5F, 7, 7, 7);
		foot.setRotationPoint(0F, 24F, 0F);
		foot.setTextureSize(32, 64);
		foot.mirror = true;
		setRotation(foot, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		head.render(par7);
		lefthandupper.render(par7);
		lefthand.render(par7);
		righthandupper.render(par7);
		righthand.render(par7);
		body.render(par7);
		foot.render(par7);
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

	public void render()
	{
		render(null, 0F, 0F, -0.1F, 0F, 0F, 0.0625F);
		// head.render(0.0625F);
		// lefthandupper.render(0.0625F);
		// lefthand.render(0.0625F);
		// righthandupper.render(0.0625F);
		// righthand.render(0.0625F);
		// body.render(0.0625F);
		// foot.render(0.0625F);
	}
}
