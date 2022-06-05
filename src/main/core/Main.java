package main.core;

import main.core.lang.Languages;
import main.core.lang.LanguagesManager;

import java.io.*;
import java.util.Properties;

public class Main {

	private static final String NAME = "Slime Chunk Finder";
	private static final String VERSION = "Beta 0.6";
	private static final Properties PROPERTIES = new Properties();
	private static final LanguagesManager LANGUAGES_MANAGER = new LanguagesManager();

	public static void main(String[] args) {
		try {
			File configFile = new File("config.properties");
			if (!configFile.exists()) {
				configFile.createNewFile();
				InputStream fis = Main.class.getResourceAsStream("/res/config.properties");
				PROPERTIES.load(fis);
				PROPERTIES.store(new FileOutputStream("config.properties"), null);
			}
			PROPERTIES.load(new FileInputStream(configFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Languages l : Languages.values()) {
			if (l.toString().equals(Main.getProperties().getProperty("language"))) {
				Main.getLanguagesManager().setLanguages(l);
				break;
			}
		}
		new SlimeChunkFinderWindow(new SlimeChunkFinder());
	}

	public static Properties getProperties() {
		return PROPERTIES;
	}

	public static LanguagesManager getLanguagesManager() {
		return LANGUAGES_MANAGER;
	}
	
	public static String getName() {
		return NAME;
	}

	public static String getVersion() {
		return VERSION;
	}
	
}
