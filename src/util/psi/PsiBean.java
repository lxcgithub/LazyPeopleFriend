package util.psi;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

/**
 * @author lxc
 */
public class PsiBean {
	
	/**
	 * file 文件
	 */
	PsiFile psiFile;
	
	/**
	 * class 文件
	 */
	PsiClass psiClass;
	
	public PsiFile getPsiFile() {
		return psiFile;
	}
	
	public void setPsiFile(PsiFile psiFile) {
		this.psiFile = psiFile;
	}
	
	public PsiClass getPsiClass() {
		return psiClass;
	}
	
	public void setPsiClass(PsiClass psiClass) {
		this.psiClass = psiClass;
	}
}
