package net.minecraft.src;

import java.lang.reflect.Method;

/**
 * ����A�C�e�������_���p�̎��ʃC���^�[�t�F�[�X
 *
 */
public interface MMM_IItemRender {

	public boolean renderItem(EntityLiving pEntity, ItemStack pItemstack, int pIndex);
	public boolean renderItemInFirstPerson(float pDelta);
	public String getRenderTexture();

}
