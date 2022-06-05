package main.core.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Util {
	
	@Contract(pure = true)
	public static @NotNull String toHTMLFormat(@NotNull String s) {
		return "<html>" + s.replaceAll("\n", "<br/>") + "</html>";
	}
	
	@Contract(pure = true)
	public static @NotNull String toHTMLColor(@NotNull String c) {
		return "<font color=\"" + c.toLowerCase() + "\">";
	}
	
}
