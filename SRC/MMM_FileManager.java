package net.minecraft.src;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarFile;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class MMM_FileManager {

	public static File minecraftJar;
	public static Map<String, List<File>> fileList = new TreeMap<String, List<File>>();
	public static File minecraftDir;
	
	public static void init() {
		// 初期化
		if (MMM_Helper.isClient) {
			minecraftDir = MMM_Helper.mc.getMinecraftDir();
		} else {
			minecraftDir = MinecraftServer.getServer().getFile("");
		}
		
		
		// mincraft.jarを取得
		// 開発中用のJar内に含まれていることの対策
		try {
/*			
			ClassLoader lcl1 = ModLoader.class.getClassLoader();
			String lcls1 = ModLoader.class.getSimpleName().concat(".class");
			lcl1.
			URL lclu1 = lcl1.getResource(lcls1);
			JarURLConnection lclc1 = (JarURLConnection)lclu1.openConnection();
			JarFile lclj1 = lclc1.getJarFile();
			mod_MMM_MMMLib.Debug(String.format("getMincraftFile-file:%s", lclj1.getName()));
*/			
			ProtectionDomain ls1 = BaseMod.class.getProtectionDomain();
			CodeSource ls2 = ls1.getCodeSource();
			URL ls3 = ls2.getLocation();
			URI ls4 = ls3.toURI();
			minecraftJar = new File(ls4);
//			minecraftJar = new File(BaseMod.class.getProtectionDomain().getCodeSource().getLocation().toURI());
//			mod_MMM_MMMLib.Debug(String.format("getMincraftFile-file:%s", minecraftJar.getName()));
			mod_MMM_MMMLib.Debug(String.format("getMincraftFile-file:%s", minecraftJar.getAbsolutePath()));
		} catch (Exception exception) {
			mod_MMM_MMMLib.Debug("getMincrafFile-Exception.");
		}
		if (minecraftJar == null) {
			String ls = System.getProperty("java.class.path");
			ls = ls.substring(0, ls.indexOf(';'));
			minecraftJar = new File(ls);
			mod_MMM_MMMLib.Debug(String.format("getMincraftFile-file:%s", ls));
		}
		
	}

	
	public static List<File> getModFile(String pname, String pprefix) {
		// MODディレクトリに含まれる対象ファイルのオブジェクトを取得
		if (MMM_Helper.mc == null) return null;
		
		// 検索済みかどうかの判定
		if (fileList.containsKey(pname)) {
			return fileList.get(pname);
		}
		List<File> llist = new ArrayList<File>();
		fileList.put(pname, llist);
		
		// ファイル・ディレクトリを検索
		try {
			File f = new File(MMM_Helper.mc.getMinecraftDir(), "/mods/");
			if (f.isDirectory()) {
				mod_MMM_MMMLib.Debug(String.format("getModFile-get:%d.", f.list().length));
				for (File t : f.listFiles()) {
					if (t.getName().indexOf(pprefix) != -1) {
						if (t.getName().endsWith(".zip")) {
							llist.add(t);
							mod_MMM_MMMLib.Debug(String.format("getModFile-file:%s", t.getName()));
						} else if (t.isDirectory()) {
							llist.add(t);
							mod_MMM_MMMLib.Debug(String.format("getModFile-file:%s", t.getName()));
						}
					}
				}
				mod_MMM_MMMLib.Debug(String.format("getModFile-files:%d", llist.size()));
			} else {
				// まずありえない
				mod_MMM_MMMLib.Debug("getModFile-fail.");
			}
			return llist;
		}
		catch (Exception exception) {
			mod_MMM_MMMLib.Debug("getModFile-Exception.");
			return null;
		}
	}

	public static List<File> getFileList(String pname) {
		return fileList.get(pname);
	}
	
	
}
