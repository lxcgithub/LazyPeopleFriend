package http;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import constant.BaseConstant;
import http.constant.HttpConstant;
import http.generate.ActivityGenerate;
import http.generate.MvpViewGenerate;
import http.generate.PresenterGenerate;
import mvp.constant.TemplateConstant;
import util.ClassTemplateUtils;
import util.psi.PsiUtils;

/**
 * @author lxc
 */
public class GenerateHttpCode {
	
	private Project project;
	private VirtualFile file;
	private String url;
	private String methodName;
	private String annotationName;
	private String requestBody;
	
	public void createMethodHttp() {
		this.project = BaseConstant.PROJECT;
		this.file = BaseConstant.VIRTUAL_FILE;
		this.url = HttpConstant.URL;
		this.methodName = HttpConstant.METHOD_NAME;
		this.annotationName = HttpConstant.ANNOTATION;
		this.requestBody = HttpConstant.REQUEST_BODY;
		
		// 拼接全目录
		String pathname = BaseConstant.VIRTUAL_FILE.getPath();
		HttpConstant.PATH_NAME = pathname;
		
		// Java路径名
		String javaPathname = pathname.substring(pathname.indexOf("java")+5).replace("/", ".");
		HttpConstant.JAVA_PATH_NAME = javaPathname;
		
		// 实体类创建
		if (new PsiUtils(BaseConstant.VIRTUAL_FILE,BaseConstant.PROJECT).setClassPrefix(methodName).getClassByName(
				TemplateConstant.ENTITY,"ResponseBean") == null){
			
			ClassTemplateUtils utils = new ClassTemplateUtils(HttpConstant.METHOD_NAME,pathname,javaPathname);
			
			utils.convertContent("ResponseBean.txt", TemplateConstant.ENTITY);
			utils.convertContent("RequestBean.txt", TemplateConstant.ENTITY);
			
			// 创建完成后刷新项目
			assert project != null;
			project.getBaseDir().refresh(false, true);
		}
		
		// 添加方法到 Presenter 中
		new PresenterGenerate(BaseConstant.VIRTUAL_FILE, project).getClassAndFile(TemplateConstant.PRESENTER,
				"Presenter").createMethod(methodName, annotationName);
		
		// 添加方法到 V 层接口中
		new MvpViewGenerate(BaseConstant.VIRTUAL_FILE, project).getClassAndFile(TemplateConstant.MVPVIEW,
				"View").createMethod(methodName, annotationName);
		
		// 添加方法到 Activity 中
		new ActivityGenerate(BaseConstant.VIRTUAL_FILE, project).getClassAndFile(TemplateConstant.UI_ACTIVITY,
				"Activity").createMethod(methodName, annotationName);
		
		// 添加常量到网络接口列表中中
	}
}
