package net.minecraft.src;

public abstract class MMM_ModelStabilizerBase extends ModelBase {
	
	public MMM_ModelStabilizerBase() {
	}

	/**
	 * 使用されるテクスチャを返す。
	 */
	public String getTexture() {
		return "";
	}
	
	/**
	 * そのハードポイントに装備可能かどうかを返す。
	 * pName:ハードポイントの識別名称。
	 */
	public boolean checkEquipment(String pName) {
		return true;
	}
	
	/**
	 * パーツの名称。
	 */
	public String getName() {
		return "";
	}
	/**
	 * メイドさんのテクスチャをそのまま使わずに、違うテクスチャを使うか？
	 */
	public boolean isLoadAnotherTexture() {
		return false;
	}
	
	/**
	 * 初期化時に実行される
	 */
	public void init(MMM_EquippedStabilizer pequipped) {
		// 変数などを定義する
	}
	
}
