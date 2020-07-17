package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * �R���t�B�O�t�@�C���̕ۑ��p�B<br>
 * �n���ꂽClass���̂Ɠ�����Config�t�@�C���𑀍삷��B<br>
 * �ΏۂƂȂ�Field��Static��Private�ł͂Ȃ����ƁA�ucfg_�v�Ŏn�܂閼�̂ł��邱�ƁB<br>
 * �ΏۂƂȂ�^��int�Afloat�Adouble�Aboolean�Astring�B<br>
 * cfg_comment�͗\��ϐ�String[]�^�ŃR�����g���L�q����B<br>
 */
public class MMM_Config {

	public static File configDir;
	
	public static String[] cfg_comment = {"test code", "can br ?" };
	public static int cfg_testi = 1;
	public static byte cfg_testb = 2;
	public static String cfg_tests = "test string";


	/**
	 * �������B
	 */
	public static void init() {
		configDir = new File(MMM_FileManager.minecraftDir, "config");
	}

	/**
	 * Config�t�@�C����Ԃ��B
	 * @param pClass
	 * @return
	 */
	protected static File getConfigFile(Class pClass) {
		return new File(configDir, pClass.getSimpleName() + ".cfg");
	}

	protected static List getConfigFields(Class pClass) {
		List<Field> llist = new ArrayList<Field>();
		Field lfeilds[] = pClass.getDeclaredFields();
		if (lfeilds != null) {
			for (Field lf : lfeilds) {
				int li = lf.getModifiers();
				if (Modifier.isStatic(li) && !Modifier.isPrivate(li)) {
					if (lf.getName().startsWith("cfg_")) {
						llist.add(lf);
					}
				}
			}
		}
		
		return llist;
	}

	/**
	 * cfg�t�@�C���ɒl��ۑ�����B
	 * @param pClass
	 */
	public static void saveConfig(Class pClass) {
		File lfile = getConfigFile(pClass);
		List<Field> llist = getConfigFields(pClass);
		StringBuilder lsb = new StringBuilder();
		Properties lprop = new Properties();
		
		try {
			for (Field lf : llist) {
				if (lf.getName().equals("cfg_comment")) {
					String[] ls = (String[])lf.get(null);
					for (String lt : ls) {
						lsb.append(lt).append("\n");
					}
				} else {
					lprop.setProperty(lf.getName(), lf.get(null).toString());
				}
			}
			lprop.store(new FileOutputStream(lfile), lsb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadConfig(Class pClass) {
		File lfile = getConfigFile(pClass);
		if (!lfile.exists() || !lfile.isFile()) return;
		
		List<Field> llist = getConfigFields(pClass);
		StringBuilder lsb = new StringBuilder();
		Properties lprop = new Properties();
		
		try {
			lprop.load(new FileInputStream(lfile));
			for (Field lf : llist) {
				if (lprop.containsKey(lf.getName())) {
					String ls = lprop.getProperty(lf.getName());
					Class lc = lf.getType();
					if (lc.isAssignableFrom(String.class)) {
						lf.set(null, ls);
					}
					else if (lc.isAssignableFrom(Byte.TYPE)) {
						lf.setByte(null, Byte.parseByte(ls));
					}
					else if (lc.isAssignableFrom(Short.TYPE)) {
						lf.setShort(null, Short.parseShort(ls));
					}
					else if (lc.isAssignableFrom(Integer.TYPE)) {
						if (ls.startsWith("0x")) {
							lf.setInt(null, Integer.parseInt(ls, 16));
						} else {
							lf.setInt(null, Integer.parseInt(ls));
						}
					}
					else if (lc.isAssignableFrom(Long.TYPE)) {
						if (ls.startsWith("0x")) {
							lf.setLong(null, Long.parseLong(ls, 16));
						} else {
							lf.setLong(null, Long.parseLong(ls));
						}
					}
					else if (lc.isAssignableFrom(Boolean.TYPE)) {
						lf.setBoolean(null, Boolean.parseBoolean(ls));
					}
					else if (lc.isAssignableFrom(Float.TYPE)) {
						lf.setFloat(null, Float.parseFloat(ls));
					}
					else if (lc.isAssignableFrom(Double.TYPE)) {
						lf.setDouble(null, Double.parseDouble(ls));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * MLProp�̕W������ɋ߂�����
	 * @param pClass
	 */
	public static void checkConfig(Class pClass) {
		loadConfig(pClass);
		saveConfig(pClass);
	}
}
