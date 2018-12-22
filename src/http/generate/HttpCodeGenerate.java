package http.generate;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import http.constant.HttpConstant;
import util.FormatUtil;
import util.StringUtils;

/**
 *
 * @author lxc
 */
public class HttpCodeGenerate {
	
	private String path;
	private String filedName;
	
	/**
	 * 创建 HttpCodeGenerate 方法
	 */
	public String createJsonString(String url) {
		
		// 解析网址 =》 Entity
		String parameterPart = url.split("\\?")[1];
		// 分割成参数
		String[] parameters = parameterPart.split("&");
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("{");
		for (String parameter : parameters) {
			// Key 组装:
			jsonString.append("\"" + parameter.split("=")[0] + "\"");
			jsonString.append(":");
			
			// Value 组装:
			// Value 值为空:
			if (parameter.split("=").length == 1) {
				
				jsonString.append("\"" + "\"");
				jsonString.append(",");
				
				// Value 值不为空:
			} else {
				
				// Value 值判断是否为数字:
				if (FormatUtil.isNumber(parameter.split("=")[1])) {
					jsonString.append(parameter.split("=")[1]);
					jsonString.append(",");
				} else {
					jsonString.append("\"" + parameter.split("=")[1] + "\"");
					jsonString.append(",");
				}
				
			}
		}
		jsonString.deleteCharAt(jsonString.length() - 1);
		jsonString.append("}");
		return jsonString.toString();
	}
	
	/**
	 * 截取请求常量
	 */
	public void createHttpConstant(String url, PsiClass mClass, PsiElementFactory mFactory) {
		
		if (HttpConstant.REQUEST_METHOD.equals(HttpConstant.GET)){
			createGetHttpConstant(url, mClass, mFactory);
		} else if (HttpConstant.REQUEST_METHOD.equals(HttpConstant.POST)){
			createPostHttpConstant(url, mClass, mFactory);
		}
	}
	
	/**
	 * 截取 get 请求常量
	 */
	public void createGetHttpConstant(String url, PsiClass mClass, PsiElementFactory mFactory) {
		
		// 解析网址 =》 Entity
		String parameterPart = url.split("\\?")[1];
		String interfacePart = url.split("\\?")[0];
		// 分割成参数
		String[] parameters = parameterPart.split("&");
		
		StringBuilder interfaceString = new StringBuilder();
		// 方法注释
		interfaceString.append("/** \n").append("* ").append( HttpConstant.ANNOTATION +"接口常量\n").append("*/ \n");
		interfaceString.append(""+"public static String " +
				FormatUtil.underscoreName(HttpConstant.METHOD_NAME).toUpperCase() + " = \"" +
				getHttpPath(interfacePart) + "\" ;");
		
		if (mClass.findFieldByName(FormatUtil.underscoreName(HttpConstant.METHOD_NAME).toUpperCase(), false) == null){
			mClass.add(mFactory.createFieldFromText(interfaceString.toString(), mClass));
		}
		
		
		for (int i = 0; i < parameters.length; i++) {
			
			StringBuilder jsonString = new StringBuilder();
			jsonString.append("public static String ");
			jsonString.append(FormatUtil.underscoreName(parameters[i].split("=")[0]).toUpperCase() + " = ");
			// 常量组装:
			if (i == 0) {
				jsonString.append("\"?" + parameters[i].split("=")[0] + "=\"" + ";");
			} else {
				jsonString.append("\"&" + parameters[i].split("=")[0] + "=\"" + ";");
			}
			filedName = FormatUtil.underscoreName(parameters[i].split("=")[0]).toUpperCase();
			if (mClass.findFieldByName(filedName, false) == null){
				mClass.add(mFactory.createFieldFromText(jsonString.toString(), mClass));
			}
		}
	}
	
	/**
	 * 截取 post 请求常量
	 */
	public void createPostHttpConstant(String url, PsiClass mClass, PsiElementFactory mFactory) {
		
		StringBuilder interfaceString = new StringBuilder();
		// 方法注释
		interfaceString.append("/** \n").append("* ").append( HttpConstant.ANNOTATION +"接口常量\n").append("*/ \n");
		interfaceString.append(""+"public static String " +
				FormatUtil.underscoreName(HttpConstant.METHOD_NAME).toUpperCase() + " = \"" +
				getHttpPath(url) + "\" ;");
		
		if (mClass.findFieldByName(FormatUtil.underscoreName(HttpConstant.METHOD_NAME).toUpperCase(), false) == null){
			mClass.add(mFactory.createFieldFromText(interfaceString.toString(), mClass));
		}
	}
	
	/**
	 * 拼接 get url
	 */
	public String createGetHttpUrl(String url, String constantClassName) {
		// 解析网址 =》 Entity
		String parameterPart = url.split("\\?")[1];
		// 分割成参数
		String[] parameters = parameterPart.split("&");
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("\t\t" + "String url =  GlobalConstant.PUBLIC_URL + " + constantClassName + FormatUtil.underscoreName(HttpConstant.METHOD_NAME) + " \n+ ");
		for (int i = 0; i < parameters.length; i++) {
			jsonString.append(constantClassName + FormatUtil.underscoreName(parameters[i].split("=")[0]) + " + ");
			jsonString.append("requestBean.get" + StringUtils.toUpperTheFirstLetter(parameters[i].split("=")[0]) + "()" + " + ");
			if (i > 0 && (i + 1) % 2 == 0) {
				jsonString.append("\n");
			}
		}
		jsonString.deleteCharAt(jsonString.length() - 2);
		return jsonString.append(";").toString();
	}
	
	/**
	 * 拼接 post url
	 */
	public String createPostHttpUrl(String url, String constantClassName) {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("\t\t" + "String url =  GlobalConstant.PUBLIC_URL + " + constantClassName + FormatUtil.underscoreName(HttpConstant.METHOD_NAME));
		return jsonString.append(";").toString();
	}
	
	/**
	 * 获取 Http Path
	 * @param url url
	 * @return
	 */
	public String getHttpPath(String url) {
		// 根据 ":" 分割，根据 "/" 分割。
		url = url.split("//")[1];
		String regex = "[a-zA-Z][a-zA-Z/0-9.]{1,}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		// 匹配一次，调用一次
		matcher.find();
		path = matcher.group(0);
		return path;
	}
}
