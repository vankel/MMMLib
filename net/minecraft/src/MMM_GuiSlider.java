package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class MMM_GuiSlider extends GuiButton {

	public String prefixStr;
	public float sliderValue;
	public boolean dragging;
	public String strFormat = "%s : %.2f";
	public float sliderMultiply = 1.0F;
	public float sliderOffset = 0.0F;

	public MMM_GuiSlider(int i, int j, int k, String s, float f) {
		super(i, j, k, 100, 20, "");
		sliderValue = 1.0F;
		dragging = false;
		sliderValue = f;
		prefixStr = s;
	}

	public MMM_GuiSlider(int i, int j, int k, String s, float f, float m, float o) {
		this(i, j, k, s, f);
		sliderMultiply = m;
		sliderOffset = o;
	}

	protected int getHoverState(boolean flag) {
		return 0;
	}

	protected void mouseDragged(Minecraft minecraft, int i, int j) {
		if (!drawButton) {
			return;
		}
		if (dragging) {
			sliderValue = (float) (i - (xPosition + 4)) / (float) (width - 8);
			if (sliderValue < 0.0F) {
				sliderValue = 0.0F;
			}
			if (sliderValue > 1.0F) {
				sliderValue = 1.0F;
			}
			setDisplayString();
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(xPosition + (int) (sliderValue * (float) (width - 8)),
				yPosition, 0, 66, 4, 20);
		drawTexturedModalRect(xPosition + (int) (sliderValue * (float) (width - 8)) + 4,
				yPosition, 196, 66, 4, 20);
	}

	public boolean mousePressed(Minecraft minecraft, int i, int j) {
		if (super.mousePressed(minecraft, i, j)) {
			sliderValue = (float) (i - (xPosition + 4)) / (float) (width - 8);
			if (sliderValue < 0.0F) {
				sliderValue = 0.0F;
			}
			if (sliderValue > 1.0F) {
				sliderValue = 1.0F;
			}
			setDisplayString();
			dragging = true;
			return true;
		} else {
			return false;
		}
	}

	public void mouseReleased(int i, int j) {
		dragging = false;
	}

	public float getSliderValue() {
		return sliderValue * sliderMultiply + sliderOffset;
	}

	public MMM_GuiSlider setDisplayString() {
		displayString = String.format(strFormat, prefixStr, getSliderValue());
		return this;
	}

	public MMM_GuiSlider setStrFormat(String s) {
		strFormat = s;
		return this;
	}

}
