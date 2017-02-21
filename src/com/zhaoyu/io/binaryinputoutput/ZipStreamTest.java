package com.zhaoyu.io.binaryinputoutput;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Zip�ĵ�
 * Zipͨ����ѹ����ʽ�洢һ�����߶���ļ���ÿ��zip�ļ���������һ��ͷ��Ϣ�����ͷ��Ϣ�������ļ����ơ�ʹ�õ�ѹ����ʽ�ȡ���java��ʹ��ZipInputStream����ȡZIP�ĵ���
 * �ĵ��а����˶���������getNextEntry����һ��������Щ���ZipEntry���͵Ķ���
 * ZipInputStream��read������������ǰ��Ľ�βʱ����-1������������zip�ļ��Ľ�β����Ȼ����������closeEntry��������һ�
 *
 * ZIP������ͨ�����ļ����ƻ�ʱ�׳�ZipException��
 *
 * Ҫд����ZIP�ļ�������ʹ��ZipOutputStream�����ڷ���ZIP�ļ��е�ÿһ���Ӧ�ô���һ��ZipEntry���󡣵���ZipOutputStream��putNextEntry��������ʼд�����ļ���
 * �����ļ����ݷ��͵�ZIP���С������ʱ����Ҫ����closeEntry��
 *
 * JAR�ļ��ǰ������嵥���Zip�ļ��������ʹ��JarInputStream��JarOutputStream����д�嵥�
 */

/**
 * �ó���˵����Zip����ʹ�á�
 *
 * �������һ��Zip��ʽ��ѹ���ļ��������ı��ļ�������������ı��� ����ʾ�����ݡ����ѹ���ļ��а������������ı���ʽ���ļ�����ᱨ��
 * word�ļ�����ĳ��ԭ�����Ϊ���롣txt���Ľ���������
 *
 * @author xiaoE
 *
 */
public class ZipStreamTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				ZipTestFrame frame = new ZipTestFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class ZipTestFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 300;
	private JComboBox fileCombo;
	private JTextArea fileText;
	private String zipName;

	public ZipTestFrame() {
		setTitle("ZipStreamTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");

		JMenuItem openItem = new JMenuItem("Open");
		menu.add(openItem);
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("D:\\javatest\\ziptest"));
				int r = chooser.showOpenDialog(ZipTestFrame.this);
				if (r == JFileChooser.APPROVE_OPTION) {
					zipName = chooser.getSelectedFile().getPath();
					fileCombo.removeAllItems();
					scanZipFile();
				}
			}

		});

		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		menuBar.add(menu);
		setJMenuBar(menuBar);

		fileText = new JTextArea();
		fileCombo = new JComboBox();
		fileCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadZipFile((String) fileCombo.getSelectedItem());
			}
		});

		add(fileCombo, BorderLayout.SOUTH);
		add(new JScrollPane(fileText), BorderLayout.CENTER);
	}

	/**
	 * ������������ѡ������ʾѹ���ļ������б�
	 */
	public void scanZipFile() {
		new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				ZipInputStream zin = new ZipInputStream(new FileInputStream(zipName));
				ZipEntry entry;
				while ((entry = zin.getNextEntry()) != null) {
					// ��process�����������ݡ����﷢�͵���ѹ��������ơ�
					publish(entry.getName());
					zin.closeEntry();
				}
				zin.close();
				return null;
			}

			@Override
			protected void process(List<String> names) {
				// ��������������ļ����ơ�
				for (String name : names) {
					fileCombo.addItem(name);
				}
			}
		}.execute();
	}

	public void loadZipFile(final String name) {
		fileCombo.setEnabled(false);
		fileText.setText("");
		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					ZipInputStream zin = new ZipInputStream(new FileInputStream(zipName));
					ZipEntry entry;
					// �����ļ����ƣ�ʹ�ı�����ض�Ӧ�ļ������ݡ�
					while ((entry = zin.getNextEntry()) != null) {
						if (entry.getName().equals(name)) {
							Scanner in = new Scanner(zin);
							while (in.hasNextLine()) {
								fileText.append(in.nextLine());
								fileText.append("\n");
							}
						}
						zin.closeEntry();
					}
					zin.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void done() {
				fileCombo.setEnabled(true);
			}

		}.execute();
	}
}