package http.generate.base;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

import constant.BaseConstant;
import format.gsonformat.intellij.ConvertBridge;
import http.constant.HttpConstant;
import http.generate.HttpCodeGenerate;
import util.psi.PsiBean;
import util.psi.PsiUtils;

/**
 * @author lxc
 */
public abstract class MethodGenerate {
	
	public VirtualFile selectGroup;
	public String name;
	public String packageName;
	public PsiClass mClass;
	public PsiFile mFile;
	public Project mProject;
	public PsiElementFactory mFactory;
	public PsiUtils psiUtil;
	public String mSelectPath;
	private ConvertBridge.Operator operator;
	/**
	 * 当前包下路径
	 */
	private String currentPackagePath;
	
	/**
	 * 类名后缀
	 */
	private String classSuffix;
	private String jsonString;
	
	/**
	 * 构造函数
	 *
	 * @param selectGroup
	 * @param mProject
	 */
	public MethodGenerate(VirtualFile selectGroup, Project mProject) {
		this.mProject = mProject;
		this.selectGroup = selectGroup;
		// 获取 File 和 Class 的类
		psiUtil = new PsiUtils(selectGroup, mProject);
		
		//获取当前包名
		this.packageName = BaseConstant.PACKAGE_NAME;
		// 写入工厂
		mFactory = JavaPsiFacade.getElementFactory(mProject);
	}
	
	/**
	 * 获取 类 与 文件
	 *
	 * @param packagePath 相对包路径
	 */
	public MethodGenerate getClassAndFile(String packagePath) {
		// 根据路径获取后缀
		String classSuffix =
				packagePath.split("/")[1].substring(0, 1).toUpperCase() + packagePath.substring(2);
		return getClassAndFile(packagePath, classSuffix);
	}
	
	public MethodGenerate getClassAndFile(String packagePath, String classSuffix) {
		//获取 Class 和 File
		this.currentPackagePath = packagePath;
		this.classSuffix = classSuffix;
		PsiBean constantBean = psiUtil.getClassAndFile(packagePath, classSuffix);
		String selectPath = psiUtil.getSelectPath();
		this.mSelectPath = selectPath;
		mClass = constantBean.getPsiClass();
		mFile = constantBean.getPsiFile();
		return this;
	}
	
	/**
	 * 创建方法
	 *
	 * @param name        接口名
	 * @param chineseName 中文注释
	 */
	public void createMethod(String name, String chineseName) {
		createMethodBody(packageName, name, chineseName, mClass, mFactory);
		formattingCode();
	}
	
	/**
	 * 检查方法是否存在
	 */
	public boolean checkMethodExist(String methodName) {
		return (mClass.findMethodsByName(methodName, false).length == 0);
	}
	
	/**
	 * 创建方法体
	 */
	public abstract void createMethodBody(String packageName, String name, String chineseName,
										  PsiClass mClass,
										  PsiElementFactory mFactory);
	
	/**
	 * 格式化代码
	 */
	public void formattingCode() {
		JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(mProject);
		styleManager.optimizeImports(mFile);
		styleManager.shortenClassReferences(mClass);
		new ReformatCodeProcessor(mProject, mClass.getContainingFile(), null, false).runWithoutProgress();
	}
	
	/**
	 * 获取选中路径
	 */
	
	public String getSelectPath() {
		return mSelectPath;
	}
	
	/**
	 * 输入路径导入包路径
	 */
	public String importClassPath(String path, String name) {
		return getSelectPath() + path.replace("/", ".") + "." + name;
	}
	
	/**
	 * 解析 json 数据
	 */
	public void parseJson(String name, String jsonInfo) {
		if (HttpConstant.REQUEST_METHOD.equals(HttpConstant.GET)) {
			jsonString = new HttpCodeGenerate().createJsonString(jsonInfo);
		} else {
			jsonString = jsonInfo;
		}
		operator = new ConvertBridge.Operator() {
			@Override
			public void showError(ConvertBridge.Error err) {
			
			}
			
			@Override
			public void dispose() {
			
			}
			
			@Override
			public void setVisible(boolean visible) {
			
			}
			
			@Override
			public void setErrorInfo(String error) {
			
			}
			
			@Override
			public void cleanErrorInfo() {
			
			}
		};
			new ConvertBridge(operator, jsonString, mFile, mProject, mClass,
					mClass, importClassPath(currentPackagePath, name + "ResponseBean")).run();
	}
	
	/**
	 * 设置类前缀
	 */
	public MethodGenerate setClassPrefix(String prefix) {
		psiUtil.setClassPrefix(prefix);
		return this;
	}
}
