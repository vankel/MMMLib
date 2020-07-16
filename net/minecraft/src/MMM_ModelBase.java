package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class MMM_ModelBase {

	public Render render;

	// ModelBase�Ƃ�����x�݊�
	public int textureWidth = 64;
	public int textureHeight = 32;
	public float onGround;
	public float onGrounds[] = new float[] {0.0F, 0.0F};
	public boolean isRiding = false;
	public boolean isChild = true;
	public List<MMM_ModelRenderer> boxList = new ArrayList();
	private Map<String, TextureOffset> modelTextureMap = new HashMap<String, TextureOffset>();



	// ModelBase�݊��֐��Q

	public void render(MMM_IModelCaps pEntityCaps, float par2, float par3,
			float ticksExisted, float pheadYaw, float pheadPitch, float par7, boolean pIsRender) {
	}

	public void setRotationAngles(float par1, float par2, float pTicksExisted,
			float pHeadYaw, float pHeadPitch, float par6, MMM_IModelCaps pEntityCaps) {
	}

	public void setLivingAnimations(MMM_IModelCaps pEntityCaps, float par2, float par3, float pRenderPartialTicks) {
	}

	public MMM_ModelRenderer getRandomModelBox(Random par1Random) {
		// �G�ɖ���󂯂Ă��܂��ĂȁE�E�E
		int li = par1Random.nextInt(this.boxList.size());
		MMM_ModelRenderer lmr = (MMM_ModelRenderer)this.boxList.get(li);
		for (int lj = 0; lj < boxList.size(); lj++) {
			if (!lmr.cubeList.isEmpty()) {
				break;
			}
			// �����Ȃ�
			if (++li >= boxList.size()) {
				li = 0;
			}
			lmr = (MMM_ModelRenderer)this.boxList.get(li);
		}
		return lmr;
	}

	protected void setTextureOffset(String par1Str, int par2, int par3) {
		modelTextureMap.put(par1Str, new TextureOffset(par2, par3));
	}

	/**
	 * ��������܂���B
	 */
	public TextureOffset getTextureOffset(String par1Str) {
		// ���̂܂܂��ƈӖ��Ȃ��ȁB
		return modelTextureMap.get(par1Str);
	}


	// MathHelper�g���l���֐��Q

	public static final float mh_sin(float f) {
		f = f % 6.283185307179586F;
		f = (f < 0F) ? 360 + f : f;
		return MathHelper.sin(f);
	}

	public static final float mh_cos(float f) {
		f = f % 6.283185307179586F;
		f = (f < 0F) ? 360 + f : f;
		return MathHelper.cos(f);
	}

	public static final float mh_sqrt_float(float f) {
		return MathHelper.sqrt_float(f);
	}

	public static final float mh_sqrt_double(double d) {
		return MathHelper.sqrt_double(d);
	}

	public static final int mh_floor_float(float f) {
		return MathHelper.floor_float(f);
	}

	public static final int mh_floor_double(double d) {
		return MathHelper.floor_double(d);
	}

	public static final long mh_floor_double_long(double d) {
		return MathHelper.floor_double_long(d);
	}

	public static final float mh_abs(float f) {
		return MathHelper.abs(f);
	}

	public static final double mh_abs_max(double d, double d1) {
		return MathHelper.abs_max(d, d1);
	}

	public static final int mh_bucketInt(int i, int j) {
		return MathHelper.bucketInt(i, j);
	}

	public static final boolean mh_stringNullOrLengthZero(String s) {
		return MathHelper.stringNullOrLengthZero(s);
	}

	public static final int mh_getRandomIntegerInRange(Random random, int i, int j) {
		return MathHelper.getRandomIntegerInRange(random, i, j);
	}

}
