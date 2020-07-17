package net.minecraft.src;

import static net.minecraft.src.MMM_IModelCaps.*;
import java.lang.reflect.Constructor;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class MMM_ModelRenderer {

	// ModelRenderer�݊��ϐ��Q
	public float textureWidth;
	public float textureHeight;
	private int textureOffsetX;
	private int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	protected boolean compiled;
	protected int displayList;
	public boolean mirror;
	public boolean showModel;
	public boolean isHidden;
	/**
	 * �p�[�c�̐e�q�֌W�ɍ��E���ꂸ�ɕ`�悷�邩�����߂�B
	 * �A�[�}�[�̕\���ȂǂɎg���B
	 */
	public boolean isRendering;
	public List<MMM_ModelBoxBase> cubeList;
	public List<MMM_ModelRenderer> childModels;
	public final String boxName;
	protected MMM_ModelBase baseModel;
	public MMM_ModelRenderer pearent;
	public float offsetX;
	public float offsetY;
	public float offsetZ;
	public float scaleX;
	public float scaleY;
	public float scaleZ;
	
	
//	public static final float radFactor = 57.295779513082320876798154814105F;
	public static final float radFactor = 180F / (float)Math.PI;
//	public static final float degFactor = 0.01745329251994329576923690768489F;
	public static final float degFactor = (float)Math.PI / 180F;
	
	// SmartMoving�ɍ��킹�邽�߂ɖ��̂̕ύX�����邩������܂���B
	public int rotatePriority;
	public static final int RotXYZ = 0;
	public static final int RotXZY = 1;
	public static final int RotYXZ = 2;
	public static final int RotYZX = 3;
	public static final int RotZXY = 4;
	public static final int RotZYX = 5;
	
//	public static final int ModeEquip = 0x000;
//	public static final int ModeInventory = 0x001;
//	public static final int ModeItemStack = 0x002;
//	public static final int ModeParts = 0x010;
	protected ItemStack itemstack;
	
	public boolean adjust;
	public FloatBuffer matrix;
	public boolean isInvertX;




	public MMM_ModelRenderer(MMM_ModelBase pModelBase, String pName) {
		textureWidth = 64.0F;
		textureHeight = 32.0F;
		compiled = false;
		displayList = 0;
		mirror = false;
		showModel = true;
		isHidden = false;
		isRendering = true;
		cubeList = new ArrayList<MMM_ModelBoxBase>();
		baseModel = pModelBase;
		pModelBase.boxList.add(this);
		boxName = pName;
		setTextureSize(pModelBase.textureWidth, pModelBase.textureHeight);
		
		rotatePriority = RotXYZ;
		itemstack = null;
		adjust = true;
		matrix = BufferUtils.createFloatBuffer(16);
		isInvertX = false;
		
		scaleX = 1.0F;
		scaleY = 1.0F;
		scaleZ = 1.0F;
		
		pearent = null;
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase, int px, int py) {
		this(pModelBase, null);
		setTextureOffset(px, py);
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase) {
		this(pModelBase, (String)null);
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase, int px, int py, float pScaleX, float pScaleY, float pScaleZ) {
		this(pModelBase, px, py);
		this.scaleX = pScaleX;
		this.scaleY = pScaleY;
		this.scaleZ = pScaleZ;
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase, float pScaleX, float pScaleY, float pScaleZ) {
		this(pModelBase);
		this.scaleX = pScaleX;
		this.scaleY = pScaleY;
		this.scaleZ = pScaleZ;
	}

	// ModelRenderer�݊��֐��Q

	public void addChild(MMM_ModelRenderer pModelRenderer) {
		if (childModels == null) {
			childModels = new ArrayList<MMM_ModelRenderer>();
		}
		childModels.add(pModelRenderer);
		pModelRenderer.pearent = this;
	}

	public MMM_ModelRenderer setTextureOffset(int pOffsetX, int pOffsetY) {
		textureOffsetX = pOffsetX;
		textureOffsetY = pOffsetY;
		return this;
	}

	public MMM_ModelRenderer addBox(String pName, float pX, float pY, float pZ,
			int pWidth, int pHeight, int pDepth) {
		addParts(MMM_ModelBox.class, pName, pX, pY, pZ, pWidth, pHeight, pDepth, 0.0F);
		return this;
	}

	public MMM_ModelRenderer addBox(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pDepth) {
		addParts(MMM_ModelBox.class, pX, pY, pZ, pWidth, pHeight, pDepth, 0.0F);
		return this;
	}

	public MMM_ModelRenderer addBox(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pDepth, float pSizeAdjust) {
		addParts(MMM_ModelBox.class, pX, pY, pZ, pWidth, pHeight, pDepth, pSizeAdjust);
		return this;
	}

	public MMM_ModelRenderer setRotationPoint(float pX, float pY, float pZ) {
		rotationPointX = pX;
		rotationPointY = pY;
		rotationPointZ = pZ;
		return this;
	}

	// TODO:�A�b�v�f�[�g���͂������`�F�b�N���邱��
	public void render(float par1, boolean pIsRender) {
		if (isHidden) return;
		if (!showModel) return;
		
		if (!compiled) {
			compileDisplayList(par1);
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(offsetX, offsetY, offsetZ);
		
		if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F) {
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
		}
		if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F) {
			setRotation();
		}
		renderObject(par1, pIsRender);
		GL11.glPopMatrix();
	}
	public void render(float par1) {
		render(par1, true);
	}

	public void renderWithRotation(float par1) {
		if (isHidden) return;
		if (!showModel) return;
		
		if (!compiled) {
			compileDisplayList(par1);
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
		
		setRotation();
		
		GL11.glCallList(displayList);
		GL11.glPopMatrix();
	}

	public void postRender(float par1) {
		if (isHidden) return;
		if (!showModel) return;
		
		if (!compiled) {
			compileDisplayList(par1);
		}
		
		if (pearent != null) {
			pearent.postRender(par1);
		}
		
		GL11.glTranslatef(offsetX, offsetY, offsetZ);
		
		if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F) {
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
		}
		if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F) {
			setRotation();
		}
	}

	protected void compileDisplayList(float par1) {
		displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(displayList, GL11.GL_COMPILE);
		Tessellator tessellator = Tessellator.instance;
		
		for (int i = 0; i < cubeList.size(); i++) {
			cubeList.get(i).render(tessellator, par1);
		}
		
		GL11.glEndList();
		compiled = true;
	}

	public MMM_ModelRenderer setTextureSize(int pWidth, int pHeight) {
		textureWidth = (float)pWidth;
		textureHeight = (float)pHeight;
		return this;
	}


	// �Ǝ��ǉ���

	/**
	 * ModelBox�p���̓Ǝ��I�u�W�F�N�g�ǉ��p
	 */
	public MMM_ModelRenderer addCubeList(MMM_ModelBoxBase pModelBoxBase) {
		cubeList.add(pModelBoxBase);
		return this;
	}

	protected MMM_ModelBoxBase getModelBoxBase(Class pModelBoxBase, Object ... pArg) {
		try {
			Constructor<MMM_ModelBoxBase> lconstructor =
					pModelBoxBase.getConstructor(MMM_ModelRenderer.class, Object[].class);
			return lconstructor.newInstance(this, pArg);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Object[] getArg(Object ... pArg) {
		Object lobject[] = new Object[pArg.length + 2];
		lobject[0] = textureOffsetX;
		lobject[1] = textureOffsetY;
		for (int li = 0; li < pArg.length; li++) {
			lobject[2 + li] = pArg[li];
		}
		return lobject;
	}

	public MMM_ModelRenderer addParts(Class pModelBoxBase, String pName, Object ... pArg) {
		pName = (new StringBuilder()).append(boxName).append(".").append(pName).toString();
		TextureOffset ltextureoffset = baseModel.getTextureOffset(pName);
		setTextureOffset(ltextureoffset.textureOffsetX, ltextureoffset.textureOffsetY);
		addCubeList(getModelBoxBase(pModelBoxBase, getArg(pArg)).setBoxName(pName));
		return this;
	}

	public MMM_ModelRenderer addParts(Class pModelBoxBase, Object ... pArg) {
		addCubeList(getModelBoxBase(pModelBoxBase, getArg(pArg)));
		return this;
	}

	/**
	 * �����Ńe�N�X�`���̍��W���w�肷�鎞�Ɏg���܂��B
	 * �R���X�g���N�^�ւ��̂܂ܒl��n���܂��B
	 */
	public MMM_ModelRenderer addPartsTexture(Class pModelBoxBase, String pName, Object ... pArg) {
		pName = (new StringBuilder()).append(boxName).append(".").append(pName).toString();
		addCubeList(getModelBoxBase(pModelBoxBase, pArg).setBoxName(pName));
		return this;
	}

	/**
	 * �����Ńe�N�X�`���̍��W���w�肷�鎞�Ɏg���܂��B
	 * �R���X�g���N�^�ւ��̂܂ܒl��n���܂��B
	 */
	public MMM_ModelRenderer addPartsTexture(Class pModelBoxBase, Object ... pArg) {
		addCubeList(getModelBoxBase(pModelBoxBase, pArg));
		return this;
	}


	public MMM_ModelRenderer addPlate(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pFacePlane) {
		addParts(MMM_ModelPlate.class, pX, pY, pZ, pWidth, pHeight, pFacePlane, 0.0F);
		return this;
	}

	public MMM_ModelRenderer addPlate(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pFacePlane, float pSizeAdjust) {
		addParts(MMM_ModelPlate.class, pX, pY, pZ, pWidth, pHeight, pFacePlane, pSizeAdjust);
		return this;
	}

	public MMM_ModelRenderer addPlate(String pName, float pX, float pY, float pZ,
			int pWidth, int pHeight, int pFacePlane) {
		addParts(MMM_ModelPlate.class, pName, pX, pY, pZ, pWidth, pHeight, pFacePlane, 0.0F);
		return this;
	}

	/**
	 * �`��p�̃{�b�N�X�A�q�����N���A����
	 */
	public void clearCubeList() {
		cubeList.clear();
		compiled = false;
		if (childModels != null) {
			childModels.clear();
		}
	}

	// TODO:���̂�����͗v�C��
	public boolean renderItems(MMM_ModelMultiBase pModelMulti, MMM_IModelCaps pEntityCaps, boolean pRealBlock, int pIndex) {
		ItemStack[] litemstacks = (ItemStack[])MMM_ModelCapsHelper.getCapsValue(pEntityCaps, caps_Items);
		if (litemstacks == null) return false;
		EnumAction[] lactions = (EnumAction[])MMM_ModelCapsHelper.getCapsValue(pEntityCaps, caps_Actions);
		EntityLivingBase lentity = (EntityLivingBase)pEntityCaps.getCapsValue(caps_Entity);
		
		renderItems(lentity, pModelMulti.render, pRealBlock, lactions[pIndex], litemstacks[pIndex]);
		return true;
	}

	public void renderItemsHead(MMM_ModelMultiBase pModelMulti, MMM_IModelCaps pEntityCaps) {
		ItemStack lis = (ItemStack)pEntityCaps.getCapsValue(caps_HeadMount);
		EntityLivingBase lentity = (EntityLivingBase)pEntityCaps.getCapsValue(caps_Entity);
		
		renderItems(lentity, pModelMulti.render, true, null, lis);
	}

	protected void renderItems(EntityLivingBase pEntityLiving, Render pRender,
			boolean pRealBlock, EnumAction pAction, ItemStack pItemStack) {
		itemstack = pItemStack;
		renderItems(pEntityLiving, pRender, pRealBlock, pAction);
	}

	protected void renderItems(EntityLivingBase pEntityLiving, Render pRender, boolean pRealBlock, EnumAction pAction) {
		if (itemstack == null) return;
		
		// �A�C�e���̃����_�����O
		GL11.glPushMatrix();
		
		// �A�C�e���̎�ނɂ��\���ʒu�̕␳
		if (adjust) {
			// GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			
			if (pRealBlock && (itemstack.getItem() instanceof ItemBlock)) {
				float f2 = 0.625F;
				GL11.glScalef(f2, -f2, -f2);
				GL11.glRotatef(270F, 0F, 1F, 0);
			} else if (pRealBlock && (itemstack.getItem() instanceof ItemSkull)) {
				float f2 = 1.0625F;
				GL11.glScalef(f2, -f2, -f2);
			} else {
				float var6;
				if (itemstack.itemID < 256
						&& RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())) {
					var6 = 0.5F;
					// GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
					GL11.glTranslatef(0.0F, 0.1875F, -0.2125F);
					var6 *= 0.75F;
					GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(var6, -var6, var6);
				} else if (itemstack.getItem() instanceof ItemBow) {
					var6 = 0.625F;
					GL11.glTranslatef(-0.05F, 0.125F, 0.3125F);
					GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(var6, -var6, var6);
					GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				} else if (Item.itemsList[itemstack.itemID].isFull3D()) {
					var6 = 0.625F;
					
					if (Item.itemsList[itemstack.itemID]
							.shouldRotateAroundWhenRendering()) {
						GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(0.0F, -0.125F, 0.0F);
					}
					
					if (pAction == EnumAction.block) {
						GL11.glTranslatef(0.05F, 0.0F, -0.1F);
						GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
						GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
					}
					
					GL11.glTranslatef(0.0F, 0.1875F, 0.1F);
					GL11.glScalef(var6, -var6, var6);
					GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				} else {
					var6 = 0.375F;
					GL11.glTranslatef(0.15F, 0.15F, -0.05F);
					// GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
					GL11.glScalef(var6, var6, var6);
					GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
				}
			}
		}
		
		if (pRealBlock && itemstack.getItem() instanceof ItemSkull) {
			String lsowner = "";
			if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("SkullOwner")) {
				lsowner = itemstack.getTagCompound().getString("SkullOwner");
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, -0.25F, -0.5F, 1, 180.0F,
					itemstack.getItemDamage(), lsowner);
		} else if (pRealBlock && itemstack.getItem() instanceof ItemBlock) {
//			pRender.loadTexture("/terrain.png");
//			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_CULL_FACE);
			pRender.renderBlocks.renderBlockAsItem(
					Block.blocksList[itemstack.itemID],
					itemstack.getItemDamage(), 1.0F);
			GL11.glDisable(GL11.GL_CULL_FACE);
		} else {
			// �A�C�e���ɐF�t��
//			pRender.loadTexture("/gui/items.png");
			for (int j = 0; j <= (itemstack.getItem()
					.requiresMultipleRenderPasses() ? 1 : 0); j++) {
				int k = itemstack.getItem().getColorFromItemStack(itemstack, j);
				float f15 = (float) (k >> 16 & 0xff) / 255F;
				float f17 = (float) (k >> 8 & 0xff) / 255F;
				float f19 = (float) (k & 0xff) / 255F;
				GL11.glColor4f(f15, f17, f19, 1.0F);
				pRender.renderManager.itemRenderer.renderItem(pEntityLiving, itemstack, j);
			}
		}
		
		GL11.glPopMatrix();
	}

	/**
	 *  ��]�ϊ����s���������w��B
	 * @param pValue
	 * Rot???���w�肷��
	 */
	public void setRotatePriority(int pValue) {
		rotatePriority = pValue;
	}

	/**
	 * �������s�p�A���W�ϊ���
	 */
	protected void setRotation() {
		// �ϊ����ʂ̐ݒ�
		switch (rotatePriority) {
		case RotXYZ:
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			break;
		case RotXZY:
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			break;
		case RotYXZ:
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			break;
		case RotYZX:
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			break;
		case RotZXY:
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			break;
		case RotZYX:
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			break;
		}
	}

	/**
	 * �������s�p�A�����_�����O�����B
	 */
	protected void renderObject(float par1, boolean pRendering) {
		// �����_�����O�A���Ǝq����
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrix);
		if (pRendering && isRendering) {
			GL11.glPushMatrix();
			GL11.glScalef(scaleX, scaleY, scaleZ);
			GL11.glCallList(displayList);
			GL11.glPopMatrix();
		}
		
		if (childModels != null) {
			for (int li = 0; li < childModels.size(); li++) {
				childModels.get(li).render(par1, pRendering);
			}
		}
	}

	/**
	 * �p�[�c�`�掞�_�̃}�g���N�X��ݒ肷��B ����ȑO�ɐݒ肳�ꂽ�}�g���N�X�͔j�������B
	 */
	public MMM_ModelRenderer loadMatrix() {
		GL11.glLoadMatrix(matrix);
		if (isInvertX) {
			GL11.glScalef(-1F, 1F, 1F);
		}
		return this;
	}


	// �Q�b�^�[�A�Z�b�^�[

	public boolean getMirror() {
		return mirror;
	}

	public MMM_ModelRenderer setMirror(boolean flag) {
		mirror = flag;
		return this;
	}

	public boolean getVisible() {
		return showModel;
	}

	public void setVisible(boolean flag) {
		showModel = flag;
	}


	// Deg�t���͊p�x�w�肪�x���@

	public float getRotateAngleX() {
		return rotateAngleX;
	}

	public float getRotateAngleDegX() {
		return rotateAngleX * radFactor;
	}

	public float setRotateAngleX(float value) {
		return rotateAngleX = value;
	}

	public float setRotateAngleDegX(float value) {
		return rotateAngleX = value * degFactor;
	}

	public float addRotateAngleX(float value) {
		return rotateAngleX += value;
	}

	public float addRotateAngleDegX(float value) {
		return rotateAngleX += value * degFactor;
	}

	public float getRotateAngleY() {
		return rotateAngleY;
	}

	public float getRotateAngleDegY() {
		return rotateAngleY * radFactor;
	}

	public float setRotateAngleY(float value) {
		return rotateAngleY = value;
	}

	public float setRotateAngleDegY(float value) {
		return rotateAngleY = value * degFactor;
	}

	public float addRotateAngleY(float value) {
		return rotateAngleY += value;
	}

	public float addRotateAngleDegY(float value) {
		return rotateAngleY += value * degFactor;
	}

	public float getRotateAngleZ() {
		return rotateAngleZ;
	}

	public float getRotateAngleDegZ() {
		return rotateAngleZ * radFactor;
	}

	public float setRotateAngleZ(float value) {
		return rotateAngleZ = value;
	}

	public float setRotateAngleDegZ(float value) {
		return rotateAngleZ = value * degFactor;
	}

	public float addRotateAngleZ(float value) {
		return rotateAngleZ += value;
	}

	public float addRotateAngleDegZ(float value) {
		return rotateAngleZ += value * degFactor;
	}

	public MMM_ModelRenderer setRotateAngle(float x, float y, float z) {
		rotateAngleX = x;
		rotateAngleY = y;
		rotateAngleZ = z;
		return this;
	}

	public MMM_ModelRenderer setRotateAngleDeg(float x, float y, float z) {
		rotateAngleX = x * degFactor;
		rotateAngleY = y * degFactor;
		rotateAngleZ = z * degFactor;
		return this;
	}

	public float getRotationPointX() {
		return rotationPointX;
	}

	public float setRotationPointX(float value) {
		return rotationPointX = value;
	}

	public float addRotationPointX(float value) {
		return rotationPointX += value;
	}

	public float getRotationPointY() {
		return rotationPointY;
	}

	public float setRotationPointY(float value) {
		return rotationPointY = value;
	}

	public float addRotationPointY(float value) {
		return rotationPointY += value;
	}

	public float getRotationPointZ() {
		return rotationPointZ;
	}

	public float setRotationPointZ(float value) {
		return rotationPointZ = value;
	}

	public float addRotationPointZ(float value) {
		return rotationPointZ += value;
	}

	public MMM_ModelRenderer setScale(float pX, float pY, float pZ) {
		scaleX = pX;
		scaleY = pY;
		scaleZ = pZ;
		return this;
	}

	public float setScaleX(float pValue) {
		return scaleX = pValue;
	}

	public float setScaleY(float pValue) {
		return scaleY = pValue;
	}

	public float setScaleZ(float pValue) {
		return scaleZ = pValue;
	}

}
