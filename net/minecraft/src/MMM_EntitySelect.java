package net.minecraft.src;

import java.util.Map;

public class MMM_EntitySelect extends EntityLiving implements MMM_IModelCaps, MMM_ITextureEntity {

	public int color;
	public int textureIndex[] = new int[] { 0, 0 };
	public MMM_TextureBoxBase textureBox[] = new MMM_TextureBoxBase[] {
			new MMM_TextureBox(), new MMM_TextureBox() };
	public boolean contract;
	public String textures[] = new String[2];
	public String texturesInner[] = new String[] { "", "" , "" , "" };
	public String texturesOuter[] = new String[] { "", "" , "" , "" };
	protected MMM_EntityCaps entityCaps;



	public MMM_EntitySelect(World par1World) {
		super(par1World);
		textures[0] = super.texture;
		entityCaps = new MMM_EntityCaps(this);
	}

	@Override
	protected void entityInit() {
		// Select�p������A����ʂɂ���񂯂ǂȁB
		super.entityInit();
		// color
		dataWatcher.addObject(19, Integer.valueOf(0));
		// 20:�I���e�N�X�`���C���f�b�N�X
		dataWatcher.addObject(20, Integer.valueOf(0));
	}

	@Override
	public String getTexture() {
		return textures[0];
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public float getBrightness(float par1) {
		return worldObj == null ? 0.0F : super.getBrightness(par1);
	}

	// EntityCaps

	@Override
	public Map<String, Integer> getModelCaps() {
		return entityCaps.getModelCaps();
	}

	@Override
	public Object getCapsValue(int pIndex, Object... pArg) {
		return entityCaps.getCapsValue(pIndex, pArg);
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		return entityCaps.setCapsValue(pIndex, pArg);
	}

	// TextureEntity

	@Override
	public void setTexturePackIndex(int pColor, int[] pIndex) {
		// Server
		color = pColor;
		textureIndex[0] = pIndex[0];
		textureIndex[1] = pIndex[1];
		dataWatcher.updateObject(20, (Integer.valueOf(textureIndex[0]) & 0xffff) | ((Integer.valueOf(textureIndex[1]) & 0xffff) << 16));
		textureBox[0] = MMM_TextureManager.instance.getTextureBoxServer(textureIndex[0]);
		textureBox[1] = MMM_TextureManager.instance.getTextureBoxServer(textureIndex[1]);
		// �T�C�Y�̕ύX
		setSize(textureBox[0].getWidth(entityCaps), textureBox[0].getHeight(entityCaps));
	}

	@Override
	public void setTexturePackName(MMM_TextureBox[] pTextureBox) {
		// Client
		textureBox[0] = pTextureBox[0];
		textureBox[1] = pTextureBox[1];
		setTextureNames();
		// �g���ύX�p
		setSize(textureBox[0].getWidth(null), textureBox[0].getHeight(null));
		setPosition(posX, posY, posZ);
		// ���f���̏�����
		((MMM_TextureBox)textureBox[0]).models[0].setCapsValue(MMM_IModelCaps.caps_changeModel, this);
	}

	/**
	 * �e�N�X�`���̃t�@�C�������l��
	 */
	protected void setTextureNames() {
		textures[0] = ((MMM_TextureBox)textureBox[0]).getTextureName(color + (contract ? 0 : MMM_TextureManager.tx_wild));
		texturesInner[0] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(true, getCurrentArmor(0));
		texturesInner[1] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(true, getCurrentArmor(1));
		texturesInner[2] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(true, getCurrentArmor(2));
		texturesInner[3] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(true, getCurrentArmor(3));
		texturesOuter[0] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(false, getCurrentArmor(0));
		texturesOuter[1] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(false, getCurrentArmor(1));
		texturesOuter[2] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(false, getCurrentArmor(2));
		texturesOuter[3] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(false, getCurrentArmor(3));
	}

	@Override
	public void setColor(int pColor) {
		color = pColor;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void setContract(boolean pContract) {
		contract = pContract;
	}

	@Override
	public boolean isContract() {
		return contract;
	}

	@Override
	public void setTextureBox(MMM_TextureBoxBase[] pTextureBox) {
		textureBox = pTextureBox;
	}

	@Override
	public MMM_TextureBoxBase[] getTextureBox() {
		return textureBox;
	}

	@Override
	public void setTextures(int pIndex, String[] pNames) {
		switch (pIndex) {
		case 0:
			textures = pNames;
		case 1:
			texturesInner = pNames;
		case 2:
			texturesOuter = pNames;
		}
	}

	@Override
	public String[] getTextures(int pIndex) {
		switch (pIndex) {
		case 0:
			return textures;
		case 1:
			return texturesInner;
		case 2:
			return texturesOuter;
		}
		return null;
	}

	@Override
	public void setTextureIndex(int[] pTextureIndex) {
		textureIndex = pTextureIndex;
	}

	@Override
	public int[] getTextureIndex() {
		return textureIndex;
	}

}
