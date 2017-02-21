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
 * 一个图片预览修改的程序，并打印对应事件的日志。
 *
 * @author xiaoE
 *
 */
public class LoggingImageViewer {
	public static void main(String[] args) {
		// System.getProperty获取系统的属性值。
		// if判断确认虚拟机没有设置自定义的日志配置。
		if (System.getProperty("java.util.logging.config.class") == null
				&& System.getProperty("java.util.logging.config.file") == null) {
			try {
				Logger.getLogger("com.zhaoyu.corejava").setLevel(Level.ALL);
				final int LOG_ROTATION_COUNT = 10;
				// 设置文件处理器为循环文件处理器。%h/LoggingImageViewer.log，%h为用户主目录，
				Handler handler = new FileHandler("%h/LoggingImageViewer.log", 0, LOG_ROTATION_COUNT);
				// 为日志添加文件处理器。
				Logger.getLogger("com.zhaoyu.corejava").addHandler(handler);
			} catch (Exception e) {
				Logger.getLogger("com.zhaoyu.corejava").log(Level.SEVERE, "Can't create log file handler", e);
			}
		}

		// awt是单线程模式的,把这个事件线程添加到awt事件处理线程中去。
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Handler windowHandler = new WindowHandler();
				windowHandler.setLevel(Level.ALL);
				// 为日志添加窗口处理器。
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
 * 显示图片的框架。
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
			// 对方法进行跟踪，进入方法时显示信息，并显示字符串“ENTRY”
			logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", event);

			// 设置文件选择器
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));

			// 接收所有以.gif结束的文件。
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

			// 显示文件选择器选框。
			int r = chooser.showOpenDialog(ImageViewerFrame.this);

			// 如果图片文件被接收，设置它为label的图标。
			if (r == JFileChooser.APPROVE_OPTION) {
				String name = chooser.getSelectedFile().getPath();
				logger.log(Level.FINE, "Reading file {0}", name);
				// logp当虚拟机对执行过程了优化，就不能得准确的信息了，此时 ，可以调用logp方法。
				logger.logp(Level.FINE, "FileOpenListener", "actionPerformed", "Reading file {0}", name);
				label.setIcon(new ImageIcon(name));
			} else {
				logger.fine("File open dialog canceled.");
			}
			// 对方法进行跟踪，退出方法时显示信息，并显示字符串“RETURN”
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

		// 使用一个label去显示图片
		label = new JLabel();
		add(label);
		logger.exiting("ImageViewerFrame", "<init>");
	}

}

/**
 * 自定义文本区流处理器。在一个window中显示日志记录
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
