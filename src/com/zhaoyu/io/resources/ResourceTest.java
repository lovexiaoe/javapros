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
 * ���������˵��java��ȡ��Դ�ļ���gif��txt��ͨ���û�������ʾ��
 * ���Խ����༰�õ�����Դ�ļ�����Ϊjar����ָ��Main-class��
 * Ҳ������ManiFest�ļ���ָ����
 * Ȼ��ʹ��java -jar�������иð���
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
		// ��ȡͼƬ,��̬gif���ó�ͼ�꣬�������к��޷�ӳ��
		URL aboutURL = getClass().getResource("about1.jpg");
		Image img = Toolkit.getDefaultToolkit().getImage(aboutURL);
		setIconImage(img);

		JTextArea textArea = new JTextArea();
		// ��ȡ�ļ���Ϣ����������
		InputStream stream = getClass().getResourceAsStream("about.txt");
		Scanner in = new Scanner(stream);
		while (in.hasNext()) {
			textArea.append(in.nextLine() + "\n");
		}
		add(textArea);
	}
}