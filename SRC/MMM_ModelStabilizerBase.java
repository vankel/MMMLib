package net.minecraft.src;

public abstract class MMM_ModelStabilizerBase extends ModelBase {
	
	public MMM_ModelStabilizerBase() {
	}

	/**
	 * �g�p�����e�N�X�`����Ԃ��B
	 */
	public String getTexture() {
		return "";
	}
	
	/**
	 * ���̃n�[�h�|�C���g�ɑ����\���ǂ�����Ԃ��B
	 * pName:�n�[�h�|�C���g�̎��ʖ��́B
	 */
	public boolean checkEquipment(String pName) {
		return true;
	}
	
	/**
	 * �p�[�c�̖��́B
	 */
	public String getName() {
		return "";
	}
	/**
	 * ���C�h����̃e�N�X�`�������̂܂܎g�킸�ɁA�Ⴄ�e�N�X�`�����g�����H
	 */
	public boolean isLoadAnotherTexture() {
		return false;
	}
	
	/**
	 * ���������Ɏ��s�����
	 */
	public void init(MMM_EquippedStabilizer pequipped) {
		// �ϐ��Ȃǂ��`����
	}
	
}
