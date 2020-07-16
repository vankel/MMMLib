package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MMM_TextureBoxBase {

	public String textureName;
	protected int contractColor;
	protected int wildColor;
	protected float modelHeight;
	protected float modelWidth;
	protected float modelYOffset;
	protected float modelMountedYOffset;


	public void setModelSize(float pHeight, float pWidth, float pYOffset, float pMountedYOffset) {
		modelHeight = pHeight;
		modelWidth = pWidth;
		modelYOffset = pYOffset;
		modelMountedYOffset = pMountedYOffset;
	}

	protected int getRandomColor(int pColor, Random pRand) {
		List<Integer> llist = new ArrayList<Integer>();
		for (int li = 0; li < 16; li++) {
			if ((pColor & 0x01) > 0) {
				llist.add(li);
			}
			pColor = pColor >>> 1;
		}
		
		if (llist.size() > 0) {
			return llist.get(pRand.nextInt(llist.size()));
		} else {
			return -1;
		}
	}

	/**
	 * �_��F�̗L�����r�b�g�z��ɂ��ĕԂ�
	 */
	public int getContractColorBits() {
		return contractColor;
	}

	/**
	 * �쐶�F�̗L�����r�b�g�z��ɂ��ĕԂ�
	 */
	public int getWildColorBits() {
		return wildColor;
	}

	/**
	 * �쐶�̃��C�h�̐F�������_���ŕԂ�
	 */
	public int getRandomWildColor(Random pRand) {
		return getRandomColor(getWildColorBits(), pRand);
	}

	/**
	 * �_��̃��C�h�̐F�������_���ŕԂ�
	 */
	public int getRandomContractColor(Random pRand) {
		return getRandomColor(getContractColorBits(), pRand);
	}

	public float getHeight() {
		return modelHeight;
	}

	public float getWidth() {
		return modelWidth;
	}

	public float getYOffset() {
		return modelYOffset;
	}

	public float getMountedYOffset() {
		return modelMountedYOffset;
	}

}
