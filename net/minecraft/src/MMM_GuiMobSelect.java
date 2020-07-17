package net.minecraft.src;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class MMM_GuiMobSelect extends GuiScreen {

	public Map<String, Entity> entityMap;
	public static Map<Class, String> entityMapClass = new HashMap<Class, String>();
	public static List<String> exclusionList = new ArrayList<String>();
	
	protected String screenTitle;
	protected GuiSlot selectPanel;



	public MMM_GuiMobSelect(World pWorld) {
		entityMap = new TreeMap<String, Entity>();
		initEntitys(pWorld, true);
	}

	public MMM_GuiMobSelect(World pWorld, Map<String, Entity> pMap) {
		entityMap = pMap;
		initEntitys(pWorld, false);
	}


	public void initEntitys(World world, boolean pForce) {
		// �\���pEntityList�̏�����
		if (entityMapClass.isEmpty()) {
			try {
				Map lmap = (Map)ModLoader.getPrivateValue(EntityList.class, null, 1);
				entityMapClass.putAll(lmap);
			}
			catch (Exception e) {
				mod_MMM_MMMLib.Debug("EntityClassMap copy failed.");
			}
		}
		
		if (entityMap == null) return;
		if (!pForce && !entityMap.isEmpty()) return;
		
		for (Map.Entry<Class, String> le : entityMapClass.entrySet()) {
			if (Modifier.isAbstract(le.getKey().getModifiers())) continue;
			int li = 0;
			Entity lentity = null;
			try {
				// �\���p��Entity�����
				do {
					lentity = (EntityLivingBase)le.getKey().getConstructor(World.class).newInstance(world);
//					lentity = (EntityLivingBase)EntityList.createEntityByName(le.getValue(), world);
				} while (lentity != null && checkEntity(le.getValue(), lentity, li++));
			} catch (Exception e) {
				mod_MMM_MMMLib.Debug("Entity [" + le.getValue() + "] can't created.");
			}
		}
	}

	/**
	 * �n���ꂽEntity�̃`�F�b�N�y�щ��H�B
	 * true��Ԃ��Ɠ����N���X�̃G���e�B�e�B���ēx�n���Ă���A���̂Ƃ�pIndex�̓J�E���g�A�b�v�����
	 */
	protected boolean checkEntity(String pName, Entity pEntity, int pIndex) {
		entityMap.put(pName, pEntity);
		return false;
	}

	@Override
	public void initGui() {
		selectPanel = new MMM_GuiSlotMobSelect(mc, this);
		selectPanel.registerScrollButtons(3, 4);
	}

	@Override
	public void drawScreen(int px, int py, float pf) {
		float lhealthScale = BossStatus.healthScale;
		int lstatusBarLength = BossStatus.statusBarLength;
		String lbossName = BossStatus.bossName;
		boolean lfield_82825_d = BossStatus.field_82825_d;
		
		drawDefaultBackground();
		selectPanel.drawScreen(px, py, pf);
		drawCenteredString(fontRenderer, StatCollector.translateToLocal(screenTitle), width / 2, 20, 0xffffff);
		super.drawScreen(px, py, pf);
		
		// GUI�ŕ\���������̃{�X�̃X�e�[�^�X��\�����Ȃ�
		BossStatus.healthScale = lhealthScale;
		BossStatus.statusBarLength = lstatusBarLength;
		BossStatus.bossName = lbossName;
		BossStatus.field_82825_d = lfield_82825_d;
	}

	/**
	 *  �X���b�g���N���b�N���ꂽ
	 */
	public abstract void clickSlot(int pIndex, boolean pDoubleClick, String pName, EntityLivingBase pEntity);

	/**
	 *  �X���b�g�̕`��
	 */
	public abstract void drawSlot(int pSlotindex, int pX, int pY, int pDrawheight, Tessellator pTessellator, String pName, Entity pEntity);
	
}
