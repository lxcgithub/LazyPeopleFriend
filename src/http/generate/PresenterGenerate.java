package http.generate;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;

import http.constant.HttpConstant;
import http.generate.base.MethodGenerate;
import mvp.constant.TemplateConstant;

/**
 * @author lxc
 */
public class PresenterGenerate extends MethodGenerate {
	
	/**
	 * 构造函数
	 *
	 * @param selectGroup
	 * @param mProject
	 */
	public PresenterGenerate(VirtualFile selectGroup, Project mProject) {
		super(selectGroup, mProject);
	}
	
	@Override
	public void createMethodBody(String packageName, String name, String chineseName,
								 PsiClass mClass,
								 PsiElementFactory mFactory) {
		
		StringBuilder requestMethod = new StringBuilder();
		
		String methodName = "loading" + name + "Data";
		
		// 方法注释
		requestMethod.append("/** \n").append("* ").append("加载" + chineseName + "接口数据").append("*/" +
				" \n");
		if (HttpConstant.RESPONSE.equals(HttpConstant.OBJECT)){
			// 方法体
			requestMethod.append("public void " + methodName + "(" + importClassPath(TemplateConstant.ENTITY, name) + "RequestBean requestBean) {\n" +
					"\t\t// Automatically generated generate\n" +
					"\t\tcom.joey.protocol.NetClient client = new com.joey.protocol.NetClient();\n" +
					"\t\tHashMap<String, "+ importClassPath(TemplateConstant.ENTITY, name) + "RequestBean"+"> map = new java.util.HashMap<>(20);\n" +
					"\t\tmap.put(\"Json\", requestBean);\n" +
					"\t\tclient.httpJsonPost(com.joeyzh.utils.AppConsts.Url.getDomainRoot(), com.alp.mes.utils.RequestMethod" +
					".PINSERT_OPERATION_LOG, map, new com.joey.protocol.JSONResponseListener<com.google.gson.JsonElement>(new " +
					"com.joey.ui.widget.JProgressDialog(mContext).setMessage(\"查询中中请稍等\")) {\n" +
					"\t\t\t\n" +
					"\t\t\t@Override\n" +
					"\t\t\tpublic void onResponse(JsonElement element, com.joey.protocol.ResponseError responseError) " +
					"{\n" +
					"\t\t\t\tcom.google.gson.Gson gson = new com.google.gson.Gson();\n" +
					"\t\t\t\t"+ importClassPath(TemplateConstant.ENTITY, name) +"ResponseBean response = gson.fromJson(element, "+ importClassPath(TemplateConstant.ENTITY, name) +"ResponseBean" +
					".class);\n" +
					"\t\t\t\tgetView()."+"loading" + name + "DataCompleted"+"(response);"+
					"\t\t\t}\n" +
					"\t\t\t\n" +
					"\t\t\t@Override\n" +
					"\t\t\tpublic void onError(ResponseError responseError) {\n" +
					"\t\t\t\tcom.joey.ui.widget.AlertMessage.show(mContext, responseError.getMessage());\n" +
					"\t\t\t}\n" +
					"\t\t});" +
					"\t}");
		} else if (HttpConstant.RESPONSE.equals(HttpConstant.LIST)){
			// 方法体
			requestMethod.append("public void " + methodName + "(" + importClassPath(TemplateConstant.ENTITY, name) + "RequestBean requestBean) {\n" +
					"\t\t// Automatically generated generate\n" +
					"\t\tcom.joey.protocol.NetClient client = new com.joey.protocol.NetClient();\n" +
					"\t\tHashMap<String, "+ importClassPath(TemplateConstant.ENTITY, name) + "RequestBean"+"> map = new java.util.HashMap<>(20);\n" +
					"\t\tmap.put(\"Json\", requestBean);\n" +
					"\t\tclient.httpJsonPost(com.joeyzh.utils.AppConsts.Url.getDomainRoot(), com.alp.mes.utils.RequestMethod" +
					".PINSERT_OPERATION_LOG, map, new com.joey.protocol.JSONResponseListener<com.google.gson.JsonElement>(new " +
					"com.joey.ui.widget.JProgressDialog(mContext).setMessage(\"查询中中请稍等\")) {\n" +
					"\t\t\t\n" +
					"\t\t\t@Override\n" +
					"\t\t\tpublic void onResponse(JsonElement element, com.joey.protocol.ResponseError responseError) " +
					"{\n" +
					"\t\t\t\tcom.google.gson.Gson gson = new com.google.gson.Gson();\n" +
					"\t\t\t\tjava.util.List<"+importClassPath(TemplateConstant.ENTITY, name) +"ResponseBean"+"> responseList=gson.fromJson(element,new " +
					"\t\t\t\tcom.google.gson.reflect.TypeToken<List<"+importClassPath(TemplateConstant.ENTITY, name) +"ResponseBean"+">>(){}.getType());\n" +
					"\t\t\t\tgetView()."+"loading" + name + "DataCompleted"+"(responseList);"+
					"\t\t\t}\n" +
					"\t\t\t\n" +
					"\t\t\t@Override\n" +
					"\t\t\tpublic void onError(ResponseError responseError) {\n" +
					"\t\t\t\tcom.joey.ui.widget.AlertMessage.show(mContext, responseError.getMessage());\n" +
					"\t\t\t}\n" +
					"\t\t});" +
					"\t}");
		}

		
		if (checkMethodExist(methodName)) {
			mClass.add(mFactory.createMethodFromText(requestMethod.toString(), mClass));
		}
	}
}
