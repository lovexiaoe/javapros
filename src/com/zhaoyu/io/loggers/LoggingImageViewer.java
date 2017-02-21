package com.zhaoyu.io.loggers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.OutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * һ��ͼƬԤ���޸ĵĳ��򣬲���ӡ��Ӧ�¼�����־��
 *
 * @author xiaoE
 *
 */
public class LoggingImageViewer {
	public static void main(String[] args) {
		// System.getProperty��ȡϵͳ������ֵ��
		// if�ж�ȷ�������û�������Զ������־���á�
		if (System.getProperty("java.util.logging.config.class") == null
				&& System.getProperty("java.util.logging.config.file") == null) {
			try {
				Logger.getLogger("com.zhaoyu.corejava").setLevel(Level.ALL);
				final int LOG_ROTATION_COUNT = 10;
				// �����ļ�������Ϊѭ���ļ���������%h/LoggingImageViewer.log��%hΪ�û���Ŀ¼��
				Handler handler = new FileHandler("%h/LoggingImageViewer.log", 0, LOG_ROTATION_COUNT);
				// Ϊ��־����ļ���������
				Logger.getLogger("com.zhaoyu.corejava").addHandler(handler);
			} catch (Exception e) {
				Logger.getLogger("com.zhaoyu.corejava").log(Level.SEVERE, "Can't create log file handler", e);
			}
		}

		// awt�ǵ��߳�ģʽ��,������¼��߳���ӵ�awt�¼������߳���ȥ��
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Handler windowHandler = new WindowHandler();
				windowHandler.setLevel(Level.ALL);
				// Ϊ��־��Ӵ��ڴ�������
				Logger.getLogger("com.zhaoyu.corejava").addHandler(windowHandler);

				JFrame frame = new ImageViewerFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				Logger.getLogger("com.zhaoyu.corejava").fine("Showing frame");
				frame.setVisible(true);
			}
		});
	}

}

/**
 * ��ʾͼƬ�Ŀ�ܡ�
 *
 * @author xiaoE
 *
 */
class ImageViewerFrame extends JFrame {

	private JLabel label;
	private static Logger logger = Logger.getLogger("com.zhaoyu.corejava");
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 400;

	private class FileOpenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// �Է������и��٣����뷽��ʱ��ʾ��Ϣ������ʾ�ַ�����ENTRY��
			logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", event);

			// �����ļ�ѡ����
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));

			// ����������.gif�������ļ���
			chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				@Override
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
				}

				@Override
				public String getDescription() {
					return "GIF Images";
				}

			});

			// ��ʾ�ļ�ѡ����ѡ��
			int r = chooser.showOpenDialog(ImageViewerFrame.this);

			// ���ͼƬ�ļ������գ�������Ϊlabel��ͼ�ꡣ
			if (r == JFileChooser.APPROVE_OPTION) {
				String name = chooser.getSelectedFile().getPath();
				logger.log(Level.FINE, "Reading file {0}", name);
				// logp���������ִ�й������Ż����Ͳ��ܵ�׼ȷ����Ϣ�ˣ���ʱ �����Ե���logp������
				logger.logp(Level.FINE, "FileOpenListener", "actionPerformed", "Reading file {0}", name);
				label.setIcon(new ImageIcon(name));
			} else {
				logger.fine("File open dialog canceled.");
			}
			// �Է������и��٣��˳�����ʱ��ʾ��Ϣ������ʾ�ַ�����RETURN��
			logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed");
		}
	}

	public ImageViewerFrame() {
		logger.entering("ImageViewerFrame", "<init>");
		setTitle("LoggingImageViewer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem openItem = new JMenuItem("Open");
		menu.add(openItem);
		openItem.addActionListener(new FileOpenListener());

		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.fine("Exiting.");
				System.exit(0);
			}
		});

		// ʹ��һ��labelȥ��ʾͼƬ
		label = new JLabel();
		add(label);
		logger.exiting("ImageViewerFrame", "<init>");
	}

}

/**
 * �Զ����ı���������������һ��window����ʾ��־��¼
 *
 * @author xiaoE
 *
 */
class WindowHandler extends StreamHandler {

	private JFrame frame;

	public WindowHandler() {
		frame = new JFrame();
		final JTextArea output = new JTextArea();
		output.setEditable(false);
		frame.setSize(200, 200);
		frame.add(new JScrollPane(output));
		frame.setFocusableWindowState(false);
		frame.setVisible(true);
		setOutputStream(new OutputStream() {

			@Override
			public void write(int b) {

			}

			@Override
			public void write(byte[] b, int off, int len) {
				output.append(new String(b, off, len));
			}
		});
	}

	@Override
	public void publish(LogRecord record) {
		if (!frame.isVisible()) {
			return;
		}
		super.publish(record);
		flush();
	}

}
