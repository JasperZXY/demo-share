package org.ruanwei.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class ConfigUtil {
	@SuppressWarnings("rawtypes")
	private static HashMap[] caches = { new HashMap<String, Properties>(),
			new HashMap<String, Properties>() };
	private static Map<String, Map<String, Integer>> iCache = new HashMap<String, Map<String, Integer>>();
	private static Map<String, Map<String, Long>> lCache = new HashMap<String, Map<String, Long>>();
	private static Map<String, Map<String, String[]>> arrCache = new HashMap<String, Map<String, String[]>>();

	private static File dir = new File("/data/webapps/dragon/config");
	private static HashMap<String, Properties> cache = null;
	private static int count = 1;
	static {
		load(count);
		cache = caches[count - 1];

		refresh();
		admin();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(ConfigUtil.getInt("test1", "i1", 666));
			System.out.println(ConfigUtil.getLong("test1", "l1", 888));
			System.out.println(ConfigUtil.getLong("test1", "l2", 888));
			System.out.println(ConfigUtil.getString("test1", "s1", "wawawa"));
			try {
				Thread.sleep(11000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static String getString(String fileName, String key,
			String defaultValue) {
		Properties props = cache.get(fileName);
		return props == null ? defaultValue : props.getProperty(key,
				defaultValue);
	}

	public static int getInt(String fileName, String key, int defaultValue) {
		try {
			Map<String, Integer> kv = iCache.get(fileName);
			if (kv != null) {
				Integer iValue = kv.get(key);
				if (iValue != null) {
					return iValue;
				}

				String value = _getString(fileName, key);
				if (value == null) {
					return defaultValue;
				}

				iValue = Integer.parseInt(value);
				kv.put(key, iValue);
				System.out.println("cache " + fileName + ":" + key + "="
						+ iValue);
				return iValue;
			} else {
				String value = _getString(fileName, key);
				if (value == null) {
					return defaultValue;
				}

				kv = new HashMap<String, Integer>();
				iCache.put(fileName, kv);
				System.out.println("cache file " + fileName);

				Integer iValue = Integer.parseInt(value);
				kv.put(key, iValue);
				System.out.println("cache " + fileName + ":" + key + "="
						+ iValue);
				return iValue;
			}
			// props.remove(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static long getLong(String fileName, String key, long defaultValue) {
		try {
			Map<String, Long> kv = lCache.get(fileName);
			if (kv != null) {
				Long iValue = kv.get(key);
				if (iValue != null) {
					return iValue;
				}

				String value = _getString(fileName, key);
				if (value == null) {
					return defaultValue;
				}

				iValue = Long.parseLong(value);
				kv.put(key, iValue);
				System.out.println("cache " + fileName + ":" + key + "="
						+ iValue);
				return iValue;
			} else {
				String value = _getString(fileName, key);
				if (value == null) {
					return defaultValue;
				}

				kv = new HashMap<String, Long>();
				lCache.put(fileName, kv);

				Long iValue = Long.parseLong(value);
				kv.put(key, iValue);
				System.out.println("cache " + fileName + ":" + key + "="
						+ iValue);
				return iValue;
			}
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String[] getArray(String fileName, String key,
			String defaultValue, String delimit) {
		try {
			Map<String, String[]> kv = arrCache.get(fileName);
			if (kv != null) {
				String[] iValue = kv.get(key);
				if (iValue != null) {
					return iValue;
				}

				String value = _getString(fileName, key);
				if (value == null) {
					return new String[] { defaultValue };
				}

				iValue = value.split(delimit);
				kv.put(key, iValue);
				System.out.println("cache " + fileName + ":" + key + "="
						+ iValue);
				return iValue;
			} else {
				String value = _getString(fileName, key);
				if (value == null) {
					return new String[] { defaultValue };
				}

				kv = new HashMap<String, String[]>();
				arrCache.put(fileName, kv);
				System.out.println("cache file " + fileName);

				String[] iValue = value.split(",");
				kv.put(key, iValue);
				System.out.println("cache " + fileName + ":" + key + "="
						+ iValue);
				return iValue;
			}
		} catch (Exception e) {
			return new String[] { defaultValue };
		}
	}

	public static List<String> getList(String fileName, String key,
			String defaultValue, String delimit) {
		return Arrays.asList(getArray(fileName, key, defaultValue, delimit));
	}

	private static String _getString(String fileName, String key) {
		Properties props = cache.get(fileName);
		return props == null ? null : props.getProperty(key);
	}

	private static void load(int count) {
		if (!dir.exists() || !dir.canRead()) {
			System.out.println("config dirs not exists or can not read:"
					+ dir.getAbsolutePath());
			return;
		}

		File[] files = dir.listFiles();
		if (files.length == 0) {
			System.out.println("config files not found."
					+ dir.getAbsolutePath());
			return;
		}

		for (File file : files) {
			InputStream inStream = null;
			try {
				inStream = new FileInputStream(file);
				Properties props = new Properties();
				props.load(inStream);
				System.out.println("load file:" + file.getName());
				caches[count - 1].put(file.getName(), props);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (inStream != null) {
						inStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void refresh() {
		new Thread() {
			public void run() {
				System.out.println("refresh thread started.");
				while (true) {
					try {
						Thread.sleep(5 * 1000);
						count = count % 2 + 1;

						load(count);
						System.out.println("load new cache." + count);

						cache.clear();
						iCache.clear();
						lCache.clear();
						System.out.println("clear old cache.");

						cache = caches[count - 1];
						System.out.println("switch to new cache." + count);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private static void admin() {
		new Thread() {
			public void run() {
				System.out.println("admin thread started.");

				Scanner in = new Scanner(System.in);
				try {
					// 输入you 2 me回车，Ctrl+Z结束输入
					while (in.hasNext()) {// 按默认分隔符拆分出标记（Token）
						String cmd = in.next();// 读取一个标记（Token）
						System.out.println("cmd===" + cmd);
						if ("fileInfo".equals(cmd)) {
							System.out.println("total " + cache.size()
									+ " files.");
							for (Map.Entry<String, Properties> entry : cache
									.entrySet()) {
								System.out.println("----------------"+entry.getKey() + "("
										+ entry.getValue().size() + ")----------------");
								entry.getValue().list(System.out);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (in != null) {
							in.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
