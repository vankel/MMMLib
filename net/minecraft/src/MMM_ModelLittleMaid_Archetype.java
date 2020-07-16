package net.minecraft.src;

import java.util.Random;

import org.lwjgl.opengl.GL11;

/**
 * ���^���f���݊��̃x�[�X���f���B
 * �֐߃����N���Ă��Ȃ�
 */
public class MMM_ModelLittleMaid_Archetype extends MMM_ModelLittleMaid {

	// fields
	public MMM_ModelRenderer bipedHeadwear;
	public MMM_ModelRenderer ChignonR;
	public MMM_ModelRenderer ChignonL;
	public MMM_ModelRenderer ChignonB;
	public MMM_ModelRenderer Tail;
	public MMM_ModelRenderer SideTailR;
	public MMM_ModelRenderer SideTailL;

	public MMM_ModelLittleMaid_Archetype() {
		super();
	}

	public MMM_ModelLittleMaid_Archetype(float f) {
		super(f);
	}

	public MMM_ModelLittleMaid_Archetype(float f, float f1) {
		super(f, f1);
	}

	@Override
	public void initModel(float psize, float pyoffset) {
		heldItemLeft = 0;
		heldItemRight = 0;
		isSneak = false;
		isWait = false;
		aimedBow = false;

		pyoffset += 8F;

		// �����ʒu
		Arms = new MMM_ModelRenderer[1];
		Arms[0] = new MMM_ModelRenderer(this, 0, 0);
		Arms[0].setRotationPointMM(-1F, 5F, -1F);
		HeadMount.setRotationPointMM(0F, -4F, 0F);
		HeadTop.setRotationPointMM(0F, -8F, 0F);

		bipedHead = new MMM_ModelRenderer(this, 0, 0);
		bipedHead.addBoxMM(-4F, -8F, -4F, 8, 8, 8, psize);
		bipedHead.setRotationPointMM(0F, 0F, 0F);
		bipedHead.addChildMM(HeadMount);
		bipedHead.addChildMM(HeadTop);

		bipedHeadwear = new MMM_ModelRenderer(this, 24, 0);
		bipedHeadwear.addBoxMM(-4F, 0F, 1F, 8, 4, 3, psize);
		bipedHeadwear.setRotationPointMM(0F, 0F, 0F);
		bipedHead.addChildMM(bipedHeadwear);

		bipedBody = new MMM_ModelRenderer(this, 32, 8);
		bipedBody.addBoxMM(-3F, 0F, -2F, 6, 7, 4, psize);
		bipedBody.setRotationPointMM(0F, 0F, 0F);

		bipedRightArm = new MMM_ModelRenderer(this, 48, 0);
		bipedRightArm.addBoxMM(-2.0F, -1F, -1F, 2, 8, 2, psize);
		bipedRightArm.setRotationPointMM(-3.0F, 1.5F, 0F);
		bipedRightArm.addChildMM(Arms[0]);

		bipedLeftArm = new MMM_ModelRenderer(this, 56, 0);
		bipedLeftArm.addBoxMM(0.0F, -1F, -1F, 2, 8, 2, psize);
		bipedLeftArm.setRotationPointMM(3.0F, 1.5F, 0F);

		bipedRightLeg = new MMM_ModelRenderer(this, 32, 19);
		bipedRightLeg.addBoxMM(-2F, 0F, -2F, 3, 9, 4, psize);
		bipedRightLeg.setRotationPointMM(-1F, 7F, 0F);

		bipedLeftLeg = new MMM_ModelRenderer(this, 32, 19);
		bipedLeftLeg.setMirror(true);
		bipedLeftLeg.addBoxMM(-1F, 0F, -2F, 3, 9, 4, psize);
		bipedLeftLeg.setRotationPointMM(1F, 7F, 0F);

		Skirt = new MMM_ModelRenderer(this, 0, 16);
		Skirt.addBoxMM(-4F, -2F, -4F, 8, 8, 8, psize);
		Skirt.setRotationPointMM(0F, 7F, 0F);

		ChignonR = new MMM_ModelRenderer(this, 24, 18);
		ChignonR.addBoxMM(-5F, -7F, 0.2F, 1, 3, 3, psize);
		ChignonR.setRotationPointMM(0F, 0F, 0F);
		bipedHead.addChildMM(ChignonR);

		ChignonL = new MMM_ModelRenderer(this, 24, 18);
		ChignonL.addBoxMM(4F, -7F, 0.2F, 1, 3, 3, psize);
		ChignonL.setRotationPointMM(0F, 0F, 0F);
		bipedHead.addChildMM(ChignonL);

		ChignonB = new MMM_ModelRenderer(this, 52, 10);
		ChignonB.addBoxMM(-2F, -7.2F, 4F, 4, 4, 2, psize);
		ChignonB.setRotationPointMM(0F, 0F, 0F);
		bipedHead.addChildMM(ChignonB);

		Tail = new MMM_ModelRenderer(this, 46, 20);
		Tail.addBoxMM(-1.5F, -6.8F, 4F, 3, 9, 3, psize);
		Tail.setRotationPointMM(0F, 0F, 0F);
		bipedHead.addChildMM(Tail);

		SideTailR = new MMM_ModelRenderer(this, 58, 21);
		SideTailR.addBoxMM(-5.5F, -6.8F, 0.9F, 1, 8, 2, psize);
		SideTailR.setRotationPointMM(0.0F, 0.0F, 0.0F);
		bipedHead.addChildMM(SideTailR);

		SideTailL = new MMM_ModelRenderer(this, 58, 21);
		SideTailL.setMirror(true);
		SideTailL.addBoxMM(4.5F, -6.8F, 0.9F, 1, 8, 2, psize);
		SideTailL.setRotationPointMM(0.0F, 0.0F, 0.0F);
		bipedHead.addChildMM(SideTailL);

		mainFrame = new MMM_ModelRenderer(this, 0, 0);
		mainFrame.setRotationPointMM(0F, 0F + pyoffset, 0F);
		mainFrame.addChildMM(bipedHead);
		mainFrame.addChildMM(bipedBody);
		mainFrame.addChildMM(bipedRightArm);
		mainFrame.addChildMM(bipedLeftArm);
		mainFrame.addChildMM(bipedRightLeg);
		mainFrame.addChildMM(bipedLeftLeg);
		mainFrame.addChildMM(Skirt);

	}

	public float getHeight() {
		// �g��
		return 1.35F;
	}

	public float getWidth() {
		// ����
		return 0.5F;
	}

	public void equippedBlockPosition() {
		// �莝���u���b�N�̕\���ʒu
		GL11.glTranslatef(0.0F, 0.1275F, -0.3125F);
	}

	public void equippedItemPosition3D() {
		// �莝���RD�A�C�e���̕\���ʒu
		GL11.glTranslatef(0.02F, 0.1300F, 0.0F);
	}

	public void equippedItemPosition() {
		// �莝���A�C�e���̕\���ʒu
		GL11.glTranslatef(0.20F, 0.0800F, -0.0875F);
	}

	public void equippedHeadItemPosition() {
		// ���������A�C�e���̕\���ʒu
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
	}

	public void equippedItemBow() {
		// �莝���|�̕\���ʒu
		// GL11.glTranslatef(-0.07F, 0.005F, 0.3F);
		equippedItemPosition3D();
		// GL11.glTranslatef(-0.09F, -0.125F, 0.3F);
		GL11.glTranslatef(-0.05F, -0.075F, 0.1F);
	}

	public boolean isItemHolder() {
		// �A�C�e���������Ă���Ƃ��Ɏ��O�ɏo�����ǂ����B
		return false;
	}

	@Override
	public void setLivingAnimationsMM(float par2, float par3, float pRenderPartialTicks) {
		super.setLivingAnimationsMM(par2, par3, pRenderPartialTicks);
		float f3 = MMM_ModelCapsHelper.getCapsValueFloat(entityCaps, caps_interestedAngle, pRenderPartialTicks);
		bipedHead.rotateAngleZ = f3;
		bipedHeadwear.rotateAngleZ = f3;
	}

	@Override
	public void setRotationAnglesMM(float par1, float par2,
			float pTicksExisted, float pHeadYaw, float pHeadPitch, float par6) {
//		super.setRotationAnglesMM(par1, par2, pTicksExisted, pHeadYaw, pHeadPitch, par6);

		bipedHead.rotateAngleY = pHeadYaw / 57.29578F;
		bipedHead.rotateAngleX = pHeadPitch / 57.29578F;
		bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
		bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
		bipedRightArm.rotateAngleX = mh_cos(par1 * 0.6662F + 3.141593F) * 2.0F * par2 * 0.5F;
		bipedLeftArm.rotateAngleX = mh_cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		bipedRightArm.rotateAngleZ = 0.0F;
		bipedLeftArm.rotateAngleZ = 0.0F;
		bipedRightLeg.rotateAngleX = mh_cos(par1 * 0.6662F) * 1.4F * par2;
		bipedLeftLeg.rotateAngleX = mh_cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2;
		bipedRightLeg.rotateAngleY = 0.0F;
		bipedLeftLeg.rotateAngleY = 0.0F;

		if (isRiding) {
			// ��蕨�ɏ���Ă���
			bipedRightArm.rotateAngleX += -0.6283185F;
			bipedLeftArm.rotateAngleX += -0.6283185F;
			bipedRightLeg.rotateAngleX = -1.256637F;
			bipedLeftLeg.rotateAngleX = -1.256637F;
			bipedRightLeg.rotateAngleY = 0.3141593F;
			bipedLeftLeg.rotateAngleY = -0.3141593F;
		}
		// �A�C�e�������Ă�Ƃ��̘r�U���}����
		if (heldItemLeft != 0) {
			bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F
					- 0.3141593F * (float) heldItemLeft;
		}
		if (heldItemRight != 0) {
			bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F
					- 0.3141593F * (float) heldItemRight;
		}

		bipedRightArm.rotateAngleY = 0.0F;
		bipedLeftArm.rotateAngleY = 0.0F;
		if (onGround > -9990F && !aimedBow) {
			// �r�U��
			float f6 = onGround;
			bipedBody.rotateAngleY = MathHelper
					.sin(mh_sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
			Skirt.rotateAngleY = bipedBody.rotateAngleY;
			bipedRightArm.rotationPointZ = MathHelper
					.sin(bipedBody.rotateAngleY) * 4F;
			bipedRightArm.rotationPointX = -MathHelper
					.cos(bipedBody.rotateAngleY) * 4F + 1.0F;
			bipedLeftArm.rotationPointZ = -MathHelper
					.sin(bipedBody.rotateAngleY) * 4F;
			bipedLeftArm.rotationPointX = MathHelper
					.cos(bipedBody.rotateAngleY) * 4F - 1.0F;
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
			f6 = 1.0F - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			float f7 = mh_sin(f6 * 3.141593F);
			float f8 = mh_sin(onGround * 3.141593F)
					* -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
			bipedRightArm.rotateAngleX -= (double) f7 * 1.2D + (double) f8;
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
			bipedRightArm.rotateAngleZ = mh_sin(onGround * 3.141593F)
					* -0.4F;
		}
		if (isSneak) {
			// ���Ⴊ��
			bipedBody.rotateAngleX = 0.5F;
			bipedRightLeg.rotateAngleX -= 0.0F;
			bipedLeftLeg.rotateAngleX -= 0.0F;
			bipedRightArm.rotateAngleX += 0.4F;
			bipedLeftArm.rotateAngleX += 0.4F;
			bipedRightLeg.rotationPointZ = 3F;
			bipedLeftLeg.rotationPointZ = 3F;
			bipedRightLeg.rotationPointY = 6F;
			bipedLeftLeg.rotationPointY = 6F;
			bipedHead.rotationPointY = 1.0F;
			bipedHeadwear.rotationPointY = 1.0F;
			bipedHeadwear.rotateAngleX += 0.5F;
			Skirt.rotationPointY = 5.8F;
			Skirt.rotationPointZ = 2.7F;
			Skirt.rotateAngleX = 0.2F;
		} else {
			// �ʏ헧��
			bipedBody.rotateAngleX = 0.0F;
			bipedRightLeg.rotationPointZ = 0.0F;
			bipedLeftLeg.rotationPointZ = 0.0F;
			bipedRightLeg.rotationPointY = 7F;
			bipedLeftLeg.rotationPointY = 7F;
			bipedHead.rotationPointY = 0.0F;
			bipedHeadwear.rotationPointY = 0.0F;
			Skirt.rotationPointY = 7.0F;
			Skirt.rotationPointZ = 0.0F;
			Skirt.rotateAngleX = 0.0F;
		}
		if (isWait) {
			// �ҋ@��Ԃ̓��ʕ\��
			bipedRightArm.rotateAngleX = mh_sin(pTicksExisted * 0.067F) * 0.05F - 0.7F;
			bipedRightArm.rotateAngleY = 0.0F;
			bipedRightArm.rotateAngleZ = -0.4F;
			bipedLeftArm.rotateAngleX = mh_sin(pTicksExisted * 0.067F) * 0.05F - 0.7F;
			bipedLeftArm.rotateAngleY = 0.0F;
			bipedLeftArm.rotateAngleZ = 0.4F;
		} else {
			if (aimedBow) {
				// �|�\��
				float f6 = mh_sin(onGround * 3.141593F);
				float f7 = mh_sin((1.0F - (1.0F - onGround)
						* (1.0F - onGround)) * 3.141593F);
				bipedRightArm.rotateAngleZ = 0.0F;
				bipedLeftArm.rotateAngleZ = 0.0F;
				bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
				bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
				// bipedRightArm.rotateAngleX = -1.570796F;
				// bipedLeftArm.rotateAngleX = -1.570796F;
				bipedRightArm.rotateAngleX = -1.470796F;
				bipedLeftArm.rotateAngleX = -1.470796F;
				bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
				bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
				bipedRightArm.rotateAngleZ += MathHelper
						.cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedLeftArm.rotateAngleZ -= MathHelper
						.cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedRightArm.rotateAngleX += MathHelper
						.sin(pTicksExisted * 0.067F) * 0.05F;
				bipedLeftArm.rotateAngleX -= MathHelper
						.sin(pTicksExisted * 0.067F) * 0.05F;
				bipedRightArm.rotateAngleX += bipedHead.rotateAngleX;
				bipedLeftArm.rotateAngleX += bipedHead.rotateAngleX;
				bipedRightArm.rotateAngleY += bipedHead.rotateAngleY;
				bipedLeftArm.rotateAngleY += bipedHead.rotateAngleY;
			} else {
				// �ʏ�
				bipedRightArm.rotateAngleZ += 0.5F;
				bipedLeftArm.rotateAngleZ -= 0.5F;
				bipedRightArm.rotateAngleZ += mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedLeftArm.rotateAngleZ -= mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedRightArm.rotateAngleX += mh_sin(pTicksExisted * 0.067F) * 0.05F;
				bipedLeftArm.rotateAngleX -= mh_sin(pTicksExisted * 0.067F) * 0.05F;
			}
		}
	}

	@Override
	public void renderItems() {
		// �莝���̕\��
		GL11.glPushMatrix();
		if (entityCaps != null) {
			int ldominant = MMM_ModelCapsHelper.getCapsValueInt(entityCaps, caps_dominantArm);
			Arms[0].loadMatrix().renderItems(this, false, ldominant);
			// ���������i
			boolean lplanter = MMM_ModelCapsHelper.getCapsValueBoolean(entityCaps, caps_isPlanter);
			if (MMM_ModelCapsHelper.getCapsValueBoolean(entityCaps, caps_isCamouflage) || lplanter) {
				HeadMount.loadMatrix();
				if (lplanter) {
					HeadTop.renderItemsHead(this);
				} else {
					HeadMount.renderItemsHead(this);
				}
			}
		}
		GL11.glPopMatrix();
	}

}
