package net.minecraft.src;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class mod_MMM_MMMLib extends BaseMod {

	@MLProp()
	public static boolean isDebugView = false;
	@MLProp()
	public static boolean isDebugMessage = true;
	
	
	public static void Debug(String pText) {
		// �f�o�b�O���b�Z�[�W
		if (isDebugMessage) {
			System.out.println(String.format("MMMLib-%s", pText));
		}
	}

	@Override
	public String getName() {
		return "MMMLib";
	}

	@Override
	public String getVersion() {
		return "1.4.5-3";
	}
	
	@Override
	public String getPriorities() {
		return MMM_Helper.isForge ? "befor-all" : "before:*";
	}

	@Override
	public void load() {
		// ������
		Debug(MMM_Helper.isClient ? "Client" : "Server");
		Debug(MMM_Helper.isForge ? "Forge" : "Modloader");
		MMM_FileManager.init();
		MMM_TextureManager.init();
		MMM_StabilizerManager.init();
		if (isDebugView) {
			ModLoader.setInGameHook(this, true, true);
			MMM_EntityDummy.isEnable = true;
		}
	}

	@Override
	public void modsLoaded() {
		// ���[�h
		if (!MMM_Helper.isClient) return;
		
		MMM_TextureManager.loadTextures();
		MMM_StabilizerManager.loadStabilizer();
	}
	
	@Override
	public void addRenderer(Map var1) {
    	if (isDebugView) {
    		var1.put(net.minecraft.src.MMM_EntityDummy.class, new MMM_RenderDummy());
    	}
    	//RenderItem
    	var1.put(EntityItem.class, new MMM_RenderItem());
	}

	@Override
	public boolean onTickInGame(float var1, Minecraft var2) {
		// �_�~�[�}�[�J�[�̕\���p����
		try {
			for (Iterator<MMM_EntityDummy> li = MMM_EntityDummy.appendList.iterator(); li.hasNext();) {
				var2.theWorld.spawnEntityInWorld(li.next());
				li.remove();
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return true;
	}
	
}
