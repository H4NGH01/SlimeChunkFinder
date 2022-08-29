package main.core;

import main.core.lang.Language;
import main.core.lang.LanguageManager;

import java.io.*;
import java.util.Properties;

public class Main {

	private static final String NAME = "Slime Chunk Finder";
	private static final String VERSION = "Beta 0.8";
	private static final Properties PROPERTIES = new Properties();
	private static final LanguageManager LANGUAGES_MANAGER = new LanguageManager();
	private static final SlimeChunkFinder FINDER = new SlimeChunkFinder();
	private static SlimeChunkFinderWindow WINDOW;

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
		for (Language l : Language.values()) {
			if (l.toString().equals(Main.getProperties().getProperty("language"))) {
				Main.getLanguagesManager().setLanguages(l);
				break;
			}
		}
		WINDOW = new SlimeChunkFinderWindow(FINDER);
	}

	public static String getName() {
		return NAME;
	}

	public static String getVersion() {
		return VERSION;
	}

	public static Properties getProperties() {
		return PROPERTIES;
	}

	public static LanguageManager getLanguagesManager() {
		return LANGUAGES_MANAGER;
	}

	public static SlimeChunkFinder getFinder() {
		return FINDER;
	}

	public static SlimeChunkFinderWindow getWindow() {
		return WINDOW;
	}

}
