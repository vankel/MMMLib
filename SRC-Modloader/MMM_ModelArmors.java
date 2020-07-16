package net.minecraft.src;

/**
 * アーマーの二重描画用クラス。
 */
public class MMM_ModelArmors extends ModelBase {
	
	public RenderLiving renderLiving;
	public ModelBase modelArmorOuter;
    public ModelBase modelArmorInner;
    /**
     * 部位毎のアーマーテクスチャの指定。
     * 外側。
     */
    public String[] textureOuter;
    /**
     * 部位毎のアーマーテクスチャの指定。
     * 内側。
     */
    public String[] textureInner;
    /**
     * 描画されるアーマーの部位。
     * shouldRenderPassとかで指定する。
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
