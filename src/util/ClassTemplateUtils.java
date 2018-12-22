package util;

/**
 * @author lxc
 */
public class ClassTemplateUtils {
	
	private String pathname;
	private String className;
	private String javaPathName;
	
	public ClassTemplateUtils(String className, String pathname,  String javaPathName) {
		this.pathname = pathname;
		this.javaPathName = javaPathName;
		this.className = StringUtils.toUpperTheFirstLetter(className);
	}
	
	/**
	 *
	 * @param fileInName
	 * @param packagePath
	 */
	public void convertContent(String fileInName, String packagePath) {
		// 不设置类名，默认替换后缀，写入文件
		String generateFileName = fileInName.replace(".txt", ".java");
		// 读入文件
		String afterConvertContent =new FileUtils().readFile(fileInName)
				// 替换包名
				.replace("&package&", javaPathName)
				// 替换类名
				.replace("&className&", className);
		
		new FileUtils().writeToFile(afterConvertContent, pathname + packagePath, className + generateFileName);
	}
}
