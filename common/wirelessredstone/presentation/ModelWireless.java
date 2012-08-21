package wirelessredstone.presentation;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelWireless extends ModelBase {
	// fields
	ModelRenderer Angle1;
	ModelRenderer Angle2;
	ModelRenderer Shape1;

	public ModelWireless() {
		textureWidth = 32;
		textureHeight = 32;

		Angle1 = new ModelRenderer(this, 0, 0);
		Angle1.addBox(0F, 0F, 0F, 4, 6, 1);
		Angle1.setRotationPoint(0F, 20F, 0.7F);
		Angle1.setTextureSize(32, 32);
		Angle1.mirror = true;
		setRotation(Angle1, -0.7853982F, 0F, 0F);
		Angle2 = new ModelRenderer(this, 0, 0);
		Angle2.addBox(0F, 0F, 0F, 4, 7, 1);
		Angle2.setRotationPoint(0F, 20.7F, -0.7F);
		Angle2.setTextureSize(32, 32);
		Angle2.mirror = true;
		setRotation(Angle2, 0.7853982F, 0F, 0F);
		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 1, 7, 1);
		Shape1.setRotationPoint(1F, 16F, -0.05F);
		Shape1.setTextureSize(32, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Angle1.render(f5);
		Angle2.render(f5);
		Shape1.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5);
	}

}
