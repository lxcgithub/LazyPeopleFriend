package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lxc
 */
public class FormatUtil {
	
	/**
	 * 判断是否为数字
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[\\-|0-9][0-9]*");
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		return result;
	}
	
	/**
	 * 转换为常量
	 */
	public static String underscoreName(String camelCaseName) {
		StringBuilder result = new StringBuilder();
		if (camelCaseName != null && camelCaseName.length() > 0) {
			result.append(camelCaseName.substring(0, 1).toLowerCase());
			for (int i = 1; i < camelCaseName.length(); i++) {
				char ch = camelCaseName.charAt(i);
				if (Character.isUpperCase(ch)) {
					result.append("_");
					result.append(Character.toLowerCase(ch));
				} else {
					result.append(ch);
				}
			}
		}
		return result.toString().toUpperCase();
	}
}
