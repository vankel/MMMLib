package net.minecraft.src;

/**
 * MMM_Texture�d�l�̃e�N�X�`���p�b�N�ݒ�ɑΉ����Ă���Entity�֌p��������B
 */
public interface MMM_ITextureEntity {

	/**
	 * Server�p�B
	 * TextureManager���T�[�o�[����Entity�փe�N�X�`���ύX�̒ʒm���s���B
	 * @param pIndex
	 * �ݒ肳���e�N�X�`���p�b�N�̃C���f�b�N�X�iTextureBoxServer�j
	 */
	public void setTexturePackIndex(int pColor, int[] pIndex);

	/**
	 * Client�p�B
	 * TextureManager���N���C�A���g����Entity�փe�N�X�`���ύX�̒ʒm���s���B
	 * @param pPackName
	 * �ݒ肳���e�N�X�`���p�b�N�̖��́iTextureBoxClient�j
	 */
	public void setTexturePackName(MMM_TextureBox[] pTextureBox);

}
