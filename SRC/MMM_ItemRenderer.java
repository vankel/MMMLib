package net.minecraft.src;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.client.Minecraft;

public class MMM_ItemRenderer extends ItemRenderer {

	// プライベート変数を使えるように
	public Minecraft mc;
    public ItemStack itemToRender;
    public float equippedProgress;
    public float prevEquippedProgress;
    
	
	public MMM_ItemRenderer(Minecraft minecraft) {
		super(minecraft);
		
		mc = minecraft;
	}
	
    public void renderItem(EntityLiving entityliving, ItemStack itemstack, int i) {
    	try {
    		Method lmethod = itemstack.getItem().getClass().getMethod("renderItem", EntityLiving.class, ItemStack.class, int.class);
    		if ((Boolean)lmethod.invoke(itemstack.getItem(), entityliving, itemstack, i)) {
    			return;
    		}
    	} catch (Exception e) {
    	}
    	
   		super.renderItem(entityliving, itemstack, i);
    }
    
    public void renderItemInFirstPerson(float f) {
    	itemToRender = null;
    	equippedProgress = 0.0F;
    	prevEquippedProgress = 0.0F;
    	
	    try {
	    	// ローカル変数を確保
    	    itemToRender = (ItemStack)ModLoader.getPrivateValue(ItemRenderer.class, this, 1);
    	    equippedProgress = (Float)ModLoader.getPrivateValue(ItemRenderer.class, this, 2);
    	    prevEquippedProgress = (Float)ModLoader.getPrivateValue(ItemRenderer.class, this, 3);

    	    Method lmethod = itemToRender.getItem().getClass().getMethod("renderItemInFirstPerson", float.class);
    		if ((Boolean)lmethod.invoke(itemToRender.getItem(), f)) {
    			return;
    		}
	    } catch (Exception e) {
	    }
   		super.renderItemInFirstPerson(f);
    	
    }
	
}
