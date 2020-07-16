package net.minecraft.src;

import java.util.Map;

/**
 * �����i�p�̃N���X�B
 *
 */
public class MMM_EquippedStabilizer {
	
	public MMM_ModelStabilizerBase stabilizer;
	public MMM_ModelRenderer equipPoint;
	public String equipPointName;
	public Map<String, Object> localValues;
	
	
	public boolean updateEquippedPoint(ModelBase pmodel) {
		// ���t���ʒu���A�b�v�f�[�g
		for (int li = 0; li < pmodel.boxList.size(); li++) {
    		ModelRenderer lmr = (ModelRenderer)pmodel.boxList.get(li);
    		if (lmr instanceof MMM_ModelRenderer) {
    			if (lmr.boxName != null && lmr.boxName.equalsIgnoreCase(equipPointName)) {
    				equipPoint = (MMM_ModelRenderer)lmr;
    				return true;
    			}
			}
		}
		
		equipPoint = null;
		return false;
	}
	
	
}
