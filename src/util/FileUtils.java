package util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lxc
 */
public class FileUtils {
	
	/**
	 * 读取 Stream 到文件
	 *
	 * @param inStream 输入流
	 * @return
	 * @throws Exception
	 */
	private byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
				System.out.println(new String(buffer));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outSteam.close();
			inStream.close();
		}
		return outSteam.toByteArray();
	}
	
	/**
	 * 读取文件内容
	 *
	 * @param filename 文件名
	 * @return
	 */
	public  String readFile(String filename) {
		InputStream in;
		// 项目绝对路径
		in = this.getClass().getResourceAsStream("/mvp/template/" + filename);
		String content = "";
		try {
			content = new String(readStream(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 内容写到文件
	 *
	 * @param content  内容
	 * @param filepath 文件路径
	 * @param filename 文件名
	 */
	public static void writeToFile(String content, String filepath, String filename) {
		try {
			// 文件夹创建
			File floder = new File(filepath);
			if (!floder.exists()) {
				floder.mkdirs();
			}
			
			// 文件创建
			File file = new File(filepath + "/" + filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
