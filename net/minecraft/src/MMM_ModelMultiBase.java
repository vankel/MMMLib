package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

/**
 * �}���`���f���p�̊�{�N���X�A������p�����Ă���΃}���`���f���Ƃ��Ďg�p�ł���B
 * Mincraft�l�C�e�B�u�ȃN���X��p���֐��Ȃǂ�r�����āA��ǉ��΍���s���B
 * �p���N���X�ł͂Ȃ��Ȃ������߁A���ړI�Ȍ݊����͂Ȃ��B
 */
public abstract class MMM_ModelMultiBase extends MMM_ModelBase implements MMM_IModelCaps {

	public float heldItem[] = new float[] {0.0F, 0.0F};
	public boolean aimedBow;
	public boolean isSneak;
	public boolean isWait;
	
	public MMM_ModelRenderer mainFrame;
	public MMM_ModelRenderer HeadMount;
	public MMM_ModelRenderer HeadTop;
	public MMM_ModelRenderer Arms[];
	public MMM_ModelRenderer HardPoint[];
	
	public float entityIdFactor;
	public int entityTicksExisted;
	// �ϐ��ł���Ӗ��Ȃ��H
	public float scaleFactor = 0.9375F;
	/**
	 * ���f���������Ă���@�\�Q
	 */
	private final Map<String, Integer> fcapsmap = new HashMap<String, Integer>() {{
		put("onGround",			caps_onGround);
		put("isRiding",			caps_isRiding);
		put("isSneak",			caps_isSneak);
		put("isWait",			caps_isWait);
		put("isChild",			caps_isChild);
		put("heldItemLeft",		caps_heldItemLeft);
		put("heldItemRight",	caps_heldItemRight);
		put("aimedBow",			caps_aimedBow);
		put("ScaleFactor", 		caps_ScaleFactor);
		put("entityIdFactor",	caps_entityIdFactor);
		put("dominantArm",	caps_dominantArm);
	}};



	public MMM_ModelMultiBase() {
		this(0.0F);
	}

	public MMM_ModelMultiBase(float pSizeAdjust) {
		this(pSizeAdjust, 0.0F, 64, 32);
	}

	public MMM_ModelMultiBase(float pSizeAdjust, float pYOffset, int pTextureWidth, int pTextureHeight) {
		isSneak = false;
		aimedBow = false;
		textureWidth = pTextureWidth;
		textureHeight = pTextureHeight;
		
		if (MMM_Helper.isClient) {
			// �n�[�h�|�C���g
			Arms = new MMM_ModelRenderer[2];
			HeadMount = new MMM_ModelRenderer(this, "HeadMount");
			HeadTop = new MMM_ModelRenderer(this, "HeadTop");
			
			initModel(pSizeAdjust, pYOffset);
		}
	}

	// �Ǝ���`�֐��Q

	/**
	 * ���f���̏������R�[�h
	 */
	public abstract void initModel(float psize, float pyoffset);

	/**
	 * �A�[�}�[���f���̃T�C�Y��Ԃ��B
	 * �T�C�Y�͓����̂��̂���B
	 */
	public abstract float[] getArmorModelsSize();

	/**
	 * ���f���w�莌�Ɉ˂炸�Ɏg�p����e�N�X�`���p�b�N���B
	 * ��̃e�N�X�`���ɕ����̃��f�������蓖�Ă鎞�Ɏg���B
	 * @return
	 */
	public String getUsingTexture() {
		return null;
	}

	/**
	 *  �g��
	 */
	@Deprecated
	public abstract float getHeight();
	/**
	 *  �g��
	 */
	public float getHeight(MMM_IModelCaps pEntityCaps) {
		return getHeight();
	}
	/**
	 * ����
	 */
	@Deprecated
	public abstract float getWidth();
	/**
	 * ����
	 */
	public float getWidth(MMM_IModelCaps pEntityCaps) {
		return getWidth();
	}
	/**
	 * ���f����Y�I�t�Z�b�g
	 */
	@Deprecated
	public abstract float getyOffset();
	/**
	 * ���f����Y�I�t�Z�b�g
	 */
	public float getyOffset(MMM_IModelCaps pEntityCaps) {
		return getyOffset();
	}
	/**
	 * ��ɏ悹�鎞�̃I�t�Z�b�g��
	 */
	@Deprecated
	public abstract float getMountedYOffset();
	/**
	 * ��ɏ悹�鎞�̃I�t�Z�b�g��
	 */
	public float getMountedYOffset(MMM_IModelCaps pEntityCaps) {
		return getMountedYOffset();
	}

	/**
	 * �A�C�e���������Ă���Ƃ��Ɏ��O�ɏo�����ǂ����B
	 */
	@Deprecated
	public boolean isItemHolder() {
		return false;
	}
	/**
	 * �A�C�e���������Ă���Ƃ��Ɏ��O�ɏo�����ǂ����B
	 */
	public boolean isItemHolder(MMM_IModelCaps pEntityCaps) {
		return isItemHolder();
	}

	/**
	 * �\�����ׂ����ׂĂ̕��i
	 */
	@Deprecated
	public void showAllParts() {
	}
	/**
	 * �\�����ׂ����ׂĂ̕��i
	 */
	public void showAllParts(MMM_IModelCaps pEntityCaps) {
		showAllParts();
	}

	/**
	 * ���ʂ��Ƃ̑��b�\���B
	 * @param parts
	 * 3:�����B
	 * 2:�����B
	 * 1:�r��
	 * 0:����
	 * @param index
	 * 0:inner
	 * 1:outer
	 * @return
	 * �߂�l�͊�{ -1
	 */
	public int showArmorParts(int parts, int index) {
		return -1;
	}

	/**
	 * �n�[�h�|�C���g�ɐڑ����ꂽ�A�C�e����\������
	 */
	public abstract void renderItems(MMM_IModelCaps pEntityCaps);

	public abstract void renderFirstPersonHand(MMM_IModelCaps pEntityCaps);



	// IModelCaps

	@Override
	public Map<String, Integer> getModelCaps() {
		return fcapsmap;
	}

	@Override
	public Object getCapsValue(int pIndex, Object ...pArg) {
		switch (pIndex) {
		case caps_onGround:
			return onGrounds;
		case caps_isRiding:
			return isRiding;
		case caps_isSneak:
			return isSneak;
		case caps_isWait:
			return isWait;
		case caps_isChild:
			return isChild;
		case caps_heldItemLeft:
			return heldItem[1];
		case caps_heldItemRight:
			return heldItem[0];
		case caps_aimedBow:
			return aimedBow;
		case caps_entityIdFactor:
			return entityIdFactor;
		case caps_ticksExisted:
			return entityTicksExisted;
		case caps_ScaleFactor:
			return scaleFactor;
		case caps_dominantArm:
			return dominantArm;
		}
		return null;
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		switch (pIndex) {
		case caps_onGround:
			for (int li = 0; li < onGrounds.length && li < pArg.length; li++) {
				onGrounds[li] = (Float)pArg[li];
			}
			return true;
		case caps_isRiding:
			isRiding = (Boolean)pArg[0];
			return true;
		case caps_isSneak:
			isSneak = (Boolean)pArg[0];
			return true;
		case caps_isWait:
			isWait = (Boolean)pArg[0];
			return true;
		case caps_isChild:
			isChild = (Boolean)pArg[0];
			return true;
		case caps_heldItemLeft:
			heldItem[1] = (Integer)pArg[0];
			return true;
		case caps_heldItemRight:
			heldItem[0] = (Integer)pArg[0];
			return true;
		case caps_aimedBow:
			aimedBow = (Boolean)pArg[0];
			return true;
		case caps_entityIdFactor:
			entityIdFactor = (Float)pArg[0];
			return true;
		case caps_ticksExisted:
			entityTicksExisted = (Integer)pArg[0];
			return true;
		case caps_ScaleFactor:
			scaleFactor = (Float)pArg[0];
			return true;
		case caps_dominantArm:
			dominantArm = (Integer)pArg[0];
			return true;
		}
		
		return false;
	}

}
