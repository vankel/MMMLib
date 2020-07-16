package net.minecraft.src;

import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;

/**
 * �ǉ��p�[�c����X�^�r���C�U�[���Ǘ�����
 */
public class MMM_StabilizerManager extends MMM_ManagerBase {

	public static final String preFix = "ModelStabilizer";
	public static Map<String, MMM_ModelStabilizerBase> stabilizerList = new TreeMap<String, MMM_ModelStabilizerBase>();
	
	
	public static void init() {
		// ���薼�̂��v���t�B�b�N�X�Ɏ���mod�t�@�C�����l��
		MMM_FileManager.getModFile("Stabilizer", preFix);
	}

	public static void loadStabilizer() {
		(new MMM_StabilizerManager()).load();
	}
	
	@Override
	protected String getPreFix() {
		return preFix;
	}
	
	@Override
	protected boolean append(Class pclass) {
        if (!(MMM_ModelStabilizerBase.class).isAssignableFrom(pclass)) {
            return false;
        }
        
		try {
	        MMM_ModelStabilizerBase lms = (MMM_ModelStabilizerBase)pclass.newInstance();
	        stabilizerList.put(lms.getName(), lms);
	        return true;
		} catch (Exception e) {
		}

		return false;
	}
	
	/**
	 * �w�肳�ꂽ���̂̃X�^�r���C�U�[���f����Ԃ��B
	 */
	public static MMM_EquippedStabilizer getStabilizer(String pname, String pequippoint) {
		if (!stabilizerList.containsKey(pname)) {
			return null;
		}
		
		MMM_EquippedStabilizer lequip = new MMM_EquippedStabilizer();
		lequip.stabilizer = stabilizerList.get(pname);
		lequip.stabilizer.init(lequip);
		lequip.equipPointName = pequippoint;
		
		return lequip;
	}
	
}
