package com.mtk.songsOrganizer.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class External {

	private static final Properties prop = new Properties();
	public static String mongoHost;
	private static External external;
	private static String filePath;

	static {
		/// initialize external
		ResourceBundle properties = null;
		boolean propFound = false;
		try {
			properties = ResourceBundle.getBundle("settings");
			propFound = true;
		} catch (MissingResourceException e) {
			System.out.println("Internal settings.properties file not found. Searching file in JBoss dir");
		}

		if (propFound) {
			External.init(properties);
			External.load();
		}

		String path = System.getProperty("jboss.server.config.dir");
		if (!propFound && path != null) {
			System.out.println(path + File.separator + "settings.properties");
			External.init(path + File.separator + "settings.properties");
			External.load();
		}
	}

	private External() {
	}

	private External(String filename) {
		try {
			FileInputStream f = new FileInputStream(filename);
			prop.load(f);
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init(String filename) {
		External.external = new External(filename);
		filePath = filename.substring(0, filename.lastIndexOf(File.separator) + 1);
	}

	public static void init(ResourceBundle resourceBundle) {
		External.external = new External();
		for (String key : resourceBundle.keySet()) {
			prop.setProperty(key, resourceBundle.getString(key).trim());
		}
	}

	public static String getString(String param) {
		checkInit();
		return External.prop.getProperty(param, "").trim();
	}

	public static boolean getBoolean(String param) {
		checkInit();
		return "true".equals(External.prop.getProperty(param, "false").trim());
	}

	public static int getInt(String param) {
		checkInit();
		return Integer.parseInt(External.prop.getProperty(param, "0").trim());
	}

	public static short getShort(String param) {
		checkInit();
		return Short.parseShort(External.prop.getProperty(param, "0").trim());
	}

	private static void checkInit() {
		if (external == null) {
			throw new NullPointerException("External is not initialized..");
		}
	}

	public static void load() {
		mongoHost = getString("MONGO_HOST");
	}
}
