package net.zhaoyu.javapros.j2se.xml.parsetest;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GridBagTest {
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				String filename = args.length == 0 ? "bin/com/zhaoyu/xml/parsetest/fontdialog.xml" : args[0];
				JFrame frame = new FontFrame(filename);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * 该框架包含一个字体选择框，字体选择框获取xml文档中的数据。
 *
 * @author xiaoE
 *
 */
class FontFrame extends JFrame {
	private GridBagPane gridbag;
	private JComboBox face;
	private JComboBox size;
	private JCheckBox bold;
	private JCheckBox italic;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 400;

	public FontFrame(String filename) {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("GridBagTest");

		gridbag = new GridBagPane(filename);
		add(gridbag);

		face = (JComboBox) gridbag.get("face");
		size = (JComboBox) gridbag.get("size");
		bold = (JCheckBox) gridbag.get("bold");
		italic = (JCheckBox) gridbag.get("italic");

		face.setModel(new DefaultComboBoxModel(new Object[] { "Serif", "SansSerif", "Monospaced", "Dialog",
				"DialogInput" }));
		size.setModel(new DefaultComboBoxModel(new Object[] { "8", "10", "12", "15", "18", "24", "36", "48" }));

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSample();
			}
		};

		face.addActionListener(listener);
		size.addActionListener(listener);
		bold.addActionListener(listener);
		italic.addActionListener(listener);
		setSample();
	}

	/**
	 * 根据选择的字体样式和大小重新设置文本框。
	 */
	public void setSample() {
		String fontFace = (String) face.getSelectedItem();
		int fontSize = Integer.parseInt((String) size.getSelectedItem());
		JTextArea sample = (JTextArea) gridbag.get("sample");
		int fontStyle = (bold.isSelected() ? Font.BOLD : 0) + (italic.isSelected() ? Font.ITALIC : 0);

		sample.setFont(new Font(fontFace, fontStyle, fontSize));
		sample.repaint();
	}
}
