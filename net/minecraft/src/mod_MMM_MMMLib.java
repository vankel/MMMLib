package net.minecraft.src;

import java.awt.event.TextEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;

public class mod_MMM_MMMLib extends BaseMod {

	public static final String Revision = "5";
	
	public static final int MMM_Server_GetTextureIndex	= 0x00;
	public static final int MMM_Server_SetTextureIndex	= 0x02;
	public static final int MMM_Server_GetTextureStr	= 0x01;
	public static final int MMM_Client_SetTextureIndex	= 0x00;
	public static final int MMM_Client_SetTextureStr	= 0x01;
	
	
	@MLProp()
	public static boolean isDebugView = false;
	@MLProp()
	public static boolean isDebugMessage = true;
	@MLProp(info = "Override RenderItem.")
	public static boolean renderHacking = true;
	@MLProp(info = "starting auto assigned ID.")
	public static int startVehicleEntityID = 2176;



	public static void Debug(String pText, Object... pVals) {
		// �f�o�b�O���b�Z�[�W
		if (isDebugMessage) {
			System.out.println(String.format("MMMLib-" + pText, pVals));
		}
	}

	@Override
	public String getName() {
		return "MMMLib";
	}

	@Override
	public String getVersion() {
		return "1.5.1-" + Revision;
	}
	
	@Override
	public String getPriorities() {
		return MMM_Helper.isForge ? "befor-all" : "before:*";
	}

	@Override
	public void load() {
		// ������
		Debug(MMM_Helper.isClient ? "Client" : "Server");
		Debug(MMM_Helper.isForge ? "Forge" : "Modloader");
		MMM_FileManager.init();
		MMM_TextureManager.init();
		MMM_StabilizerManager.init();
		ModLoader.setInGameHook(this, true, true);
		if (isDebugView) {
			MMM_EntityDummy.isEnable = true;
		}
		
		// �Ǝ��p�P�b�g�p�`�����l��
		ModLoader.registerPacketChannel(this, "MMM|Upd");
	}

	@Override
	public void modsLoaded() {
		// ���[�h
		if (MMM_Helper.isClient) {
			// �e�N�X�`���p�b�N�̍\�z
			MMM_TextureManager.loadTextures();
			MMM_StabilizerManager.loadStabilizer();
			MMM_Client.setArmorPrefix();
		} else {
			MMM_TextureManager.loadTextureIndex();
		}
		
		// �e�N�X�`���C���f�b�N�X�̍\�z
		Debug("Localmode: InitTextureList.");
		MMM_TextureManager.initTextureList(true);
	}

	@Override
	public void addRenderer(Map var1) {
		if (isDebugView) {
			var1.put(net.minecraft.src.MMM_EntityDummy.class, new MMM_RenderDummy());
		}
		//RenderItem
		var1.put(EntityItem.class, new MMM_RenderItem());
	}

	@Override
	public boolean onTickInGame(float var1, Minecraft var2) {
		if (isDebugView && MMM_Helper.isClient) {
			// �_�~�[�}�[�J�[�̕\���p����
			if (var2.theWorld != null && var2.thePlayer != null) {
				try {
					for (Iterator<MMM_EntityDummy> li = MMM_EntityDummy.appendList.iterator(); li.hasNext();) {
						var2.theWorld.spawnEntityInWorld(li.next());
						li.remove();
					}
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		}
		
		// �A�C�e�������_�[���I�[�o�[���C�h
		if (renderHacking && MMM_Helper.isClient) {
			MMM_Client.setItemRenderer();
		}
		
		return true;
	}

	@Override
	public void serverCustomPayload(NetServerHandler var1, Packet250CustomPayload var2) {
		// �T�[�o���̓���
		byte lmode = var2.data[0];
		int leid = 0;
		Entity lentity = null;
		if ((lmode & 0x80) != 0) {
			leid = MMM_Helper.getInt(var2.data, 1);
			lentity = MMM_Helper.getEntity(var2.data, 1, var1.playerEntity.worldObj);
			if (lentity == null) return;
		}
		Debug("MMM|Upd Srv Call[%2x:%d].", lmode, leid);
		byte[] ldata;
		
		switch (lmode) {
		case MMM_Server_GetTextureIndex:
			// �e�N�X�`�����̂̃��N�G�X�g�ɑ΂��Ĕԍ���Ԃ�
			/*
			 * 0:ID
			 * 1:index �v�����������̔ԍ�
			 * 2-:Str
			 */
			String lsgti = MMM_Helper.getStr(var2.data, 2);
			int ligti = 0;
			Entry<Integer, MMM_TextureBoxServer> legti = MMM_TextureManager.getTextureBoxServer(lsgti);
			if (legti != null) {
				ligti = MMM_TextureManager.getTextureBoxServer(lsgti).getKey();
			}
			Debug("%s : %d = %d", lsgti, var2.data[1], ligti);
			ldata = new byte[] {
					MMM_Client_SetTextureIndex,
					var2.data[1],
					0, 0
			};
			MMM_Helper.setShort(ldata, 2, ligti);
			sendToClient(var1, ldata);
			break;
		case MMM_Server_SetTextureIndex:
			// �e�N�X�`�����̂̃��N�G�X�g�ɑ΂��Ĕԍ���Ԃ�
			/*
			 * 0:ID
			 * 1:index �v�����������̔ԍ�
			 * 2-3:contColorBits
			 * 4-5:wildColorBits
			 * 6-9:height
			 * 10-13:width
			 * 14-17:yoffset
			 * 18-:Str
			 */
			MMM_TextureBoxServer lbox = new MMM_TextureBoxServer(var2.data);
			int li = MMM_TextureManager.setTextureBoxToIndex(lbox);
			Debug("%d = %d : %04x : %s", li, var2.data[1], lbox.wildColor, lbox.textureName == null ? "NULL" : lbox.textureName);
			ldata = new byte[] {
					MMM_Client_SetTextureIndex,
					var2.data[1],
					0, 0
			};
			MMM_Helper.setShort(ldata, 2, li);
			sendToClient(var1, ldata);
			break;
		case MMM_Server_GetTextureStr:
			// �C���f�b�N�X����e�N�X�`�����̂�Ԃ�
			/*
			 * 0:ID
			 * 1-2:index �o�^�e�N�X�`���ԍ�
			 */
			int li8 = MMM_Helper.getShort(var2.data, 1);
			MMM_TextureBoxServer lb8 = MMM_TextureManager.getIndexToBox(li8);
			Debug("%d : %s", li8, lb8.textureName == null ? "NULL" : lb8.textureName);
			ldata = new byte[3 + lb8.textureName.getBytes().length];
			ldata[0] = MMM_Client_SetTextureStr;
			ldata[1] = var2.data[1];
			ldata[2] = var2.data[2];
			MMM_Helper.setStr(ldata, 3, lb8.textureName);
			sendToClient(var1, ldata);
			break;
			
		}
	}

	public static void sendToClient(NetServerHandler pHandler, byte[] pData) {
		ModLoader.serverSendPacket(pHandler, new Packet250CustomPayload("MMM|Upd", pData));
	}

	@Override
	public void clientCustomPayload(NetClientHandler var1, Packet250CustomPayload var2) {
		MMM_Client.clientCustomPayload(var1, var2);
	}

	@Override
	public void clientConnect(NetClientHandler var1) {
		MMM_Client.clientConnect(var1);
	}

	@Override
	public void clientDisconnect(NetClientHandler var1) {
		if (MMM_Helper.isClient) {
			MMM_Client.clientDisconnect(var1);
		} else {
			MMM_TextureManager.saveTextureIndex();
		}
	}

}
