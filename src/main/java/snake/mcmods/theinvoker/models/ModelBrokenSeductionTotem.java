package snake.mcmods.theinvoker.models;

import net.minecraft.client.model.ModelRenderer;

public class ModelBrokenSeductionTotem extends ModelSeductionTotem
{

	public ModelBrokenSeductionTotem()
	{
		textureWidth = 64;
		textureHeight = 32;

		leg = new ModelRenderer(this, 0, 0);
		leg.addBox(-1.5F, 0F, -1.5F, 3, 13, 3);
		leg.setRotationPoint(-1F, 12F, -1.7F);
		leg.setTextureSize(64, 32);
		setRotation(leg, -0.3717861F, 0.1858931F, 0F);

		body = new ModelRenderer(this, 12, 0);
		body.addBox(-2.5F, 0F, -2.5F, 5, 10, 5);
		body.setRotationPoint(-4F, 22F, -2F);
		body.setTextureSize(64, 32);
		setRotation(body, 0F, -1.375609F, -1.561502F);

		head = new ModelRenderer(this, 0, 16);
		head.addBox(-4F, -7F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 23F, 4F);
		head.setTextureSize(64, 32);
		setRotation(head, 0F, 1.152537F, 1.152537F);

		handleft = new ModelRenderer(this, 32, 0);
		handleft.addBox(-1F, 0F, -1F, 2, 9, 2);
		handleft.setRotationPoint(0F, 18F, -3F);
		handleft.setTextureSize(64, 32);
		setRotation(handleft, 0F, 0F, 0.8096675F);

		handright = new ModelRenderer(this, 32, 0);
		handright.addBox(-1F, 0F, -1F, 2, 9, 2);
		handright.setRotationPoint(2F, 17F, -2F);
		handright.setTextureSize(64, 32);
		handright.mirror = true;
		setRotation(handright, 0F, 0.9666439F, -0.6609531F);
	}
}
