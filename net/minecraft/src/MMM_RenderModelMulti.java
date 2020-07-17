package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class MMM_RenderModelMulti extends RenderLiving {

	public MMM_ModelBaseSolo modelMain;
	public MMM_ModelBaseDuo modelFATT;
	public MMM_IModelCaps fcaps;



	public MMM_RenderModelMulti(float pShadowSize) {
		super(null, pShadowSize);
		modelFATT = new MMM_ModelBaseDuo(this);
		modelFATT.isModelAlphablend = mod_MMM_MMMLib.cfg_isModelAlphaBlend;
		modelFATT.isRendering = true;
		modelMain = new MMM_ModelBaseSolo(this);
		modelMain.isModelAlphablend = mod_MMM_MMMLib.cfg_isModelAlphaBlend;
		modelMain.capsLink = modelFATT;
		mainModel = modelMain;
		setRenderPassModel(modelFATT);
	}

	protected int showArmorParts(EntityLivingBase par1EntityLiving, int par2, float par3) {
		// �A�[�}�[�̕\���ݒ�
		modelFATT.renderParts = par2;
		ItemStack is = par1EntityLiving.getCurrentItemOrArmor(par2 + 1);
		if (is != null && is.stackSize > 0) {
			modelFATT.showArmorParts(par2);
			return is.isItemEnchanted() ? 15 : 1;
		}
		
		return -1;
	}
	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3) {
		return showArmorParts((EntityLivingBase)par1EntityLiving, par2, par3);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		Float lscale = (Float)modelMain.getCapsValue(MMM_IModelCaps.caps_ScaleFactor);
		if (lscale != null) {
			GL11.glScalef(lscale, lscale, lscale);
		}
	}

	public void setModelValues(EntityLivingBase par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9, MMM_IModelCaps pEntityCaps) {
		if (par1EntityLiving instanceof MMM_ITextureEntity) {
			MMM_ITextureEntity ltentity = (MMM_ITextureEntity)par1EntityLiving;
			modelMain.model = ltentity.getTextureData().textureModel[0];
			modelFATT.modelInner = ltentity.getTextureData().textureModel[1];
			modelFATT.modelOuter = ltentity.getTextureData().textureModel[2];
//			modelMain.model = ((MMM_TextureBox)ltentity.getTextureBox()[0]).models[0];
			modelMain.textures = ltentity.getTextures(0);
//			modelFATT.modelInner = ((MMM_TextureBox)ltentity.getTextureBox()[1]).models[1];
//			modelFATT.modelOuter = ((MMM_TextureBox)ltentity.getTextureBox()[1]).models[2];
			modelFATT.textureInner = ltentity.getTextures(1);
			modelFATT.textureOuter = ltentity.getTextures(2);
			modelFATT.textureInnerLight = ltentity.getTextures(3);
			modelFATT.textureOuterLight = ltentity.getTextures(4);
		}
		modelMain.setEntityCaps(pEntityCaps);
		modelFATT.setEntityCaps(pEntityCaps);
		modelMain.setRender(this);
		modelFATT.setRender(this);
		modelMain.showAllParts();
		modelFATT.showAllParts();
		modelMain.isAlphablend = true;
		modelFATT.isAlphablend = true;
		modelMain.lighting = modelFATT.lighting = par1EntityLiving.getBrightnessForRender(par8);
		
		modelMain.setCapsValue(MMM_IModelCaps.caps_heldItemLeft, (Integer)0);
		modelMain.setCapsValue(MMM_IModelCaps.caps_heldItemRight, (Integer)0);
		modelMain.setCapsValue(MMM_IModelCaps.caps_onGround, renderSwingProgress(par1EntityLiving, par9));
		modelMain.setCapsValue(MMM_IModelCaps.caps_isRiding, par1EntityLiving.isRiding());
		modelMain.setCapsValue(MMM_IModelCaps.caps_isSneak, par1EntityLiving.isSneaking());
		modelMain.setCapsValue(MMM_IModelCaps.caps_aimedBow, false);
		modelMain.setCapsValue(MMM_IModelCaps.caps_isWait, false);
		modelMain.setCapsValue(MMM_IModelCaps.caps_isChild, par1EntityLiving.isChild());
		modelMain.setCapsValue(MMM_IModelCaps.caps_entityIdFactor, 0F);
		modelMain.setCapsValue(MMM_IModelCaps.caps_ticksExisted, par1EntityLiving.ticksExisted);
	}

//	public void renderModelMulti(EntityLivingBase par1EntityLiving, double par2,
	public void renderModelMulti(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9, MMM_IModelCaps pEntityCaps) {
		setModelValues(par1EntityLiving, par2, par4, par6, par8, par9, pEntityCaps);
		// TODO:1.6.2-MCP805 �Ȃ����ςȂƂ��ɔ��Ń��[�v����
//		super.func_130000_a(par1EntityLiving, par2, par4, par6, par8, par9);
		super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9) {
		fcaps = (MMM_IModelCaps)par1EntityLiving;
		renderModelMulti(par1EntityLiving, par2, par4, par6, par8, par9, fcaps);
	}

	@Override
	protected void func_110827_b(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9) {
		// ��̈ʒu�̃I�t�Z�b�g
		// TODO�FMCP-804�΍�
		float lf = 0F;
		if (modelMain.model != null && fcaps != null) {
			lf = modelMain.model.getLeashOffset(fcaps);
		}
		super.func_110827_b(par1EntityLiving, par2, par4 - lf, par6, par8, par9);
	}

	@Override
	protected void renderModel(EntityLivingBase par1EntityLiving, float par2,
			float par3, float par4, float par5, float par6, float par7) {
		if (!par1EntityLiving.isInvisible()) {
			modelMain.setArmorRendering(true);
		} else {
			modelMain.setArmorRendering(false);
		}
		// �A�C�e���̃����_�����O�ʒu���l�����邽��render���ĂԕK�v������
		mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2) {
		// �n�[�h�|�C���g�̕`��
		modelMain.renderItems(par1EntityLiving, this);
		renderArrowsStuckInEntity(par1EntityLiving, par2);
	}

	@Override
	protected void renderArrowsStuckInEntity(EntityLivingBase par1EntityLiving, float par2) {
		MMM_Client.renderArrowsStuckInEntity(par1EntityLiving, par2, this, modelMain.model);
	}

	@Override
	protected ResourceLocation func_110775_a(Entity var1) {
		// �e�N�X�`�����\�[�X��Ԃ��Ƃ��낾����ǁA��{�I�Ɏg�p���Ȃ��B
		return null;
	}

}
