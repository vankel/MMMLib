package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public abstract class MMM_ManagerBase {

	protected abstract String getPreFix();
	/**
	 * �ǉ������̖{��
	 */
	protected abstract boolean append(Class pclass);


	protected void load() {
		// ���[�h
		
		// �J���p
		Package lpackage = mod_MMM_MMMLib.class.getPackage();
		String ls = "";
		if (lpackage != null) {
			ls = mod_MMM_MMMLib.class.getPackage().getName().replace('.', File.separatorChar);
		}
		File lf1 = new File(MMM_FileManager.minecraftJar, ls);
		
		if (lf1.isDirectory()) {
			// �f�B���N�g���̉��
			decodeDirectory(lf1);
		} else {
			// Zip�̉��
			decodeZip(lf1);
		}
		
		
		// mods
		for (Entry<String, List<File>> le : MMM_FileManager.fileList.entrySet()) {
			for (File lf : le.getValue()) {
				if (lf.isDirectory()) {
					// �f�B���N�g���̉��
					decodeDirectory(lf);
				} else {
					// Zip�̉��
					decodeZip(lf);
				}
			}
		}
	}

	private void decodeDirectory(File pfile) {
		// �f�B���N�g�����̃N���X������
		for (File lf : pfile.listFiles()) {
			if (lf.isFile()) {
				String lname = lf.getName();
				if (lname.indexOf(getPreFix()) > 0 && lname.endsWith(".class")) {
					// �ΏۃN���X�t�@�C���Ȃ̂Ń��[�h
					loadClass(lf.getName());
				}
			}
		}
	}

	private void decodeZip(File pfile) {
		// zip�t�@�C�������
		try {
			FileInputStream fileinputstream = new FileInputStream(pfile);
			ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
			ZipEntry zipentry;
			
			do {
				zipentry = zipinputstream.getNextEntry();
				if(zipentry == null) {
					break;
				}
				if (!zipentry.isDirectory()) {
					String lname = zipentry.getName();
					if (lname.indexOf(getPreFix()) > 0 && lname.endsWith(".class")) {
						loadClass(zipentry.getName());
					}
				}
			} while(true);
			
			zipinputstream.close();
			fileinputstream.close();
		}
		catch (Exception exception) {
			mod_MMM_MMMLib.Debug(String.format("add%sZip-Exception.", getPreFix()));
		}
		
	}

	private void loadClass(String pname) {
		// �Ώۃt�@�C�����N���X�Ƃ��ă��[�h
		try {
			ClassLoader lclassLoader = mod_MMM_MMMLib.class.getClassLoader();
			Package lpackage = mod_MMM_MMMLib.class.getPackage();
			String lclassname = pname.replace(".class", "");
			Class lclass;
			if(lpackage != null) {
				lclassname = (new StringBuilder(String.valueOf(lpackage.getName()))).append(".").append(lclassname).toString();
				lclass = lclassLoader.loadClass(lclassname);
			} else {
				lclass = Class.forName(lclassname);
			}
			if (Modifier.isAbstract(lclass.getModifiers())) {
				return;
			}
			if (append(lclass)) {
				mod_MMM_MMMLib.Debug(String.format("get%sClass-done: %s", getPreFix(), lclassname));
			} else {
				mod_MMM_MMMLib.Debug(String.format(String.format("get%sClass-fail: %s", getPreFix(), lclassname)));
			}
			/*
            if (!(MMM_ModelStabilizerBase.class).isAssignableFrom(lclass) || Modifier.isAbstract(lclass.getModifiers())) {
            	mod_MMM_MMMLib.Debug(String.format(String.format("get%sClass-fail: %s", pprefix, lclassname)));
                return;
            }
            
            MMM_ModelStabilizerBase lms = (MMM_ModelStabilizerBase)lclass.newInstance();
            pmap.put(lms.getName(), lms);
            mod_MMM_MMMLib.Debug(String.format("get%sClass-done: %s[%s]", pprefix, lclassname, lms.getName()));
            */
		}
		catch (Exception exception) {
			mod_MMM_MMMLib.Debug(String.format("get%sClass-Exception.", getPreFix()));
		}
		catch (Error error) {
			mod_MMM_MMMLib.Debug(String.format("get%sClass-Error: %s", getPreFix(), pname));
		}
		
	}
	
	
}
