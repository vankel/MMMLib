package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.minecraft.client.Minecraft;

public class MMM_TextureManager {

	private static String defDirName = "/mob/littleMaid/";
	private static String defNames[] = {
		"mob_littlemaid0.png", "mob_littlemaid1.png",
		"mob_littlemaid2.png", "mob_littlemaid3.png",
		"mob_littlemaid4.png", "mob_littlemaid5.png",
		"mob_littlemaid6.png", "mob_littlemaid7.png",
		"mob_littlemaid8.png", "mob_littlemaid9.png",
		"mob_littlemaida.png", "mob_littlemaidb.png",
		"mob_littlemaidc.png", "mob_littlemaidd.png",
		"mob_littlemaide.png", "mob_littlemaidf.png",
		"mob_littlemaidw.png",
		"mob_littlemaid_a00.png", "mob_littlemaid_a01.png"
	};
	
	public static final int tx_oldwild		= 0x10; //16;
	public static final int tx_oldarmor1	= 0x11; //17;
	public static final int tx_oldarmor2	= 0x12; //18;
	public static final int tx_gui			= 0x20; //32;
	public static final int tx_wild			= 0x30; //48;
	public static final int tx_armor1		= 0x40; //64;
	public static final int tx_armor2		= 0x50; //80;
	public static Map<String, Map<Integer, String>> textures = new TreeMap<String, Map<Integer, String>>();
	public static Map<String, MMM_ModelBiped[]> modelMap = new TreeMap<String, MMM_ModelBiped[]>();
	public static Map<String, Map<String, Map<Integer, String>>> armors = new TreeMap<String, Map<String, Map<Integer, String>>>();
	public static String[] armorFilenamePrefix;
	public static MMM_ModelBiped[] defaultModel;
	/**
	 * �T�[�o�[�E�N���C�A���g�ԂŃe�N�X�`���p�b�N�̖��̃��X�g�̓��������̂Ɏg���B
	 * ���܂����ƍ��΁A�N���C�A���g���ɂ����e�N�X�`���p�b�N������΁A�T�[�o�ɂ͕s�v�ɂȂ�͂��B
	 * ������i�̍\�z���߂�ǂ��̂Ō�񂵁B
	 * �N���C�A���g����T�[�o�[�ɃC���f�b�N�X���X�g�ɖ������̂̃C���f�b�N�X�����N�G�X�g�B
	 * �T�[�o�[���烊�N�G�X�g���ꂽ�C���f�b�N�X��Ԃ��A������΃T�[�o�[���̃��X�g�ɒǉ����Ēl��Ԃ��B
	 * �N���C�A���g���̃��X�g�ɒǉ��B
	 */
	public static List<String> textureIndex = new ArrayList<String>();
	
	
	public static void init() {
		MMM_FileManager.getModFile("littleMaidMob", "littleMaidMob");
	}

	private static void getArmorPrefix() {
		// �A�[�}�[�t�@�C���̃v���t�B�b�N�X���l��
		try {
			Field f = RenderPlayer.class.getDeclaredFields()[3];
			f.setAccessible(true);
			String[] s = (String[])f.get(null);
			List<String> list = Arrays.asList(s);
			armorFilenamePrefix = list.toArray(new String[0]);
//			for (String t : armorFilenamePrefix) {
//				mod_littleMaidMob.Debug("armor:".concat(t));
//			}
		}
		catch (Exception e) {
		}
	}
	
	
	public static boolean loadTextures() {
		getArmorPrefix();

		// �f�t�H���g�e�N�X�`�����̍쐬
		if (defaultModel != null) {
			for (int i = 0; i < defNames.length; i++) {
				addTextureName((new StringBuilder()).append(defDirName).append("default/").append(defNames[i]).toString());
			}
			modelMap.put("default", defaultModel);
			getStringToIndex("default");
			mod_MMM_MMMLib.Debug("getTexture-append-default-done.");
		}

		// jar���̃e�N�X�`����ǉ�
		if (MMM_FileManager.minecraftJar == null) {
			mod_MMM_MMMLib.Debug("getTexture-append-jar-file not founded.");
		} else {
			addTexturesJar(MMM_FileManager.minecraftJar);
		}

		// mods
		for (File lf : MMM_FileManager.getFileList("littleMaidMob")) {
			boolean lflag;
			if (lf.isDirectory()) {
				// �f�B���N�g��
	    		lflag = addTexturesDir(lf);
			} else {
				// zip
				lflag = addTexturesZip(lf);
			}
			mod_MMM_MMMLib.Debug(String.format("getTexture-append-%s-%s.", lf.getName(), lflag ? "done" : "fail"));
		}
/*		
		// ���[�h�����e�N�X�`���p�b�N����N���X���������[�h
		for (Entry<String, Map<Integer, String>> tt: textures.entrySet()) {
			String st = tt.getKey();
			int index = st.lastIndexOf("_");
			if (index > -1) {
				st = st.substring(index + 1);
				if (!st.isEmpty()) {
					addModelClass("ModelLittleMaid_".concat(st).concat(".class"));
				}
			}
		}
		mod_MMM_MMMLib.Debug("getTexture-append-Models-append-done.");
*/		
		
		return false;
	}
	
	private static void addModelClass(String fname) {
		// ���f����ǉ�
		String prefix = "ModelLittleMaid_";
		int lfindprefix = fname.indexOf(prefix);
		if (lfindprefix > -1 && fname.endsWith(".class")) {
			String cn = fname.replace(".class", "");
			String pn = cn.substring(prefix.length() + lfindprefix);
			
			if (modelMap.containsKey(pn)) return;
			
			try {
		        ClassLoader lclassloader = mod_MMM_MMMLib.class.getClassLoader();
	            Package lpackage = mod_MMM_MMMLib.class.getPackage();
	            Class lclass;
	            if (lpackage != null) {
	                cn = (new StringBuilder(String.valueOf(lpackage.getName()))).append(".").append(cn).toString();
		            lclass = lclassloader.loadClass(cn);
	            } else {
					lclass = Class.forName(cn);
	            }
	            if (!(MMM_ModelBiped.class).isAssignableFrom(lclass) || Modifier.isAbstract(lclass.getModifiers())) {
					mod_MMM_MMMLib.Debug(String.format("getModelClass-fail."));
	                return;
	            }
	            MMM_ModelBiped mlm[] = new MMM_ModelBiped[3];
	            Constructor<MMM_ModelBiped> cm = lclass.getConstructor(float.class);
	            mlm[0] = cm.newInstance(0.0F);
	            mlm[1] = cm.newInstance(0.5F);
	            mlm[2] = cm.newInstance(0.1F);
	            modelMap.put(pn, mlm);
//				mod_littleMaidMob.Debug(String.format("getModelClass-%s", mlm[0].getClass().getName()));
				mod_MMM_MMMLib.Debug(String.format("getModelClass-%s:%s", pn, cn));
				
			}
			catch (Exception exception) {
				mod_MMM_MMMLib.Debug(String.format("getModelClass-Exception: %s", fname));
			}
			catch (Error error) {
				mod_MMM_MMMLib.Debug(String.format("getModelClass-Error: ".concat(fname)));
			}
		}
	}
	
	private static void addTextureName(String fname) {
		// �p�b�P�[�W�Ƀe�N�X�`����o�^
		if (!fname.startsWith("/")) {
			fname = (new StringBuilder()).append("/").append(fname).toString();
		}
		
		if (fname.startsWith(defDirName)) {
			int i = fname.lastIndexOf("/");
			if (defDirName.length() < i) {
				String pn = fname.substring(defDirName.length(), i);
				pn = pn.replace('/', '.');
				String fn = fname.substring(i);
				int j = getIndex(fn);
				if (j > -1) {
					String an = null;
					if (j == tx_oldarmor1) {
						j = tx_armor1;
						an = "default";
					}
					if (j == tx_oldarmor2) {
						j = tx_armor2;
						an = "default";
					}
					if (j == tx_oldwild) {
						j = tx_wild + 12;
					}
					if (j >= 0x40 && j <= 0x5f) {
						// �_���[�W�h�A�[�}�[
						Map<String, Map<Integer, String>> s = armors.get(pn);
						if (s == null) {
							s = new HashMap<String, Map<Integer, String>>();
							armors.put(pn, s);
							mod_MMM_MMMLib.Debug(String.format("getTextureName-append-armorPack-%s", pn));
						}
						if (an == null) an = fn.substring(1, fn.lastIndexOf('_'));
						Map<Integer, String> ss = s.get(an);
						if (ss == null) {
							ss = new HashMap<Integer, String>();
							s.put(an, ss);
						}
						ss.put(j, fn);
//						mod_littleMaidMob.Debug(String.format("getTextureName-append-armor-%s:%d:%s", pn, j, fn));
					} else {
						// �ʏ�̃e�N�X�`��
						Map<Integer, String> s = textures.get(pn);
						if (s == null) {
							s = new HashMap<Integer, String>();
							textures.put(pn, s);
							getStringToIndex(pn);
							mod_MMM_MMMLib.Debug(String.format("getTextureName-append-texturePack-%s", pn));
						}
						s.put(j, fn);
//						mod_littleMaidMob.Debug(String.format("getTextureName-append-%s:%d:%s", pn, j, fn));
					}
				}
			}
		}
	}
	
	
	public static boolean addTexturesZip(File file) {
		//
		if (file == null || file.isDirectory()) {
			return false;
		}
		try {
	        FileInputStream fileinputstream = new FileInputStream(file);
	        ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
            ZipEntry zipentry;
            do
            {
                zipentry = zipinputstream.getNextEntry();
                if(zipentry == null)
                {
                    break;
                }
                if (!zipentry.isDirectory()) {
                	if (zipentry.getName().endsWith(".class")) {
                		addModelClass(zipentry.getName());
                	} else {
                    	addTextureName(zipentry.getName());
                	}
                }
            } while(true);
            
            
            
            zipinputstream.close();
            fileinputstream.close();
            
            return true;
		}
		catch (Exception exception) {
			mod_MMM_MMMLib.Debug("addTextureZip-Exception.");
			return false;
		}
	}

	protected static void addTexturesJar(File file) {
		// 
		if (file.isFile()) {
			mod_MMM_MMMLib.Debug("addTextureJar-zip.");
    		if (addTexturesZip(file)) {
    			mod_MMM_MMMLib.Debug("getTexture-append-jar-done.");
    		} else {
    			mod_MMM_MMMLib.Debug("getTexture-append-jar-fail.");
    		}
		}
		
		// �Ӗ��Ȃ��H
		if (file.isDirectory()) {
			mod_MMM_MMMLib.Debug("addTextureJar-file.");

            for (File t : file.listFiles()) {
            	if (t.isDirectory() && t.getName().equalsIgnoreCase("mob")) {
            		if (addTexturesDir(file)) {
            			mod_MMM_MMMLib.Debug("getTexture-append-jar-done.");
            		} else {
            			mod_MMM_MMMLib.Debug("getTexture-append-jar-fail.");
            		}
            	}
            }
			
			Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
            if(package1 != null)
            {
                String s = package1.getName().replace('.', File.separatorChar);
                file = new File(file, s);
                mod_MMM_MMMLib.Debug(String.format("addTextureJar-file-Packege:%s", s));
            } else {
            	mod_MMM_MMMLib.Debug("addTextureJar-file-null.");
            }
    		if (addTexturesDir(file)) {
    			mod_MMM_MMMLib.Debug("getTexture-append-jar-done.");
    		} else {
    			mod_MMM_MMMLib.Debug("getTexture-append-jar-fail.");
    		}

		}
	}
	
	public static boolean addTexturesDir(File file) {
		// mods�t�H���_�ɓ˂�����ł�����̂������A�ċA�ŁB
		if (file == null) {
			return false;
		}
		
		try {
            for (File t : file.listFiles()) {
                if(t.isDirectory()) {
                	addTexturesDir(t);
                } else {
                    String s = t.getPath().replace('\\', '/');
                    if (t.getName().endsWith(".class")) {
                		addModelClass(t.getName());
                    } else {
                        int i = s.indexOf(defDirName);
                        if (i > -1) {
                        	// �Ώۂ̓e�N�X�`���f�B���N�g��
                        	addTextureName(s.substring(i).replace('\\', '/'));
                        }
                    }
                }
            }
            return true;
            
		}
		catch (Exception e) {
			mod_MMM_MMMLib.Debug("addTextureDebug-Exception.");
			return false;
		
		}
		
	}

	private static int getIndex(String name) {
		// ���O����C���f�b�N�X�����o��
		for (int i = 0; i < defNames.length; i++) {
			if (name.endsWith(defNames[i])) {
				return i;
			}
		}
		
		Pattern p = Pattern.compile("_([0-9a-f]+).png");
		Matcher m = p.matcher(name);
		if (m.find()) {
			return Integer.decode("0x" + m.group(1));
		}
		
		return -1;
	}
	
	public static String getNextPackege(String nowname, int index) {
		// ���̃e�N�X�`���p�b�P�[�W�̖��O��Ԃ�
		boolean f = false;
		for (Map.Entry<String, Map<Integer, String>> es : textures.entrySet()) {
			if (f && es.getValue().get(index) != null) {
				return es.getKey();
			}
			if (es.getKey().equalsIgnoreCase(nowname))
				f = true;
		}
		for (Map.Entry<String, Map<Integer, String>> es : textures.entrySet()) {
			if (es.getValue().get(index) != null) {
				return es.getKey();
			}
		}		
		return null;
	}

	public static String getPrevPackege(String nowname, int index) {
		// �O�̃e�N�X�`���p�b�P�[�W�̖��O��Ԃ�
		String lastname = null;
		for (Map.Entry<String, Map<Integer, String>> es : textures.entrySet()) {
			if (es.getKey().equalsIgnoreCase(nowname)) {
				if (lastname != null) {
					break;
				}
			}
			if (es.getValue().get(index) != null) {
				lastname = es.getKey();
			}
		}
		return lastname;
	}

	
	public static String getTextureName(String name, int index) {
		if (!textures.containsKey(name) || !textures.get(name).containsKey(index))
			return null;
		return (new StringBuilder()).append(defDirName).append(name.replace('.', '/')).append(textures.get(name).get(index)).toString();
	}
	
	public static int getTextureCount() {
		return textures.size();
	}
	
	public static String getNextArmorPackege(String nowname) {
		// ���̃e�N�X�`���p�b�P�[�W�̖��O��Ԃ�
//		if (getArmorTextureCount() == 0) return null;
		
		boolean f = false;
		for (Map.Entry<String, Map<String, Map<Integer, String>>> es : armors.entrySet()) {
			if (f) {
				return es.getKey();
			}
			if (es.getKey().equalsIgnoreCase(nowname))
				f = true;
		}
		for (Map.Entry<String, Map<String, Map<Integer, String>>> es : armors.entrySet()) {
			return es.getKey();
		}		
		return null;
	}

	public static String getPrevArmorPackege(String nowname) {
		// �O�̃e�N�X�`���p�b�P�[�W�̖��O��Ԃ�
		String lastname = null;
		for (Map.Entry<String, Map<String, Map<Integer, String>>> es : armors.entrySet()) {
			if (es.getKey().equalsIgnoreCase(nowname)) {
				if (lastname != null) {
					break;
				}
			}
			lastname = es.getKey();
		}
		return lastname;
	}

	public static String getArmorTextureName(String name, int index, ItemStack itemstack) {
		// index��0x40,0x50�ԑ�
		Map<String, Map<Integer, String>> mm = armors.get(name);
		if (mm == null || itemstack == null) return null;
		
		if (!(itemstack.getItem() instanceof ItemArmor)) return null;
		Map<Integer, String> m = mm.get(armorFilenamePrefix[((ItemArmor)itemstack.getItem()).renderIndex]);
		if (m == null) {
			m = mm.get("default");
			if (m == null) return null;
		}
		int l = 0;
		if (itemstack.getMaxDamage() > 0) {
			l = (10 * itemstack.getItemDamage() / itemstack.getMaxDamage());
		}
		String s = null;
		for (int i = index + l; i >= index; i--) {
			s = m.get(i);
			if (s != null) break;
		}
		if (s == null) {
			return null;
		} else {
			return (new StringBuilder()).append(defDirName).append(name.replace('.', '/')).append(s).toString();
		}
	}
	
	public static int getArmorTextureCount() {
		return armors.size();
	}
	
	public static int getRandomWildColor(String name, Random rand) {
		// �쐶�̃��C�h�̐F�������_���ŕԂ�
//		Integer i[] = textures.get(name).keySet().toArray(new Integer[0]);
		List<Integer> l = new ArrayList<Integer>();
		for (Integer i : textures.get(name).keySet()) {
			if (i >= 0x30 && i <= 0x3f) {
				l.add(i & 0x0f);
			}
		}
		
		if (l.size() > 0) {
			return l.get(rand.nextInt(l.size()));
		} else {
			return -1;
		}
	}
	
	public static int getStringToIndex(String pname) {
		if (!textureIndex.contains(pname)) {
			textureIndex.add(pname);
		}
		return textureIndex.indexOf(pname);
	}
	
	public static String getIndexToString(int pindex) {
		if (textureIndex.size() < pindex) {
			pindex = 0;
		}
		return textureIndex.get(pindex);
	}
	
}
