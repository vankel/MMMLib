package net.minecraft.src;

import net.minecraft.client.Minecraft;

/**
 * ItemRenderer���u��������Ă��邩�ǂ����𔻒肷�邽�߂̃C���^�[�t�F�[�X�B
 */
public interface MMM_IItemRenderer {

	public Minecraft getMC();
	public ItemStack getItemToRender();
	public float getEquippedProgress();
	public float getPrevEquippedProgress();

}
