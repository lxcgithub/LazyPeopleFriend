package http;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;

import base.BaseAnAction;
import constant.BaseConstant;
import http.ui.MvpDialog;

/**
 * @author lxc
 */
public class HttpAction extends BaseAnAction {
	
	@Override
	public void actionPerformed(AnActionEvent e) {
		super.actionPerformed(e);
		// 所选项目
		BaseConstant.PROJECT = e.getProject();
		//获取选中的文件
		BaseConstant.VIRTUAL_FILE = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
		
		// 信息输入框
		MvpDialog jsonD = new MvpDialog();
		// 没有相对位置
		jsonD.setLocationRelativeTo(null);
		jsonD.setSize(500, 400);
		jsonD.setVisible(true);
	}
	
}
