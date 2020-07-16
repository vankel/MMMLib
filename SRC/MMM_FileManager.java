package net.minecraft.src;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;

public class MMM_FileManager {

	public static Minecraft mc;
	public static File minecraftJar;
	public static Map<String, List<File>> fileList = new TreeMap<String, List<File>>();
	
	
	public static void init(Minecraft pminecraft) {
		// ������
		mc = pminecraft;
		
		// mincraft.jar���擾
		// �J�����p��Jar���Ɋ܂܂�Ă��邱�Ƃ̑΍�
		try {
			minecraftJar = new File(BaseMod.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			mod_MMM_MMMLib.Debug(String.format("getMincraftFile-file:%s", minecraftJar.getName()));
		} catch (Exception exception) {
			mod_MMM_MMMLib.Debug("getMincrafFile-Exception.");
		}

	}

	
	public static List<File> getModFile(String pname, String pprefix) {
		// MOD�f�B���N�g���Ɋ܂܂��Ώۃt�@�C���̃I�u�W�F�N�g���擾

		// �����ς݂��ǂ����̔���
		if (fileList.containsKey(pname)) {
			return fileList.get(pname);
		}
		List<File> llist = new ArrayList<File>();
		fileList.put(pname, llist);
		
		// �t�@�C���E�f�B���N�g��������
		try {
			File f = new File(mc.getMinecraftDir(), "/mods/");
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
				// �܂����肦�Ȃ�
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
