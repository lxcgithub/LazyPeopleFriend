package util;

/**
 * 方法生成工具
 * @author lxc
 */
public class MethodGenerateUtils {
	
	/**
	 * 添加注释
	 * @param annotation 注释
	 * @return 字符串
	 */
	public static StringBuilder appendAnnotation(String annotation) {
		return new StringBuilder().append("/** \n").append("* ").append(annotation).append("*/ " +
				"\n");
	}
	
}
