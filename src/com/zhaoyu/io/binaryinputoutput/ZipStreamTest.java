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
 * Zip文档
 * Zip通常以压缩格式存储一个或者多个文件，每个zip文件都包含了一个头信息，这个头信息包含了文件名称、使用的压缩方式等。在java中使用ZipInputStream来读取ZIP文档。
 * 文档中包含了多个单独的项，getNextEntry返回一个描述这些项的ZipEntry类型的对象。
 * ZipInputStream的read方法在碰到当前项的结尾时返回-1（而不是整个zip文件的结尾），然后你必须调用closeEntry来读入下一项。
 *
 * ZIP输入流通常在文件被破坏时抛出ZipException。
 *
 * 要写出到ZIP文件，可以使用ZipOutputStream，对于放入ZIP文件中的每一项，都应该创建一个ZipEntry对象。调用ZipOutputStream的putNextEntry方法来开始写出新文件，
 * 并将文件数据发送到ZIP流中。当完成时，需要调用closeEntry。
 *
 * JAR文件是包含了清单项的Zip文件，你可以使用JarInputStream和JarOutputStream来读写清单项。
 */

/**
 * 该程序说明了Zip流的使用。
 *
 * 程序解析一个Zip格式的压缩文件，并对文本文件进行浏览，在文本框 中显示其内容。如果压缩文件中包含了其它非文本格式的文件，则会报错。
 * word文件由于某种原因解析为乱码。txt中文解析正常。
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
	 * 用于在下拉框选择中显示压缩文件名称列表。
	 */
	public void scanZipFile() {
		new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				ZipInputStream zin = new ZipInputStream(new FileInputStream(zipName));
				ZipEntry entry;
				while ((entry = zin.getNextEntry()) != null) {
					// 向process方法发送数据。这里发送的是压缩项的名称。
					publish(entry.getName());
					zin.closeEntry();
				}
				zin.close();
				return null;
			}

			@Override
			protected void process(List<String> names) {
				// 向下拉框中添加文件名称。
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
					// 根据文件名称，使文本框加载对应文件的内容。
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