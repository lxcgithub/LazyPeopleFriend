package http.ui;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import http.GenerateHttpCode;
import http.constant.HttpConstant;
import util.StringUtils;

public class MvpDialog extends JFrame implements ActionListener {
	
	private VirtualFile selectGroup;
	private Project project;
	private JPanel contentPane;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField nameT;
	private JTextField urlT;
	private JTextField annotationT;
	private JTextField requestMethodT;
	private JTextPane postBodyT;
	private JRadioButton getRadioButton;
	private JRadioButton postRadioButton;
	private JRadioButton listRadioButton;
	private JRadioButton objectRadioButton;
	private String requestMethod;
	private String mRequest = HttpConstant.POST;
	private String mResponse = HttpConstant.OBJECT;
	
	public MvpDialog() {
		setContentPane(contentPane);
		setTitle("MvpFormat");
		// 设置默认回车响应按钮
		getRootPane().setDefaultButton(okButton);
		this.setAlwaysOnTop(true);
		initListener();
		// 按钮组
		ButtonGroup bg=new ButtonGroup();
		bg.add(getRadioButton);
		bg.add(postRadioButton);
		// 按钮组
		ButtonGroup bg2=new ButtonGroup();
		bg2.add(listRadioButton);
		bg2.add(objectRadioButton);
	}
	
	private void initListener() {
		getRadioButton.addActionListener(this);
		postRadioButton.addActionListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		listRadioButton.addActionListener(this);
		objectRadioButton.addActionListener(this);
		
		// 窗体上关闭按钮响应
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		// 窗口关闭监听
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});
		
		// 监听 Esc 键
		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void onOK() {
		
		WriteCommandAction.runWriteCommandAction(project, new Runnable() {
			
			@Override
			public void run() {
				HttpConstant.URL = urlT.getText();
				HttpConstant.REQUEST_BODY = postBodyT.getText();
				HttpConstant.METHOD_NAME = StringUtils.toUpperTheFirstLetter(nameT.getText());
				HttpConstant.ANNOTATION = annotationT.getText();
				HttpConstant.REQUEST_METHOD = mRequest;
				HttpConstant.RESPONSE = mResponse;
				new GenerateHttpCode().createMethodHttp();
			}
		});
		// 创建完成后刷新项目
		assert project != null;
		project.getBaseDir().refresh(false, true);
		
		dispose();
	}
	
	private void onCancel() {
		dispose();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Ok":
				onOK();
				break;
			case "Cancel":
				onCancel();
				break;
			case "Get":
				mRequest = HttpConstant.GET;
				break;
			case "Post":
				mRequest = HttpConstant.POST;
				break;
			case "List":
				mResponse = HttpConstant.LIST;
				break;
			case "Object":
				mResponse = HttpConstant.OBJECT;
				break;
			default:
				break;
		}
	}
	
}
