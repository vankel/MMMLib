package net.minecraft.src;

public class MMM_Helper {

	/**
	 * �}���`�Ή��p�B
	 * ItemStack�ɏ��X�V���s���ƁA�T�[�o�[���Ƃ̍��ق���Slot�̃A�b�v�f�[�g���s����B
	 * ���̍ہAUsingItem�̍X�V�������s���Ȃ����߈Ⴄ�A�C�e���Ɏ��ւ���ꂽ�Ɣ��肳���B
	 * �����ł͔�r�p�Ɏg����X�^�b�N���X�g�������I�ɏ������鎖�ɂ��Ή������B
	 */
	public static void updateCheckinghSlot(Entity pEntity, ItemStack pItemstack) {
		if (pEntity instanceof EntityPlayerMP) {
			// �T�[�o�[���ł̂ݏ���
			EntityPlayerMP lep = (EntityPlayerMP)pEntity;
			Container lctr = lep.openContainer;
			for (int li = 0; li < lctr.inventorySlots.size(); li++) {
				ItemStack lis = ((Slot)lctr.getSlot(li)).getStack(); 
				if (lis == pItemstack) {
					lctr.inventoryItemStacks.set(li, pItemstack.copy());
					break;
				}
			}
		}
	}
	
	/**
	 * Forge�p�ǉ��e�N�X�`���̐ݒ�
	 */
	public static void setForgeIcon(Object pobject) {
		if (mod_MMM_MMMLib.isForge) {
			try {
				pobject.getClass().getMethod("setTextureFile", String.class).invoke(pobject, "/gui/mmmforge.png");
			} catch (Exception e) {
			}
		}
	}

	public static boolean isForge() {
		return mod_MMM_MMMLib.isForge;
	}

}
