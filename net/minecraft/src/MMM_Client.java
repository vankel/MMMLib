package net.minecraft.src;

import static net.minecraft.src.mod_MMM_MMMLib.Debug;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class MMM_Client {

	public static MMM_ItemRenderer itemRenderer;


	public static void setItemRenderer() {
		if (itemRenderer == null) {
			itemRenderer = new MMM_ItemRenderer(MMM_Helper.mc);
		}
		if (!(MMM_Helper.mc.entityRenderer.itemRenderer instanceof MMM_ItemRenderer)) {
			mod_MMM_MMMLib.Debug("replace entityRenderer.itemRenderer.");
			MMM_Helper.mc.entityRenderer.itemRenderer = itemRenderer;
		}
		if (!(RenderManager.instance.itemRenderer instanceof MMM_ItemRenderer)) {
			mod_MMM_MMMLib.Debug("replace RenderManager.itemRenderer.");
			RenderManager.instance.itemRenderer = itemRenderer;
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
		Debug("MMM|Upd Clt Call[%2x:%d].", lmode, leid);
		
		switch (lmode) {
		case MMM_Statics.Client_SetTextureIndex:
			// �₢���킹���e�N�X�`���p�b�N�̊Ǘ��ԍ����󂯎��
			MMM_TextureManager.reciveFormServerSetTexturePackIndex(var2.data);
			break;
		case MMM_Statics.Client_SetTexturePackName:
			// �Ǘ��ԍ��ɓo�^����Ă���e�N�X�`���p�b�N�̏����󂯎��
			MMM_TextureManager.reciveFromServerSetTexturePackName(var2.data);
			break;
		}
	}

	public static void clientConnect(NetClientHandler var1) {
		if (MMM_Helper.mc.isIntegratedServerRunning()) {
			Debug("Localmode: InitTextureList.");
			MMM_TextureManager.initTextureList(true);
		} else {
			Debug("Remortmode: ClearTextureList.");
			MMM_TextureManager.initTextureList(false);
		}
	}

	public static void clientDisconnect(NetClientHandler var1) {
//		super.clientDisconnect(var1);
//		Debug("Localmode: InitTextureList.");
//		MMM_TextureManager.initTextureList(true);
	}

	public static void sendToServer(byte[] pData) {
		ModLoader.clientSendPacket(new Packet250CustomPayload("MMM|Upd", pData));
		Debug("MMM|Upd:%2x:NOEntity", pData[0]);
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

	/**
	 * Duo���g�����͕K��Render���̂��̊֐���u�������邱�ƁB
	 * @param par1EntityLiving
	 * @param par2
	 */
	public static void renderArrowsStuckInEntity(EntityLiving par1EntityLiving, float par2,
			Render pRender, MMM_ModelBase pModel) {
		int lacount = par1EntityLiving.getArrowCountInEntity();
		
		if (lacount > 0) {
			EntityArrow larrow = new EntityArrow(par1EntityLiving.worldObj, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
			Random lrand = new Random((long)par1EntityLiving.entityId);
			RenderHelper.disableStandardItemLighting();
			
			for (int var6 = 0; var6 < lacount; ++var6) {
				GL11.glPushMatrix();
				MMM_ModelRenderer var7 = pModel.getRandomModelBox(lrand);
				MMM_ModelBoxBase var8 = var7.cubeList.get(lrand.nextInt(var7.cubeList.size()));
				var7.postRender(0.0625F);
				float var9 = lrand.nextFloat();
				float var10 = lrand.nextFloat();
				float var11 = lrand.nextFloat();
				float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0F;
				float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0F;
				float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0F;
				GL11.glTranslatef(var12, var13, var14);
				var9 = var9 * 2.0F - 1.0F;
				var10 = var10 * 2.0F - 1.0F;
				var11 = var11 * 2.0F - 1.0F;
				var9 *= -1.0F;
				var10 *= -1.0F;
				var11 *= -1.0F;
				float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
				larrow.prevRotationYaw = larrow.rotationYaw = (float)(Math.atan2((double)var9, (double)var11) * 180.0D / Math.PI);
				larrow.prevRotationPitch = larrow.rotationPitch = (float)(Math.atan2((double)var10, (double)var15) * 180.0D / Math.PI);
				double var16 = 0.0D;
				double var18 = 0.0D;
				double var20 = 0.0D;
				float var22 = 0.0F;
				pRender.renderManager.renderEntityWithPosYaw(larrow, var16, var18, var20, var22, par2);
				GL11.glPopMatrix();
			}
			
			RenderHelper.enableStandardItemLighting();
		}
	}


}
