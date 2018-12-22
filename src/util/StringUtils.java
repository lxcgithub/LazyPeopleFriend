package util;

/**
 * @author lxc
 */
public class StringUtils {
	
	/**
	 * 首字母大写（方法名，大驼峰）
	 */
	public static String toUpperTheFirstLetter(String content){
		return content.substring(0, 1).toUpperCase() + content.substring(1);
	}
	
	/**
	 * 首字母小写（方法名，小驼峰）
	 */
	public static String toLowerTheFirstLetter(String content){
		return content.substring(0, 1).toLowerCase() + content.substring(1);
	}
}
