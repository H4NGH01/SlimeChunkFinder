package main.core.lang;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import main.core.utils.JSONReader;
import org.jetbrains.annotations.NotNull;

public class LanguagesManager extends ResourceBundle {

	private final HashMap<String, Object> currentLangMap = new HashMap<>();
	
	public LanguagesManager() {
		this.setLanguages(Languages.en_us);
	}
	
	public void setLanguages(@NotNull Languages lang) {
		JSONReader reader = new JSONReader(lang.getPath());
		this.currentLangMap.clear();
		for (String s : reader.getMap().keySet()) {
			this.currentLangMap.put(s, reader.getString(s));
		}
	}

	@Override
	public Object handleGetObject(@NotNull String key) {
		for (String s : currentLangMap.keySet()) {
			if (key.equalsIgnoreCase(s)) {
				return currentLangMap.get(key);
			}
		}
		return key;
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(currentLangMap.keySet());
	}

	public String getTranslateFromLanguage(String key, @NotNull Languages language) {
		final HashMap<String, Object> langMap = new HashMap<>();
		JSONReader reader = new JSONReader(language.getPath());
		for (String s : reader.getMap().keySet()) {
			langMap.put(s, reader.getString(s));
		}
		return (String) langMap.get(key);
	}
}
