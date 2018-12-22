package mvp;

import mvp.constant.TemplateConstant;
import util.ClassTemplateUtils;

/**
 * @author lxc
 */
public class GenerateMvpCode {
	
	/**
	 * 创建 MVP 类文件
	 * @param className 输入的类名
	 */
	public static void createClassMvp(String className) {
		// 区分结尾是不是 Fragment
		boolean isFragment = className.endsWith("Fragment") || className.endsWith("fragment");
		
		if (className.endsWith("Fragment") || className.endsWith("fragment") || className.endsWith("Activity") || className.endsWith("activity")) {
			// 获取类名称
			className = className.substring(0, className.length() - 8);
			TemplateConstant.NAME = className;
		}
		
		ClassTemplateUtils utils = new ClassTemplateUtils(className,TemplateConstant.PATH_NAME,TemplateConstant.JAVA_PATH_NAME);
		
		// 生成模版类
		if (isFragment) {
			utils.convertContent("Fragment.txt", TemplateConstant.UI_FRAGMENT);
		} else {
			utils.convertContent("Activity.txt", TemplateConstant.UI_ACTIVITY);
		}
		utils.convertContent("Presenter.txt", TemplateConstant.PRESENTER);
		utils.convertContent("View.txt", TemplateConstant.MVPVIEW);
	}
}
