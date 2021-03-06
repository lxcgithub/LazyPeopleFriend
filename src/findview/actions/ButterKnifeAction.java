package findview.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.psi.xml.XmlFile;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import findview.constant.Constant;
import findview.entitys.Element;
import findview.utils.CreateMethodCreator;
import findview.utils.Util;
import findview.views.ButterKnifeDialog;
import findview.views.GenerateDialog;

public class ButterKnifeAction extends AnAction {
    private ButterKnifeDialog mDialog;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取project
        Project project = e.getProject();
        // 判断 Project 不为空
        if (project == null) {
            return;
        }
        // 获取选中内容
        final Editor mEditor = e.getData(PlatformDataKeys.EDITOR);
        if (mEditor == null) {
            return;
        }
        // 获取选中数字
        String mSelectedText = mEditor.getSelectionModel().getSelectedText();
        // 未选中布局内容，显示dialog
        int popupTime = 5;
        // 选择文字为空
        if (StringUtils.isEmpty(mSelectedText)) {
            mSelectedText = Messages.showInputDialog(project,
                    Constant.actions.selectedMessage,
                    Constant.actions.selectedTitle,
                    Messages.getInformationIcon());
            if (StringUtils.isEmpty(mSelectedText)) {
                Util.showPopupBalloon(mEditor, Constant.actions.selectedErrorNoName, popupTime);
                return;
            }
        }
        // 获取布局文件，通过FilenameIndex.getFilesByName获取
        // GlobalSearchScope.allScope(project)搜索整个项目
        PsiFile[] psiFiles = new PsiFile[0];
        psiFiles = FilenameIndex.getFilesByName(project,
                mSelectedText + Constant.selectedTextSUFFIX,
                GlobalSearchScope.allScope(project));
        // 找不到文件
        if (psiFiles.length <= 0) {
            Util.showPopupBalloon(mEditor, Constant.actions.selectedErrorNoSelected, popupTime);
            return;
        }
        // XML 中获取 ID 保存
        XmlFile xmlFile = (XmlFile) psiFiles[0];
        List<Element> elements = new ArrayList<>();
        Util.getIDsFromLayout(xmlFile, elements);
        // 将代码写入文件，不允许在主线程中进行实时的文件写入
        if (elements.size() == 0) {
            Util.showPopupBalloon(mEditor, Constant.actions.selectedErrorNoId, popupTime);
            return;
        }
        // 判断是否有onCreate/onCreateView方法
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(mEditor, project);
        PsiClass psiClass = Util.getTargetClass(mEditor, psiFile);
        if (psiClass == null) {
            Util.showPopupBalloon(mEditor, Constant.actions.selectedErrorNoPoint, popupTime);
            return;
        }
        // 判断是否有onCreate方法
        if (Util.isExtendsActivityOrActivityCompat(project, psiClass)
                && psiClass.findMethodsByName(Constant.psiMethodByOnCreate, false).length == 0) {
            // 写onCreate方法
            new CreateMethodCreator(mEditor, psiFile, psiClass, Constant.creatorCommandName,
                    mSelectedText, Constant.classTypeByActivity, true).execute();
            return;
        }
        // 判断是否有onCreateView方法
        if (Util.isExtendsFragmentOrFragmentV4(project, psiClass)
                && psiClass.findMethodsByName(Constant.psiMethodByOnCreateView, false).length == 0) {
            new CreateMethodCreator(mEditor, psiFile, psiClass, Constant.creatorCommandName,
                    mSelectedText, Constant.classTypeByFragment, true).execute();
            return;
        }
        // 有的话就创建变量和ButterKnife代码
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancelDialog();
        }
        mDialog = new ButterKnifeDialog(new GenerateDialog.Builder(elements.size())
                .setEditor(mEditor)
                .setProject(project)
                .setPsiFile(psiFile)
                .setClass(psiClass)
                .setElements(elements)
                .setSelectedText(mSelectedText)
                .setIsButterKnife(true));
        mDialog.showDialog();
    }
}
