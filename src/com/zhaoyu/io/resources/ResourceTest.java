package com.zhaoyu.io.resources;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * 这个类用于说明java读取资源文件，gif和txt并通过用户界面显示。
 * 可以将该类及用到的资源文件导出为jar，并指定Main-class。
 * 也可以在ManiFest文件中指定。
 * 然后使用java -jar命令运行该包。
 *
 *
 * @author xiaoE
 *
 */
public class ResourceTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new ResourceTestFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class ResourceTestFrame extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ResourceTestFrame() {
		setTitle("ResourceTest");
		setSize(300, 400);
		// 读取图片,动态gif设置成图标，程序运行后无反映。
		URL aboutURL = getClass().getResource("about1.jpg");
		Image img = Toolkit.getDefaultToolkit().getImage(aboutURL);
		setIconImage(img);

		JTextArea textArea = new JTextArea();
		// 读取文件信息，返回流。
		InputStream stream = getClass().getResourceAsStream("about.txt");
		Scanner in = new Scanner(stream);
		while (in.hasNext()) {
			textArea.append(in.nextLine() + "\n");
		}
		add(textArea);
	}
}