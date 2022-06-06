package main.core.lang;

public enum Language {
	en_us("/res/en_us.json"),
	zh_ch("/res/zh_ch.json"),
	zh_tw("/res/zh_tw.json");
	
	public final String path;
	
	Language(String s) {
		this.path = s;
	}
	
	public String getPath() {
		return this.path;
	}

}
