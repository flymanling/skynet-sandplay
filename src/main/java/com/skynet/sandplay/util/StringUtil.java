package com.skynet.sandplay.util;

public class StringUtil {

	public static boolean isNotEmpty(String s) {
		return s != null && !"".equals(s) && !"".equals(s.trim());
	}
}
