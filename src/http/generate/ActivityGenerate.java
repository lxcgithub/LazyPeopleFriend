package http.generate;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;

import http.constant.HttpConstant;
import http.generate.base.MethodGenerate;
import mvp.constant.TemplateConstant;
import util.MethodGenerateUtils;

/**
 * @author lxc
 */
public class ActivityGenerate extends MethodGenerate {
	
	public ActivityGenerate(VirtualFile selectGroup, Project mProject) {
		super(selectGroup, mProject);
	}
	
	@Override
	public void createMethodBody(String packageName, String name, String annotation,
								 PsiClass mClass,
								 PsiElementFactory mFactory) {
		// 网络请求结果填充
		methodFill(name, annotation, mClass, mFactory);
		
//		methodPost(name, annotation, mClass, mFactory);
		formattingCode();
	}
	
	private void methodPost(String name, String chineseName, PsiClass mClass,
							PsiElementFactory mFactory) {
		if (HttpConstant.REQUEST_METHOD.equals(HttpConstant.POST)) {
			
			StringBuilder postMethod = new StringBuilder();
			String postMethodRequest = "post" + name + "Request";
			// 方法注释
			postMethod.append(MethodGenerateUtils.appendAnnotation("post " + chineseName +
					"请求拼装"));
			// 方法体
			postMethod.append(
					"public void " + postMethodRequest + "(" + importClassPath(TemplateConstant.ENTITY, name) +
							"RequestBean requestBean) {\n" +
							"\t\t// Automatically generated generate\n" +
							"\t}");
			if (checkMethodExist(postMethodRequest)) {
				mClass.add(mFactory.createMethodFromText(postMethod.toString(), mClass));
			}
		}
	}
	
	private void methodFill(String name, String chineseName, PsiClass mClass,
							PsiElementFactory mFactory) {
		StringBuilder requestMethod = new StringBuilder();
		String methodName = "loading" + name + "DataCompleted";
		
		// 方法注释
		requestMethod.append(MethodGenerateUtils.appendAnnotation(chineseName + "数据加载完成"));
		if (HttpConstant.RESPONSE.equals(HttpConstant.OBJECT)) {
			// 方法体
			requestMethod.append(
					"\t@Override\n" +
							"\tpublic void " + methodName + "(" + importClassPath(TemplateConstant.ENTITY, name) + "ResponseBean responseBean) {\n" +
							"\t\t// Automatically generated generate\n" +
							"\t}");
			if (checkMethodExist(methodName)) {
				mClass.add(mFactory.createMethodFromText(requestMethod.toString(), mClass));
			}
		} else if (HttpConstant.RESPONSE.equals(HttpConstant.LIST)) {
			// 方法体
			requestMethod.append(
					"\t@Override\n" +
							"\tpublic void " + methodName + "(java.util.List<" + importClassPath(TemplateConstant.ENTITY, name) + "ResponseBean> responseBean) {\n" +
							"\t\t// Automatically generated generate\n" +
							"\t}");
			if (checkMethodExist(methodName)) {
				mClass.add(mFactory.createMethodFromText(requestMethod.toString(), mClass));
			}
		}
		
	}
	
}
