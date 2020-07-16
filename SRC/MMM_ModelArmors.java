package net.minecraft.src;

/**
 * �A�[�}�[�̓�d�`��p�N���X�B
 */
public class MMM_ModelArmors extends ModelBase {
	
	public RenderLiving renderLiving;
	public ModelBase modelArmorOuter;
    public ModelBase modelArmorInner;
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

    
    public MMM_ModelArmors(RenderLiving pRender) {
    	renderLiving = pRender;
    	renderParts = 0;
	}
        
    @Override
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4) {
    	modelArmorOuter.setLivingAnimations(par1EntityLiving, par2, par3, par4);
    	modelArmorInner.setLivingAnimations(par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    	renderLiving.loadTexture(textureOuter[renderParts]);
    	modelArmorOuter.render(par1Entity, par2, par3, par4, par5, par6, par7);
    	renderLiving.loadTexture(textureInner[renderParts]);
    	modelArmorInner.render(par1Entity, par2, par3, par4, par5, par6, par7);
    }
    
}
