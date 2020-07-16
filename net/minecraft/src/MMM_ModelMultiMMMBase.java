package net.minecraft.src;

import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

/**
 * MMM�̎����R�[�h���܂ޕ����B
 * ModelMultiBase�ɒǉ�����ɑ���邩�������Ŏ����B
 */
public abstract class MMM_ModelMultiMMMBase extends MMM_ModelMultiBase {

	public Map<String, MMM_EquippedStabilizer> stabiliser;

	@Deprecated
	public float onGround;
	@Deprecated
	public float heldItemLeft;
	@Deprecated
	public float heldItemRight;


	public MMM_ModelMultiMMMBase() {
		super();
	}
	public MMM_ModelMultiMMMBase(float pSizeAdjust) {
		super(pSizeAdjust);
	}
	public MMM_ModelMultiMMMBase(float pSizeAdjust, float pYOffset, int pTextureWidth, int pTextureHeight) {
		super(pSizeAdjust, pYOffset, pTextureWidth, pTextureHeight);
	}

	/**
	 * mainFrame�ɑS�ĂԂ牺�����Ă���Ȃ�ΕW���ŕ`�悷��B
	 */
	@Override
	public void render(MMM_IModelCaps pEntityCaps, float par2, float par3, float ticksExisted,
			float pheadYaw, float pheadPitch, float par7, boolean pIsRender) {
		setRotationAngles(par2, par3, ticksExisted, pheadYaw, pheadPitch, par7, pEntityCaps);
		mainFrame.render(par7, pIsRender);
		renderStabilizer(pEntityCaps, par2, par3, ticksExisted, pheadYaw, pheadPitch, par7);
	}

	/**
	 * �ʏ�̃����_�����O�O�ɌĂ΂��B
	 * @return false��Ԃ��ƒʏ�̃����_�����O���X�L�b�v����B
	 */
	public boolean preRender(float par2, float par3,
			float par4, float par5, float par6, float par7) {
		return true;
	}

	/**
	 * �ʏ�̃����_�����O��ɌĂԁB ��{�I�ɑ����i�Ȃǂ̎����^�����Ȃ��p�[�c�̕`��p�B
	 */
	public void renderExtention(float par2, float par3,
			float par4, float par5, float par6, float par7) {
	}

	/**
	 * �X�^�r���C�U�[�̕`��B �����ł͌Ă΂�Ȃ��̂�render���ŌĂԕK�v������܂��B
	 */
	protected void renderStabilizer(MMM_IModelCaps pEntityCaps, float par2, float par3,
			float ticksExisted, float pheadYaw, float pheadPitch, float par7) {
		// �X�^�r���C�U�[�̕`��AdoRender�̕����������H
		if (stabiliser == null || stabiliser.isEmpty() || render == null)
			return;

		GL11.glPushMatrix();
		for (Entry<String, MMM_EquippedStabilizer> le : stabiliser.entrySet()) {
			MMM_EquippedStabilizer les = le.getValue();
			if (les != null && les.equipPoint != null) {
				MMM_ModelStabilizerBase lsb = les.stabilizer;
				if (lsb.isLoadAnotherTexture()) {
					render.loadTexture(lsb.getTexture());
				}
				les.equipPoint.loadMatrix();
				lsb.render(this, null, par2, par3, ticksExisted, pheadYaw, pheadPitch, par7);
			}
		}
		GL11.glPopMatrix();
	}

	/**
	 * ���f���ؑ֎��Ɏ��s�����R�[�h
	 * @param pEntityCaps
	 * Entity�̒l�𑀍삷�邽�߂�ModelCaps�B
	 */
	public void changeModel(MMM_IModelCaps pEntityCaps) {
		// �J�E���^�n�̉��Z�l�A���~�b�g�l�̐ݒ�ȂǍs���\��B
	}

	/**
	 * �������[�h���Ɏ��s
	 */
	public void buildTexture() {
		
	}

	public void setDefaultPause() {
	}

	public void setDefaultPause(float par1, float par2, float pTicksExisted,
			float pHeadYaw, float pHeadPitch, float par6, MMM_IModelCaps pEntityCaps) {
		setDefaultPause();
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		switch (pIndex) {
		case caps_changeModel:
			changeModel((MMM_IModelCaps)pArg[0]);
			return true;
		}
		return super.setCapsValue(pIndex, pArg);
	}


}
