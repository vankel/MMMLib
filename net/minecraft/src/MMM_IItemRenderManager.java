package net.minecraft.src;

/**
 * �A�C�e���p�̓��ꃌ���_�[�Ɍp��������C���^�[�t�F�[�X�B
 * ����A�p�������Ă��Ȃ��Ă����\�b�h��Item�ɋL�q����Ă���Γ��삷��B
 */
public interface MMM_IItemRenderManager {

	public boolean renderItem(EntityLivingBase pEntityLiving, ItemStack pItemStack, int pIndex);
	public boolean renderItemInFirstPerson(float pDeltaTimepRenderPhatialTick, MMM_ItemRenderer pItemRenderer);
	public ResourceLocation getRenderTexture();
	public boolean isRenderItemWorld();

}
