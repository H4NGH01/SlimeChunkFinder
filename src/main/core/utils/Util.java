package main.core.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Util {
	
	@Contract(pure = true)
	public static @NotNull String toHTMLFormat(String s) {
		s = "<html>" + s.replaceAll("\n", "<br/>") + "</html>";
		return s;
	} 
	
	@Contract(pure = true)
	public static @NotNull String toHTMLColor(@NotNull String c) {
		return "<font color=\"" + c.toLowerCase() + "\">";
	}
	
}
