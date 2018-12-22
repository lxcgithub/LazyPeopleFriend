package util.psi;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import constant.BaseConstant;
import util.StringUtils;

/**
 * @author lxc
 */
public class PsiUtils {
	
	private VirtualFile selectGroup;
	private Project mProject;
	private String classSuffix;
	private String classPrefix;
	
	public PsiUtils(VirtualFile selectGroup, Project mProject) {
		this.mProject = mProject;
		this.selectGroup = selectGroup;
	}
	
	/**
	 * 获取指定 Class
	 */
	public PsiClass getClassByName(String packagePath) {
		
		// 获取后缀
		String classSuffix =
				packagePath.split("/")[1].substring(0, 1).toUpperCase() + packagePath.substring(2);
		return getClassByName(packagePath, classSuffix);
	}
	
	/**
	 * 获取指定 Class
	 */
	public PsiClass getClassByName(String packagePath, String classSuffix) {
		
		String className = getSelectPath();
		if (classPrefix == null) {
			// 获取当前包名
			String currentPackageName = BaseConstant.PACKAGE_NAME;
			// 类名前缀
			classPrefix = StringUtils.toUpperTheFirstLetter(currentPackageName);
		}
		// 全路径包名
		className = className + packagePath.replace("/", ".") + "." + classPrefix + classSuffix;
		// 根据类的全限定名查询PsiClass，下面这个方法是查询Project域
		return JavaPsiFacade.getInstance(mProject).findClass(className,
				GlobalSearchScope.projectScope(mProject));
	}
	
	/**
	 * 获取指定 Java File
	 */
	public PsiFile getJavaFileByName(String classSuffix) {
		
		if (classPrefix == null) {
			// 获取当前包名
			String currentPackageName = BaseConstant.PACKAGE_NAME;
			// 类名前缀
			classPrefix = StringUtils.toUpperTheFirstLetter(currentPackageName);
		}
		// 全路径包名
		String className = classPrefix + classSuffix;
		PsiFile[] psiFiles = FilenameIndex.getFilesByName(mProject, className + ".java",
				GlobalSearchScope.allScope(mProject));
		return psiFiles[0];
	}
	
	/**
	 * 获取 class 和 file
	 */
	public PsiBean getClassAndFile(String packagePath) {
		PsiBean psiBean = new PsiBean();
		psiBean.setPsiClass(getClassByName(packagePath));
		// 根据路径获取后缀
		classSuffix =
				packagePath.split("/")[1].substring(0, 1).toUpperCase() + packagePath.substring(2);
		psiBean.setPsiFile(getJavaFileByName(classSuffix));
		return psiBean;
	}
	
	/**
	 * 获取 class 和 file
	 */
	public PsiBean getClassAndFile(String packagePath, String classSuffix) {
		PsiBean psiBean = new PsiBean();
		psiBean.setPsiClass(getClassByName(packagePath, classSuffix));
		psiBean.setPsiFile(getJavaFileByName(classSuffix));
		return psiBean;
	}
	
	/**
	 * 获取选中包路径
	 */
	public String getSelectPath() {
		// 所选中目录
		String path = selectGroup.getPath();
		// 截取包名：将前面的去除
		String selectPath = path.substring(path.indexOf("java") + 5).replace("/", ".");
		return selectPath;
	}
	
	/**
	 * 设置类前缀
	 */
	public PsiUtils setClassPrefix(String prefix) {
		this.classPrefix = prefix;
		return this;
	}
}
