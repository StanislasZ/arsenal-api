package com.cmbnb.weChatNotice.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: hxy
 * @date: 2017/10/24 10:16
 */
public class StringTools {

	public static boolean isNullOrEmpty(String str) {
		return null == str || "".equals(str) || "null".equals(str);
	}

	public static boolean isNullOrEmpty(Object obj) {
		return null == obj || "".equals(obj);
	}


	public static String trimAllBlank(String s) {
		if (s == null) return null;

		return s.replaceAll("\\s*", "");
	}


	public static boolean existEmpty(String... args) {
		for (String s: args) {
			if (StringUtils.isEmpty(s)) return true;
		}
		return false;
	}


	public static void main(String[] args) {

		System.out.println(existEmpty("1", "2"));

		System.out.println(existEmpty("1", "", " 3"));

		System.out.println(existEmpty("1", null, " 3"));

	}
}
