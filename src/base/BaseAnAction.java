package base;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import constant.BaseConstant;
import util.ProjectUtils;

/**
 * @author lxc
 */
public class BaseAnAction extends AnAction {
	
	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		getBaseInfo(anActionEvent);
	}
	
	private void getBaseInfo(AnActionEvent anActionEvent) {
		
		// 所选项目
		Project project = anActionEvent.getProject();
		BaseConstant.PROJECT = project;
		
		// 获取选中文件
		VirtualFile virtualFile = ProjectUtils.getSelectFile(anActionEvent);
		BaseConstant.VIRTUAL_FILE = virtualFile;
		
		// 获取当前包名
		String packageName = ProjectUtils.getCurrentPackageName(virtualFile);
		BaseConstant.PACKAGE_NAME = packageName;
	}
}
