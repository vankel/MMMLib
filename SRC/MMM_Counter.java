package net.minecraft.src;

/**
 * カウンタ用。
 */
public class MMM_Counter {

	protected int fSetValue;
	protected int fMaxValue;
	protected int fDelayValue;
	protected int fCounter;
	
	
	public MMM_Counter() {
		this(25, 20, -10);
	}

	public MMM_Counter(int pSetValue, int pMaxValue, int pDelayValue) {
		fSetValue = pSetValue;
		fMaxValue = pMaxValue;
		fDelayValue = pDelayValue;		
		fCounter = 0;
	}
	
	
	public void setCountValue(int pSetValue, int pMaxValue, int pDelayValue) {
		fSetValue = pSetValue;
		fMaxValue = pMaxValue;
		fDelayValue = pDelayValue;		
	}
	
	public void setValue(int pValue) {
		fCounter = pValue;
	}
	
	public int getValue() {
		return fCounter;
	}
	
	public void setEnable(boolean pFlag) {
		fCounter = pFlag ? (isEnable() ? fMaxValue : fSetValue) : fDelayValue;
	}
	
	public boolean isEnable() {
		return fCounter > 0;
	}
	
	public boolean isDelay() {
		return fCounter > fDelayValue;
	}
	
	public boolean isReady() {
		return fCounter >= fMaxValue;
	}
	
	public void onUpdate() {
		if (fCounter > fDelayValue) {
			fCounter--;
		}
	}
	
	
}
