package mvp;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import base.BaseAnAction;
import constant.BaseConstant;
import mvp.constant.TemplateConstant;
import util.ProjectUtils;

/**
 * @author lxc
 */
public class AndroidMvpAction extends BaseAnAction {
	
	private VirtualFile virtualFile;
	private String pathname;
	
	@Override
	public void actionPerformed(AnActionEvent e) {
		super.actionPerformed(e);
		
		// 获取类名
		String className = Messages.showInputDialog(BaseConstant.PROJECT, "请输入类名称", "NewMvpGroup",
				Messages.getQuestionIcon());
		TemplateConstant.NAME = className;
		
		// 拼接全目录
		String pathname = BaseConstant.VIRTUAL_FILE.getPath() + "/" + className.toLowerCase();
		TemplateConstant.PATH_NAME = pathname;
		
		// Java路径名
		String javaPathname = pathname.substring(pathname.indexOf("java")+5).replace("/", ".");
		TemplateConstant.JAVA_PATH_NAME = javaPathname;
		
		
		// 输入为空
		if (className == null || className.equals("")) {
			System.out.print("没有输入类名");
			return;
		} else {
			GenerateMvpCode.createClassMvp(className);
		}
		
		// 刷新项目
		ProjectUtils.refreshProject(BaseConstant.PROJECT);
	}
	
	
}
