package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class MMM_Helper {

	public static final boolean isClient;
	public static final Package fpackage;
	public static final String packegeBase;
	public static final boolean isForge = ModLoader.isModLoaded("Forge");
	public static final Minecraft mc;
	
	static {
		fpackage = ModLoader.class.getPackage();
		packegeBase = fpackage == null ? "" : fpackage.getName().concat(".");

		Minecraft lm = null;
		try {
			lm = ModLoader.getMinecraftInstance();
		} catch (Exception e) {
//			e.printStackTrace();
		} catch (Error e) {
//			e.printStackTrace();
		}
		mc = lm;
		isClient = mc != null;
		
	}
	
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
		if (isForge) {
			try {
				pobject.getClass().getMethod("setTextureFile", String.class).invoke(pobject, "/gui/mmmforge.png");
			} catch (Exception e) {
			}
		}
	}

	@Deprecated
	public static boolean isForge() {
		return isForge;
	}

	/**
	 * Forge�p�N���X�l���B
	 */
	public static Class getEntityClass(BaseMod pMod, String pName) {
		if (isForge) {
			pName = pName.concat("_Forge");
		}
		if (fpackage != null) {
			pName = fpackage.getName() + "." + pName;
		}
		Class lclass = null;
		try {
			lclass = Class.forName(pName);
		} catch (Exception e) {
		}

		return lclass;
	}
	
	/**
	 * ���M�p�f�[�^�̃Z�b�g
	 */
	public static void setValue(byte[] pData, int pIndex, int pVal, int pSize) {
		for (int li = 0; li < pSize; li++) {
			pData[pIndex++] = (byte)(pVal & 0xff);
			pVal = pVal >>> 8;
		}
	}
	
	public static void setInt(byte[] pData, int pIndex, int pVal) {
    	pData[pIndex++]	= (byte)(pVal & 0xff);
    	pData[pIndex++]	= (byte)((pVal >>> 8) & 0xff);
    	pData[pIndex++]	= (byte)((pVal >>> 16) & 0xff);
    	pData[pIndex]	= (byte)((pVal >>> 24) & 0xff);
	}
	
	public static int getInt(byte[] pData, int pIndex) {
		return (pData[pIndex] & 0xff) | ((pData[pIndex + 1] & 0xff) << 8) | ((pData[pIndex + 2] & 0xff) << 16) | ((pData[pIndex + 3] & 0xff) << 24);
	}
	
	public static void setShort(byte[] pData, int pIndex, int pVal) {
		pData[pIndex++]	= (byte)(pVal & 0xff);
		pData[pIndex]	= (byte)((pVal >>> 8) & 0xff);
	}
	
	public static short getShort(byte[] pData, int pIndex) {
		return (short)((pData[pIndex] & 0xff) | ((pData[pIndex + 1] & 0xff) << 8));
	}

}
