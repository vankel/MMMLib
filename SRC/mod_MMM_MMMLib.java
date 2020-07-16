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
	
	public static boolean isForge = ModLoader.isModLoaded("Forge");
	public static Minecraft minecraft;
	
	
	public static void Debug(String pText) {
		// デバッグメッセージ
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
		return "1.4.5-1";
	}
	
	@Override
	public String getPriorities() {
		return isForge ? "befor-all" : "before:*";
	}

	@Override
	public void load() {
		// 初期化
		try {
			minecraft = ModLoader.getMinecraftInstance();
		} catch (Error e) {
//			MinecraftServer mcs = ModLoader.getMinecraftServerInstance();
		}
		MMM_FileManager.init(minecraft);
		MMM_TextureManager.init();
		MMM_StabilizerManager.init();
		if (isDebugView) {
			ModLoader.setInGameHook(this, true, true);
			MMM_EntityDummy.isEnable = true;
		}
	}

	@Override
	public void modsLoaded() {
		// ロード
		if (minecraft == null) return;
		
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
		// ダミーマーカーの表示用処理
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
