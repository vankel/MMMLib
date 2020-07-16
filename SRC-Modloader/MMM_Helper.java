package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class MMM_Helper {

	public static final boolean isClient;
	public static final Package fpackage;
	public static final String packegeBase;
	public static final boolean isForge = ModLoader.isModLoaded("Forge");
	public static final Minecraft mc;
	
	static {
		fpackage = ModLoader.class.getPackage();
		packegeBase = fpackage == null ? "" : fpackage.getName().concat(".");

		Minecraft lm = null;
		try {
			lm = ModLoader.getMinecraftInstance();
		} catch (Exception e) {
//			e.printStackTrace();
		} catch (Error e) {
//			e.printStackTrace();
		}
		mc = lm;
		isClient = mc != null;
		
	}
	
	/**
	 * マルチ対応用。
	 * ItemStackに情報更新を行うと、サーバー側との差異からSlotのアップデートが行われる。
	 * その際、UsingItemの更新処理が行われないため違うアイテムに持替えられたと判定される。
	 * ここでは比較用に使われるスタックリストを強制的に書換える事により対応した。
	 */
	public static void updateCheckinghSlot(Entity pEntity, ItemStack pItemstack) {
		if (pEntity instanceof EntityPlayerMP) {
			// サーバー側でのみ処理
			EntityPlayerMP lep = (EntityPlayerMP)pEntity;
			Container lctr = lep.openContainer;
			for (int li = 0; li < lctr.inventorySlots.size(); li++) {
				ItemStack lis = ((Slot)lctr.getSlot(li)).getStack(); 
				if (lis == pItemstack) {
					lctr.inventoryItemStacks.set(li, pItemstack.copy());
					break;
				}
			}
		}
	}
	
	/**
	 * Forge用追加テクスチャの設定
	 */
	public static void setForgeIcon(Object pobject) {
		if (isForge) {
			try {
				pobject.getClass().getMethod("setTextureFile", String.class).invoke(pobject, "/gui/mmmforge.png");
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Forge用クラス獲得。
	 */
	public static Class getEntityClass(BaseMod pMod, String pName) {
		if (isForge) {
			pName = pName.concat("_Forge");
		}
		return getNameOfClass(pName);
	}

	/**
	 * 名前からクラスを獲得する
	 */
	public static Class getNameOfClass(String pName) {
		if (fpackage != null) {
			pName = fpackage.getName() + "." + pName;
		}
		Class lclass = null;
		try {
			lclass = Class.forName(pName);
		} catch (Exception e) {
		}
		
		return lclass;
	}

	/**
	 * 送信用データのセット
	 */
	public static void setValue(byte[] pData, int pIndex, int pVal, int pSize) {
		for (int li = 0; li < pSize; li++) {
			pData[pIndex++] = (byte)(pVal & 0xff);
			pVal = pVal >>> 8;
		}
	}
	
	public static void setInt(byte[] pData, int pIndex, int pVal) {
    	pData[pIndex++]	= (byte)(pVal & 0xff);
    	pData[pIndex++]	= (byte)((pVal >>> 8) & 0xff);
    	pData[pIndex++]	= (byte)((pVal >>> 16) & 0xff);
    	pData[pIndex]	= (byte)((pVal >>> 24) & 0xff);
	}
	
	public static int getInt(byte[] pData, int pIndex) {
		return (pData[pIndex] & 0xff) | ((pData[pIndex + 1] & 0xff) << 8) | ((pData[pIndex + 2] & 0xff) << 16) | ((pData[pIndex + 3] & 0xff) << 24);
	}
	
	public static void setShort(byte[] pData, int pIndex, int pVal) {
		pData[pIndex++]	= (byte)(pVal & 0xff);
		pData[pIndex]	= (byte)((pVal >>> 8) & 0xff);
	}
	
	public static short getShort(byte[] pData, int pIndex) {
		return (short)((pData[pIndex] & 0xff) | ((pData[pIndex + 1] & 0xff) << 8));
	}

	protected static boolean canBlockBeSeen(Entity pEntity, int x, int y, int z, boolean toTop, boolean do1, boolean do2) {
		// ブロックの可視判定
		Vec3 vec3d = Vec3.createVectorHelper(pEntity.posX, pEntity.posY + pEntity.getEyeHeight(), pEntity.posZ);
		Vec3 vec3d1 = Vec3.createVectorHelper((double)x + 0.5D, (double)y + (toTop ? 0.9D : 0.5D), (double)z + 0.5D);
		
		MovingObjectPosition movingobjectposition = pEntity.worldObj.rayTraceBlocks_do_do(vec3d, vec3d1, do1, do2);
		if (movingobjectposition == null) {
			return false;
		}
		if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			if (movingobjectposition.blockX == MathHelper.floor_double(vec3d1.xCoord) && 
				movingobjectposition.blockY == MathHelper.floor_double(vec3d1.yCoord) &&
				movingobjectposition.blockZ == MathHelper.floor_double(vec3d1.zCoord)) {
				return true;
			}
		}
		return false;
	}

	public static boolean setPathToTile(EntityLiving pEntity, TileEntity pTarget, boolean flag) {
		// Tileまでのパスを作る
		PathNavigate lpn = pEntity.getNavigator();
		float lspeed = 0.3F;
		// 向きに合わせて距離を調整
		int i = (pTarget.yCoord == MathHelper.floor_double(pEntity.posY) && flag) ? 2 : 1;
		switch (pEntity.worldObj.getBlockMetadata(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord)) {
		case 3:
			return lpn.tryMoveToXYZ(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord + i, lspeed);
		case 2:
			return lpn.tryMoveToXYZ(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord - i, lspeed);
		case 5:
			return lpn.tryMoveToXYZ(pTarget.xCoord + 1, pTarget.yCoord, pTarget.zCoord, lspeed);
		case 4:
			return lpn.tryMoveToXYZ(pTarget.xCoord - i, pTarget.yCoord, pTarget.zCoord, lspeed);
		default:
			return lpn.tryMoveToXYZ(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord, lspeed);
		}
	}


}
