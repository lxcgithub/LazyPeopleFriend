package util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author lxc
 */
public class ProjectUtils {
	
	/**
	 * 刷新项目
	 * @param project project
	 */
	public static void refreshProject (Project project){
		assert project != null;
		project.getBaseDir().refresh(false, true);
	}
	
	/**
	 * 获取当前包名
	 * @param file 当前选中文件
	 * @return 当前包名
	 */
	public static String getCurrentPackageName(VirtualFile file) {
		String[] filePath = file.toString().split("/");
		int len = filePath.length;
		return filePath[len-1];
	}
	
	/**
	 * 获取选中文件
	 * @param e 当前活动
	 * @return 选中文件
	 */
	public static VirtualFile getSelectFile(AnActionEvent e) {
		return DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
	}
	
	/**
	 * 获取选中文字
	 * @param e 当前活动
	 * @return 选中文字
	 */
	public String getSleectText(AnActionEvent e) {
		// 获取选中内容
		final Editor mEditor = e.getData(PlatformDataKeys.EDITOR);
		// 获取选中文字
		String mSelectedText = mEditor.getSelectionModel().getSelectedText();
		return mSelectedText;
	}
}
