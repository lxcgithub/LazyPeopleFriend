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
public class MvpViewGenerate extends MethodGenerate {
	
	
	/**
	 * 构造函数
	 *
	 * @param selectGroup
	 * @param mProject
	 */
	public MvpViewGenerate(VirtualFile selectGroup, Project mProject) {
		super(selectGroup, mProject);
	}
	
	@Override
	public void createMethodBody(String packageName, String name, String chineseName,
								 PsiClass mClass,
								 PsiElementFactory mFactory) {
		
		StringBuilder requestMethod = new StringBuilder();
		String methodName = "loading" + name + "DataCompleted";
		method(name,chineseName, mClass, mFactory, requestMethod, methodName);
		
	}
	
	private void method(String name,String chineseName, PsiClass mClass, PsiElementFactory mFactory,
						StringBuilder requestMethod, String methodName) {
		// 方法注释
		requestMethod.append(MethodGenerateUtils.appendAnnotation("获取" + chineseName + "返回数据"));
		if (HttpConstant.RESPONSE.equals(HttpConstant.OBJECT)){
			// 方法体
			requestMethod.append("public void " + methodName + "("
					+ importClassPath(TemplateConstant.ENTITY, name) + "ResponseBean responseBean);");
		} else if (HttpConstant.RESPONSE.equals(HttpConstant.LIST)){
			// 方法体
			requestMethod.append("public void " + methodName + "(java.util.List<"
					+ importClassPath(TemplateConstant.ENTITY, name) + "ResponseBean> responseList);");
		}
		
		if (checkMethodExist(methodName)) {
			mClass.add(mFactory.createMethodFromText(requestMethod.toString(), mClass));
		}
	}
	
}
