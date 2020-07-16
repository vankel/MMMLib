package net.minecraft.src;

import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

/**
 * �A�[�}�[�̓�d�`��p�N���X�B
 */
public class MMM_ModelArmors extends ModelBase implements MMM_IModelCaps {

	public RenderLiving renderLiving;
	public MMM_ModelBiped modelArmorOuter;
	public MMM_ModelBiped modelArmorInner;
	/**
	 * ���ʖ��̃A�[�}�[�e�N�X�`���̎w��B
	 * �O���B
	 */
	public String[] textureOuter;
	/**
	 * ���ʖ��̃A�[�}�[�e�N�X�`���̎w��B
	 * �����B
	 */
	public String[] textureInner;
	/**
	 * �`�悳���A�[�}�[�̕��ʁB
	 * shouldRenderPass�Ƃ��Ŏw�肷��B
	 */
	public int renderParts;
	public boolean isAlphablend;
	public boolean isModelAlphablend;


	public MMM_ModelArmors(RenderLiving pRender) {
		renderLiving = pRender;
		renderParts = 0;
	}

	@Override
	public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4) {
		if (modelArmorOuter != null) {
			modelArmorOuter.setLivingAnimations(par1EntityLiving, par2, par3, par4);
		}
		modelArmorInner.setLivingAnimations(par1EntityLiving, par2, par3, par4);
		isAlphablend = true;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (isAlphablend) {
			if (isModelAlphablend) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else {
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
		if (modelArmorOuter != null) {
			renderLiving.loadTexture(textureOuter[renderParts]);
			modelArmorOuter.render(par1Entity, par2, par3, par4, par5, par6, par7);
		}
		if (textureInner != null) {
			renderLiving.loadTexture(textureInner[renderParts]);
		}
		modelArmorInner.render(par1Entity, par2, par3, par4, par5, par6, par7);
		isAlphablend = false;
	}

	@Override
	public ModelRenderer func_85181_a(Random par1Random) {
		return modelArmorInner.func_85181_a(par1Random);
	}

	@Override
	public TextureOffset getTextureOffset(String par1Str) {
		return modelArmorInner.getTextureOffset(par1Str);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity par7Entity) {
		if (modelArmorOuter != null) {
			modelArmorOuter.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		}
		modelArmorInner.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}

	public void showArmorParts(int pIndex) {
		if (modelArmorOuter != null) {
			modelArmorOuter.showArmorParts(pIndex);
		}
		modelArmorInner.showArmorParts(pIndex);
	}

	@Override
	public Map<String, Integer> getModelCaps() {
		return modelArmorInner.getModelCaps();
	}

	@Override
	public Object getCapsValue(int pIndex) {
		return modelArmorInner.getCapsValue(pIndex);
	}
	@Override
	public Object getCapsValue(String pCapsName) {
		return modelArmorInner.getCapsValue(pCapsName);
	}
	@Override
	public int getCapsValueInt(int pIndex) {
		return modelArmorInner.getCapsValueInt(pIndex);
	}
	@Override
	public float getCapsValueFloat(int pIndex) {
		return modelArmorInner.getCapsValueFloat(pIndex);
	}
	@Override
	public double getCapsValueDouble(int pIndex) {
		return modelArmorInner.getCapsValueDouble(pIndex);
	}
	@Override
	public boolean getCapsValueBoolean(int pIndex) {
		return modelArmorInner.getCapsValueBoolean(pIndex);
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		if (modelArmorOuter != null) {
			modelArmorOuter.setCapsValue(pIndex, pArg);
		}
		return modelArmorInner.setCapsValue(pIndex, pArg);
	}
	@Override
	public boolean setCapsValue(String pCapsName, Object... pArg) {
		if (modelArmorOuter != null) {
			modelArmorOuter.setCapsValue(pCapsName, pArg);
		}
		return modelArmorInner.setCapsValue(pCapsName, pArg);
	}

}
