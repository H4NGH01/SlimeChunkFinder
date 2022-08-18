package main.core.lang;

public enum Language {

	en_us("/res/en_us.json"),
	zh_ch("/res/zh_ch.json"),
	zh_tw("/res/zh_tw.json");
	
	private final String path;
	
	Language(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}

}
