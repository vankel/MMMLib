package net.minecraft.src;

import static net.minecraft.src.mod_MMM_MMMLib.*;

import java.lang.reflect.Constructor;

import net.minecraft.client.Minecraft;

public class MMM_Client {

	public static Class itemRendererClass;
	public static Constructor itemRendererConstructor;


	public static void getItemRendererClass() {
		itemRendererClass = MMM_ItemRenderer.class;
		if (MMM_Helper.mc.entityRenderer.itemRenderer.getClass().getSimpleName().equals("ItemRendererHD")) {
			try {
				String ls = "MMM_ItemRendererHD";
				if (MMM_Helper.fpackage != null) {
					ls = MMM_Helper.fpackage.getName() + "." + ls;
				}
				Class lc = Class.forName(ls);
				itemRendererClass = lc;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			itemRendererConstructor = itemRendererClass.getConstructor(Minecraft.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void setItemRenderer() {
		if (!(MMM_Helper.mc.entityRenderer.itemRenderer instanceof MMM_IItemRenderer)) {
			mod_MMM_MMMLib.Debug("replace entityRenderer.itemRenderer.");
			getItemRendererClass();
			try {
				Object lo = itemRendererConstructor.newInstance(MMM_Helper.mc);
				MMM_Helper.mc.entityRenderer.itemRenderer = (ItemRenderer)lo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!(RenderManager.instance.itemRenderer instanceof MMM_IItemRenderer)) {
			mod_MMM_MMMLib.Debug("replace RenderManager.itemRenderer.");
			getItemRendererClass();
			try {
				Object lo = itemRendererConstructor.newInstance(MMM_Helper.mc);
				RenderManager.instance.itemRenderer = (ItemRenderer)lo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// GUI�̕\����ς���ɂ͏펞�Ď����K�v�H
	}

	public static void clientCustomPayload(NetClientHandler var1, Packet250CustomPayload var2) {
		// �N���C�A���g���̓���p�P�b�g��M����
		byte lmode = var2.data[0];
		int leid = 0;
		Entity lentity = null;
		if ((lmode & 0x80) != 0) {
			leid = MMM_Helper.getInt(var2.data, 1);
			lentity = MMM_Helper.getEntity(var2.data, 1, MMM_Helper.mc.theWorld);
			if (lentity == null) return;
		}
		Debug(String.format("MMM|Upd Clt Call[%2x:%d].", lmode, leid));
		
		switch (lmode) {
		case MMM_Client_SetTextureIndex:
			// �e�N�X�`�����̂ɑΉ�����T�[�o�[���Ɠ����C���f�b�N�X��ݒ肷��
			/*
			 * 0:id
			 * 1:index
			 * 2-5:TextureIndex
			 */
			int li7 = MMM_Helper.getShort(var2.data, 2);
			String ls7 = MMM_TextureManager.getRequestString(var2.data[1]);
			Debug(String.format("%d = %d : %s", li7, var2.data[1], ls7 == null ? "NULL" : ls7));
			MMM_TextureManager.setStringToIndex(li7, ls7);
			break;
		case MMM_Client_SetTextureStr:
			// �C���f�b�N�X�ɑΉ�����e�N�X�`�����̂�o�^����A��ɃT�[�o�[���݂̂ɖ��̓o�^������ꍇ�Ɏg�p�B
			/*
			 * 0:id
			 * 1-2:index �o�^�e�N�X�`���ԍ�
			 * 3-:Str ����
			 */
			int li8 = MMM_Helper.getShort(var2.data, 1);
			String ls8 = MMM_Helper.getStr(var2.data, 3);
			Debug(String.format("%d = %s", li8, ls8 == null ? "NULL" : ls8));
			MMM_TextureManager.setStringToIndex(li8, ls8);
			break;
			
		}
	}

	public static void clientConnect(NetClientHandler var1) {
		if (MMM_Helper.mc.isIntegratedServerRunning()) {
//			Debug("Localmode: InitTextureList.");
//			MMM_TextureManager.initTextureList(true);
		} else {
			Debug("Remortmode: ClearTextureList.");
			MMM_TextureManager.initTextureList(false);
		}
	}

	public static void clientDisconnect(NetClientHandler var1) {
//		super.clientDisconnect(var1);
		Debug("Localmode: InitTextureList.");
		MMM_TextureManager.initTextureList(true);
	}

	public static void sendToServer(byte[] pData) {
		ModLoader.clientSendPacket(new Packet250CustomPayload("MMM|Upd", pData));
		Debug(String.format("MMM|Upd:%2x:NOEntity", pData[0]));
	}

	public static boolean isIntegratedServerRunning() {
		return MMM_Helper.mc.isIntegratedServerRunning();
	}

	public static void setArmorPrefix() {
		// �A�[�}�[�v���t�B�b�N�X��ݒ�
		try {
			ModLoader.setPrivateValue(RenderBiped.class, null, 4, ModLoader.getPrivateValue(RenderPlayer.class, null, 3));
		} catch (Exception e) {
		}
	}

}
