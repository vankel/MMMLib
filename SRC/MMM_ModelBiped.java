package net.minecraft.src;

import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.spec.PSource;

import org.lwjgl.opengl.GL11;

/**
 * ����̐l�^���f���u�������n�̋��ʃN���X
 */
public abstract class MMM_ModelBiped extends ModelBiped {

    public boolean isWait;
    public MMM_ModelRenderer Arms[];
    public MMM_ModelRenderer HeadMount;
    public MMM_ModelRenderer HardPoint[];
    
    public Render render;
    public Map<String, MMM_EquippedStabilizer> stabiliser;
    

    /**
     * �R���X�g���N�^�͑S�Čp�������邱��
     */
    public MMM_ModelBiped() {
		this(0.0F);
	}
    /**
     * �R���X�g���N�^�͑S�Čp�������邱��
     */
	public MMM_ModelBiped(float psize) {
		this(psize, 0.0F);
	}
    /**
     * �R���X�g���N�^�͑S�Čp�������邱��
     */
	public MMM_ModelBiped(float psize, float pyoffset) {
		super();

		heldItemLeft = 0;
        heldItemRight = 0;
        isSneak = false;
        aimedBow = false;
        
        // �n�[�h�|�C���g
        Arms = new MMM_ModelRenderer[2];
        HeadMount = new MMM_ModelRenderer(this, "HeadMount");
        
        initModel(psize, pyoffset);
	}

	/**
	 *  ���f���̏������R�[�h
	 */
	public abstract void initModel(float psize, float pyoffset);
	
	/**
	 * ���f���ؑ֎��Ɏ��s�����R�[�h
	 */
	public void changeModel(EntityLiving pentity) {
		// �J�E���^�n�̉��Z�l�A���~�b�g�l�̐ݒ�ȂǍs���\��B
	
	}
	
	/**
	 * �n�[�h�|�C���g�ɐڑ����ꂽ�A�C�e����\������
	 */
    public void renderItems(EntityLiving pEntity, Render pRender) {
    }
    
    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    	if (preRender(par1Entity, par2, par3, par4, par5, par6, par7)) {
            this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
            this.bipedHead.render(par7);
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
            this.bipedHeadwear.render(par7);
    	}
    	renderExtention(par1Entity, par2, par3, par4, par5, par6, par7);
    	renderStabilizer(par1Entity, stabiliser, par2, par3, par4, par5, par6, par7);
    }
    
    /**
     * �ʏ�̃����_�����O�O�ɌĂ΂��B
     * @return false��Ԃ��ƒʏ�̃����_�����O���X�L�b�v����B
     */
    public boolean preRender(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    	return true;
    }
    
    /**
     * �ʏ�̃����_�����O��ɌĂԁB
     * ��{�I�ɑ����i�Ȃǂ̎����^�����Ȃ��p�[�c�̕`��p�B
     */
    public void renderExtention(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {    	
    }
    
	/**
	 * �X�^�r���C�U�[�̕`��B
	 * �����ł͌Ă΂�Ȃ��̂�render���ŌĂԕK�v������܂��B
	 */
    protected void renderStabilizer(Entity pEntity, Map<String, MMM_EquippedStabilizer> pmap, float par2, float par3, float par4, float par5, float par6, float par7) {
    	// �X�^�r���C�U�[�̕`��AdoRender�̕����������H
    	if (pmap == null || pmap.isEmpty() || render == null) return;
    	
		GL11.glPushMatrix();
    	for (Entry<String, MMM_EquippedStabilizer> le : pmap.entrySet()) {
    		MMM_EquippedStabilizer les = le.getValue();
    		if (les != null && les.equipPoint != null) {
				MMM_ModelStabilizerBase lsb = les.stabilizer;
				if (lsb.isLoadAnotherTexture()) {
    				render.loadTexture(lsb.getTexture());
				}
    			les.equipPoint.loadMatrix();
    			lsb.render(this, pEntity, par2, par3, par4, par5, par6, par7);
    		}
    	}
		GL11.glPopMatrix();
    }
	
	
	// �g��
    public abstract float getHeight();

	// ����
    public abstract float getWidth();

    public boolean isItemHolder() {
    	// �A�C�e���������Ă���Ƃ��Ɏ��O�ɏo�����ǂ����B
    	return false;
    }

    public void showAllParts() {
    	// �\�����ׂ����ׂĂ̕��i
    }
    
    public int showArmorParts(int parts) {
    	// ���ʂ��Ƃ̑��b�\��
    	// 3:����
    	// 2:����
    	// 1:�r��
    	// 0:����
    	// �߂�l�͊�{ -1
    	return -1;
    }
    
    /**
     * ���f�����̃X�|�[������
     */
    public boolean getCanSpawnHere(World pworld, int px, int py, int pz, EntityLiving pentity) {
    	return true;
    }
	
}
