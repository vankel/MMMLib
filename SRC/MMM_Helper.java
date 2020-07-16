package net.minecraft.src;

public class MMM_Helper {

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
		if (mod_MMM_MMMLib.isForge) {
			try {
				pobject.getClass().getMethod("setTextureFile", String.class).invoke(pobject, "/gui/mmmforge.png");
			} catch (Exception e) {
			}
		}
	}

	public static boolean isForge() {
		return mod_MMM_MMMLib.isForge;
	}

}
